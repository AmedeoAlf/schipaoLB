@file:Suppress("UnstableApiUsage")

package sh.ftp.schipao.schipaoLB

import com.destroystokyo.paper.event.block.BlockDestroyEvent
import io.papermc.paper.math.BlockPosition
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockFormEvent
import org.bukkit.event.block.BlockMultiPlaceEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntitySpawnEvent
import java.util.UUID

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

        fun addBlock(blockPosition: BlockPosition, data: BlockData) = blocks.add(
            OverriddenBlock(
                blockPosition.chunkPosition(), data
            )
        )

        fun addEmpty(blockPosition: BlockPosition) = blocks.add(
            OverriddenBlock(blockPosition.chunkPosition(), null)
        )
    }

    private val mutatedChunks = mutableMapOf<Long, MutatedChunk>()
    private val newEntities = mutableListOf<UUID>()

    fun logRemoval(block: Block) = logRemoval(block, block.blockData)

    fun logRemoval(block: Block, blockData: BlockData) {
        if (block.world != SchipaoLB.world) return
        mutatedChunks.getOrPut(block.chunk.chunkKey) { MutatedChunk() }.addBlock(block.position, blockData)
    }

    fun logCreation(blockPosition: BlockPosition, world: World) {
        if (world != SchipaoLB.world) return
        mutatedChunks.getOrPut(
            blockPosition.toLocation(world).chunk.chunkKey
        ) { MutatedChunk() }.addEmpty(blockPosition)
    }

    fun logCreation(block: Block) = logCreation(block.position, block.world)

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) = logCreation(event.block.position, event.block.world)

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) = logRemoval(event.block)

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) = logRemoval(event.block, event.explodedBlockState.blockData)

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) = logRemoval(event.block)

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) = event.blockList().forEach {
        logRemoval(it)
    }

    @EventHandler
    fun onBlockDestroy(event: BlockDestroyEvent) = logRemoval(event.block)

    @EventHandler
    fun onBlockForm(event: BlockFormEvent) = logCreation(event.block)

    @EventHandler
    fun onBlockMultiPlace(event: BlockMultiPlaceEvent) =
        event.replacedBlockStates.forEach {
            logCreation(it.block)
        }

    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) =
        when (event.entityType) {
            EntityType.PLAYER -> {}
            else -> newEntities.add(event.entity.uniqueId)
        }

    fun restore() {
        SchipaoLB.log("Restoring ${SchipaoLB.world.name}, with ${mutatedChunks.size} chunks")
        mutatedChunks.forEach { (key, data) ->
            val chunk = SchipaoLB.world.getChunkAt(key)
            SchipaoLB.world.loadChunk(chunk)
            SchipaoLB.log("Restoring chunk x=${chunk.x} z=${chunk.z} (${data.blocks.size} actions)")

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

        SchipaoLB.log("Removing ${newEntities.size} entities")
        newEntities.forEach { SchipaoLB.world.getEntity(it)?.remove() }
        newEntities.clear()
    }

    fun apply() {
        SchipaoLB.log("Forgetting ${newEntities.size} new entities and ${mutatedChunks.size} chunks")
        newEntities.clear()
        mutatedChunks.clear()
    }
}
