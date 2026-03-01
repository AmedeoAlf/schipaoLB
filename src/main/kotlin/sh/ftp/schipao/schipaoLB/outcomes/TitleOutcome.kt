package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.title.Title
import org.bukkit.block.Block
import org.bukkit.entity.Player
import sh.ftp.schipao.schipaoLB.mm

@SerialName("title")
@Serializable
class TitleOutcome(val title: String, val subtitle: String, val forTicks: Int = 40, override val lucky: Float = 0f) :
    LBOutcome {
    override fun run(player: Player, block: Block) {
        player.showTitle(
            Title.title(
                mm(title),
                mm(subtitle),
                5,
                forTicks,
                5
            )
        )
    }
}