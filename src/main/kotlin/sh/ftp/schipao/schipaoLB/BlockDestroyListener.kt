package sh.ftp.schipao.schipaoLB

import com.destroystokyo.paper.event.block.BlockDestroyEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class BlockDestroyListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockDestroy(event: BlockDestroyEvent?) {
        println("Block destroy event $event")
    }
}