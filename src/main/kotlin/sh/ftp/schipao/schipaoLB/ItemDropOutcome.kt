package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.TypedKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

fun ItemStack.toSerializedString(): String = listOf(
    type.name,
    displayName().toString(),
    lore()?.joinToString("\n") ?: "",
    enchantments.map { "${it.key.key}/${it.value}" }.joinToString("\n"),
    (itemMeta as Damageable).damage.toString()
).joinToString(";") {
    it.replace("\n", "\\n").replace(";", "\\,")
}


fun fromSerializedString(str: String): ItemStack {
    val (type, itemDisplayName, itemLore, enchantments, itemDamage) = str.split(";").map {
        it.trim().replace("\\n", "\n").replace("\\,", ";")
    }.let { it + List(5 - it.size) { null } }
    return ItemStack.of(Material.getMaterial(type!!)!!).apply {
        if (itemDisplayName == null) return@apply
        itemMeta = itemMeta.apply {
            if (itemDisplayName != "") displayName(Component.text(itemDisplayName))
            if (itemLore != null && itemLore != "") lore(listOf(Component.text(itemLore)))
            if (this is Damageable && itemDamage != null) damage = runCatching { itemDamage.toInt() }.getOrDefault(0)
        }
        enchantments?.split("\n")?.forEach {
            val (enchant, level) = it.split("/") + List(1) { "1" }
            val key = TypedKey.create(RegistryKey.ENCHANTMENT, Key.key(enchant))
            addUnsafeEnchantment(
                RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(key)!!, level.toInt()
            )
        }
    }
}

@Serializable(with = ItemDropOutcomeSerializer::class)
@SerialName("itemdrop")
class ItemDropOutcome(val items: Collection<ItemStack>) : LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.give(items, true)
    }
}

class ItemDropOutcomeSerializer : KSerializer<ItemDropOutcome> {
    private val itemListSerializer = ListSerializer(String.serializer())

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("itemdrop") {
            element("items", itemListSerializer.descriptor)
        }


    override fun serialize(
        encoder: Encoder, value: ItemDropOutcome
    ) {
        encoder.encodeStructure(descriptor) {
            val encodedItems = value.items.map { it.toSerializedString() } // your encoding
            encodeSerializableElement(
                descriptor, 0, itemListSerializer, encodedItems
            )
        }
    }

    override fun deserialize(decoder: Decoder): ItemDropOutcome {
        return decoder.decodeStructure(descriptor) {
            var items: List<String> = emptyList()

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> items = decodeSerializableElement(
                        descriptor, 0, itemListSerializer
                    )

                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            ItemDropOutcome(items.map { fromSerializedString(it) })
        }
    }

}