package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

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