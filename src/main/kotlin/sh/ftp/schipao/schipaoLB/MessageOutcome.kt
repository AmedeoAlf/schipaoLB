package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

@Serializable
@SerialName("message")
class MessageOutcome(val message: () -> Component): LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.sendMessage(message)
    }
}