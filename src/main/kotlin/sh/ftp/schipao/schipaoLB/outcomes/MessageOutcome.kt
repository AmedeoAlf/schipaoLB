package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import org.bukkit.block.Block
import org.bukkit.entity.Player

@Serializable
@SerialName("message")
class MessageOutcome(val message: String): LBOutcome {
    override fun run(player: Player, block: Block) {
        player.sendMessage { Component.text(message) }
    }
}