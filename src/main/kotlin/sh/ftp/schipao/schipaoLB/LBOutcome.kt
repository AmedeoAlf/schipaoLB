package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import org.bukkit.entity.Player

interface LBOutcome {
    fun run(player: Player, position: BlockPosition)
}
