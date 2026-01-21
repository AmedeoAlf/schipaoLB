package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.bukkit.block.Block
import org.bukkit.entity.Player

interface LBOutcome {
    fun run(player: Player, block: Block)
}

val lbOutcomeModule = SerializersModule {
    polymorphic(LBOutcome::class) {
        subclass(TeleportOutcome::class)
        subclass(ItemDropOutcome::class)
        subclass(MessageOutcome::class)
        subclass(MobOutcome::class)
        subclass(MultiOutcome::class)
        subclass(EffectOutcome::class)
        subclass(TitleOutcome::class)
    }
}

val json = Json {
    serializersModule = lbOutcomeModule
    classDiscriminator = "type"
}
