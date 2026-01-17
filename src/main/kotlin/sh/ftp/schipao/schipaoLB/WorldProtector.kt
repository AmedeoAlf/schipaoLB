package sh.ftp.schipao.schipaoLB

import com.destroystokyo.paper.event.block.BlockDestroyEvent
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent

fun Block.chunkPosition() = (y + 64) shl 4 or (x and 0xf) shl 4 or (z and 0xf)

fun Chunk.blockFromChunkPos(pos: Int) = getBlock(
    (pos shr 4 and 0xf),
    (pos shr 8) - 64,
    (pos and 0xf),
)

class WorldProtector(val world: World) : Listener {
    data class OverriddenBlock(val material: Material, val chunkPos: Int)
    class MutatedChunk {
        val blocks = mutableListOf<OverriddenBlock>()
        fun addBlock(b: Block, type: Material) = blocks.add(OverriddenBlock(type, b.chunkPosition()))
    }

    val mutatedChunks = mutableMapOf<Long, MutatedChunk>()

    private fun updateBlock(b: Block, type: Material = Material.AIR) {
        if (b.world != world) return
        mutatedChunks.getOrPut(b.chunk.chunkKey, { MutatedChunk() }).addBlock(b, type)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        updateBlock(event.block)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        updateBlock(event.block, event.block.type)
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        updateBlock(event.block, event.explodedBlockState.type)
    }

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        updateBlock(event.block, event.block.type)
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.blockList().forEach {
            updateBlock(it, it.type)
        }
    }

    @EventHandler
    fun onBlockDestroy(event: BlockDestroyEvent) {
        updateBlock(event.block, event.block.type)
    }

    fun restore() {
        println("Restoring ${world.name}, with ${mutatedChunks.size} chunks")
        mutatedChunks.forEach { (key, data) ->
            val chunk = world.getChunkAt(key)
            world.loadChunk(chunk)
            println("Restoring chunk x=${chunk.x} z=${chunk.z} (${data.blocks.size} actions)")

            data.blocks.reversed().forEach { (material, chunkPos) ->
                chunk.blockFromChunkPos(chunkPos).type = material
            }
        }
        mutatedChunks.clear()
    }

    fun apply() {
        mutatedChunks.clear()
    }
}
