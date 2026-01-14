package sh.ftp.schipao.schipaoLB

import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockDestroyListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockPlace(event: BlockBreakEvent) {
        if (event.block.type == Material.WET_SPONGE) {
            SchipaoLB.luckyblocks.add(event.block.position)
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (SchipaoLB.luckyblocks.remove(event.block.position)) {
            event.player.sendMessage { Component.text("Luckyblock destroyed!!!!11!!") }

            event.block.world.playSound(
                event.block.location,
                Sound.BLOCK_STONE_BREAK,
                1f,
                1f
            )
        }
    }
}