package sh.ftp.schipao.schipaoLB

import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

fun Block.chunkPosition() = (y + 64) shl 4 or (x and 0xf) shl 4 or (z and 0xf)

fun Chunk.blockFromChunkPos(pos: Int) = getBlock(
    (pos shr 4 and 0xf) + x,
    (pos shr 8) - 64,
    (pos and 0xf) + z,
)

class WorldProtector(val world: World) : Listener {
    data class OverriddenBlock(val material: Material, val chunkPos: Int)
    class MutatedChunk {
        val blocks = mutableListOf<OverriddenBlock>()
        fun addBlock(b: Block, type: Material) = blocks.add(OverriddenBlock(type, b.chunkPosition()))
    }

    val mutatedChunks = mutableMapOf<Long, MutatedChunk>()

    fun updateBlock(b: Block, type: Material = Material.AIR) {
        if (b.world != world) return
        mutatedChunks.getOrPut(b.chunk.chunkKey, { MutatedChunk() }).addBlock(b, type)
    }

    @EventHandler
    fun onBlockPlace(event: BlockBreakEvent) {
        updateBlock(event.block)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        updateBlock(event.block, event.block.type)
    }

    fun restore() {
        mutatedChunks.forEach { (key, data) ->
            val chunk = world.getChunkAt(key)
            world.loadChunk(chunk)

            data.blocks.asReversed().forEach { (material, chunkPos) ->
                chunk.blockFromChunkPos(chunkPos).type = material
            }
        }
    }

    fun apply() {
        mutatedChunks.clear()
    }
}


