package sh.ftp.schipao.schipaoLB

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockDestroyListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockBreak(event: BlockBreakEvent) {
        println("Block destroy event $event")
    }
}