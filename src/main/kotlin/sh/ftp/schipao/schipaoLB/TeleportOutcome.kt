package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import org.bukkit.entity.Player

class TeleportOutcome(val y_distance: Double) : LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.teleport(player.location.add(0.0, y_distance, 0.0))
    }
}