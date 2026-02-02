package sh.ftp.schipao.schipaoLB.outcomes

import io.papermc.paper.math.Position
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.Player
import org.bukkit.generator.LimitedRegion
import org.bukkit.util.BlockTransformer
import sh.ftp.schipao.schipaoLB.LBStructureManager
import sh.ftp.schipao.schipaoLB.SchipaoLB
import sh.ftp.schipao.schipaoLB.position
import java.util.*

@SerialName("structure")
@Serializable
class StructureOutcome(
    val structure: String,
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val offsetZ: Int = 0,
    val rotation: Boolean = true,
    val entities: Boolean = false
) : LBOutcome {

    override fun run(player: Player, block: Block) {
        val loadedStructure = LBStructureManager.getStructure(structure)
        if (loadedStructure == null) {
            println("Structure '$structure' not found")
            return
        }

        @Suppress("UnstableApiUsage")
        val baseLocation = block.position
            .offset(offsetX, offsetY, offsetZ)
            .toLocation(block.world)

        val rotationValue = if (rotation) StructureRotation.entries.toTypedArray().random()
        else StructureRotation.NONE

        @Suppress("UnstableApiUsage")
        loadedStructure.place(
            baseLocation,
            entities,
            rotationValue,
            Mirror.NONE,
            0,
            1.0f,
            Random(),
            listOf(
                object : BlockTransformer {
                    override fun transform(
                        region: LimitedRegion,
                        x: Int,
                        y: Int,
                        z: Int,
                        current: BlockState,
                        state: BlockTransformer.TransformationState
                    ): BlockState {
                        if (current.isPlaced) {
                            SchipaoLB.worldProtector.logRemoval(current.block)
                        } else {
                            SchipaoLB.worldProtector.logCreation(Position.block(x, y, z), region.world)
                        }
                        return current
                    }
                }
            ),
            listOf())
    }
}