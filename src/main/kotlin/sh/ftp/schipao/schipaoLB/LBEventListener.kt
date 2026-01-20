package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import io.papermc.paper.math.Position
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent

val Block.position: BlockPosition get() = Position.block(x, y, z)

class LBEventListener(val block: Material, val outcomes: Collection<LBOutcome>) : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.block.type == block) {
            event.block.world.playSound(
                event.block.location, Sound.BLOCK_STONE_PLACE, 1f, 1f
            )
        }
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.blockList().removeIf { it.type == block }
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        if (event.explodedBlockState.type == block) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        if (event.block.type == block) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type == block) {
            event.isDropItems = false
            event.block.world.playSound(
                event.block.location, Sound.BLOCK_STONE_BREAK, 1f, 1f
            )

            outcomes.random().run(event.player, event.block)
        }
    }
}