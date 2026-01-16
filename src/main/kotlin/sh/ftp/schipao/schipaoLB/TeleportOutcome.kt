package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player

@SerialName("teleport")
@Serializable
class TeleportOutcome(val yDistance: Double) : LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.teleport(player.location.add(0.0, yDistance, 0.0))
    }
}