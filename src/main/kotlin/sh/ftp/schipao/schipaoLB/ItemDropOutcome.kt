package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun ItemStack.toSerializedString(): String = this.type.name
fun fromSerializedString(str: String) = ItemStack.of(Material.getMaterial(str) ?: Material.AIR)

@Serializable(with = ItemDropOutcomeSerializer::class)
@SerialName("explosion")
class ItemDropOutcome(val items: Collection<ItemStack>) : LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.give(items, true)
    }
}

class ItemDropOutcomeSerializer : KSerializer<ItemDropOutcome> {
    private val itemListSerializer = ListSerializer(String.serializer())

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("ItemDropOutcome") {
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