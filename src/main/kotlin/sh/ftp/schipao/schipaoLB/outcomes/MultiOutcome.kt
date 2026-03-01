package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.entity.Player

@SerialName("multi")
@Serializable
class MultiOutcome(val outcomes: List<LBOutcome>, override val lucky: Float = 0f) : LBOutcome {
    override fun run(player: Player, block: Block) {
        outcomes.forEach { it.run(player, block) }
    }
}