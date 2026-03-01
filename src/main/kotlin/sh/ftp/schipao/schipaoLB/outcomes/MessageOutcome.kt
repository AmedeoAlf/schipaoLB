package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.entity.Player
import sh.ftp.schipao.schipaoLB.mm

@Serializable
@SerialName("message")
class MessageOutcome(val message: String): LBOutcome {
    override fun run(player: Player, block: Block) {
        player.sendMessage { mm(message) }
    }
}