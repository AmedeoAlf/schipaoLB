package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class MessageOutcome(val message: () -> Component): LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.sendMessage(message)
    }
}