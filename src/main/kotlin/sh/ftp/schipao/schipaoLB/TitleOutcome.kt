package sh.ftp.schipao.schipaoLB

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.block.Block
import org.bukkit.entity.Player

@SerialName("title")
@Serializable
class TitleOutcome(val title: String, val subtitle: String, val forTicks: Int = 40) : LBOutcome {
    override fun run(player: Player, block: Block) {
        player.showTitle(
            Title.title(
                Component.text(title),
                Component.text(subtitle),
                5,
                forTicks,
                5
            )
        )
    }
}