package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import io.papermc.paper.math.Position
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

val Block.position: BlockPosition get() = Position.block(x, y, z)

val outcomes = listOf(
    TeleportOutcome(10.0), MessageOutcome { Component.text("IDK immagina sia un altro drop casuale") })

class BlockDestroyListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockPlace(event: BlockBreakEvent) {
        if (event.block.type == Material.DRIED_KELP_BLOCK) {
            event.block.world.playSound(
                event.block.location, Sound.BLOCK_STONE_PLACE, 1f, 1f
            )
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type == Material.DRIED_KELP_BLOCK) {
            event.block.world.playSound(
                event.block.location, Sound.BLOCK_STONE_BREAK, 1f, 1f
            )

            outcomes.random().run(event.player, event.block.position)
        }
    }
}