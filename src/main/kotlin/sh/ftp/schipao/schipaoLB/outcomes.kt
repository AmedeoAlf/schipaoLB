package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

val outcomes = listOf(
    TeleportOutcome(30.0), MessageOutcome { Component.text("IDK immagina sia un altro drop casuale") }, ItemDropOutcome(
        listOf(
            ItemStack.of(Material.GOLDEN_SWORD).apply {
                itemMeta = (itemMeta as Damageable).apply {
                    damage = Material.GOLDEN_SWORD.maxDurability - 1
                    displayName(Component.text("Hai una chance"))
                }
                addUnsafeEnchantment(Enchantment.SHARPNESS, 200)
            })
    )
)

val lbOutcomeModule = SerializersModule {
    polymorphic(LBOutcome::class) {
        subclass(TeleportOutcome::class)
        subclass(ItemDropOutcome::class)
        subclass(MessageOutcome::class)
    }
}

val json = Json {
    serializersModule = lbOutcomeModule
    classDiscriminator = "type"
}

fun serialize(outcome: LBOutcome): String = json.encodeToString(
    PolymorphicSerializer(LBOutcome::class), outcome
)

fun deserialize(str: String): LBOutcome = json.decodeFromString(
    PolymorphicSerializer(LBOutcome::class), str
)