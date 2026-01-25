package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.entity.Player

@SerialName("teleport")
@Serializable
class TeleportOutcome(val yDistance: Double) : LBOutcome {
    override fun run(player: Player, block: Block) {
        player.teleport(player.location.add(0.0, yDistance, 0.0))
    }
}