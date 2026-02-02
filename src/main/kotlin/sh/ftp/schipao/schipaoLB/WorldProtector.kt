@file:Suppress("UnstableApiUsage")

package sh.ftp.schipao.schipaoLB

import com.destroystokyo.paper.event.block.BlockDestroyEvent
import io.papermc.paper.math.BlockPosition
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent

fun BlockPosition.chunkPosition() = (blockY() + 64) shl 4 or (blockX() and 0xf) shl 4 or (blockZ() and 0xf)

fun Chunk.blockFromChunkPos(pos: Int) = getBlock(
    (pos shr 4 and 0xf),
    (pos shr 8) - 64,
    (pos and 0xf),
)

class WorldProtector : Listener {
    data class OverriddenBlock(val chunkPos: Int, val data: BlockData?)
    class MutatedChunk {
        val blocks = mutableListOf<OverriddenBlock>()
        fun addBlock(b: Block) = blocks.add(
            OverriddenBlock(
                b.position.chunkPosition(), b.blockData
            )
        )

        fun addBlockData(blockPosition: BlockPosition, data: BlockData) = blocks.add(
            OverriddenBlock(
                blockPosition.chunkPosition(), data
            )
        )

        fun addEmpty(blockPosition: BlockPosition) = blocks.add(
            OverriddenBlock(blockPosition.chunkPosition(), null)
        )
    }

    private val mutatedChunks = mutableMapOf<Long, MutatedChunk>()

    fun addBlock(block: Block) {
        if (block.world != SchipaoLB.world) return
        mutatedChunks.getOrPut(block.chunk.chunkKey) { MutatedChunk() }.addBlock(block)
    }

    fun addBlock(block: Block, blockData: BlockData) {
        if (block.world != SchipaoLB.world) return
        mutatedChunks.getOrPut(block.chunk.chunkKey) { MutatedChunk() }.addBlockData(block.position, blockData)
    }

    fun removeBlock(blockPosition: BlockPosition, world: World) {
        if (world != SchipaoLB.world) return
        mutatedChunks.getOrPut(
            blockPosition.toLocation(world).chunk.chunkKey
        ) { MutatedChunk() }.addEmpty(blockPosition)
    }


    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        removeBlock(event.block.position, event.block.world)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        addBlock(event.block)
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        addBlock(event.block, event.explodedBlockState.blockData)
    }

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        addBlock(event.block)
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.blockList().forEach {
            addBlock(it)
        }
    }

    @EventHandler
    fun onBlockDestroy(event: BlockDestroyEvent) {
        addBlock(event.block)
    }

    fun restore() {
        println("Restoring ${SchipaoLB.world.name}, with ${mutatedChunks.size} chunks")
        mutatedChunks.forEach { (key, data) ->
            val chunk = SchipaoLB.world.getChunkAt(key)
            SchipaoLB.world.loadChunk(chunk)
            println("Restoring chunk x=${chunk.x} z=${chunk.z} (${data.blocks.size} actions)")

            data.blocks.reversed().forEach { (chunkPos, data) ->
                if (data == null) {
                    chunk.blockFromChunkPos(chunkPos).type = Material.AIR
                } else {
                    chunk.blockFromChunkPos(chunkPos).apply {
                        type = data.material
                        blockData = data
                    }
                }
            }
        }
        mutatedChunks.clear()
    }

    fun apply() {
        mutatedChunks.clear()
    }
}
