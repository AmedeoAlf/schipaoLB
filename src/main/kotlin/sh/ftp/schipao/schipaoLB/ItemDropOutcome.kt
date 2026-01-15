package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemDropOutcome(val items: Collection<ItemStack>): LBOutcome {
    override fun run(player: Player, position: BlockPosition) {
        player.give(items, true)
    }
}