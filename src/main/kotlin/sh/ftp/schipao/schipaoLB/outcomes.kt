package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/*
val outcomes = listOf(
    TeleportOutcome(30.0), MessageOutcome("IDK immagina sia un altro drop casuale"), ItemDropOutcome(
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
*/

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

val outcomes =
    json.decodeFromString<Collection<LBOutcome>>("""[{"type":"teleport","yDistance":30.0}, {"type":"message","message":"IDK immagina sia un altro drop casuale"}, {"type":"ItemDropOutcome","items":["GOLDEN_SWORD;Hai una chance;;minecraft:sharpness/200;31"]}]""")