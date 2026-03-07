package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.block.Block
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.Player
import sh.ftp.schipao.schipaoLB.LBStructureManager
import sh.ftp.schipao.schipaoLB.SchipaoLB
import sh.ftp.schipao.schipaoLB.component1
import sh.ftp.schipao.schipaoLB.component2
import sh.ftp.schipao.schipaoLB.component3
import sh.ftp.schipao.schipaoLB.position
import sh.ftp.schipao.schipaoLB.rotated
import java.util.*

@SerialName("structure")
@Serializable
class StructureOutcome(
    val structure: String,
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val offsetZ: Int = 0,
    val rotation: Boolean = true,
    val entities: Boolean = false,
    override val lucky: Float = 0f
) : LBOutcome {

    @Suppress("UnstableApiUsage")
    override fun run(player: Player, block: Block) {
        val loadedStructure = LBStructureManager.getStructure(structure)
        if (loadedStructure == null) {
            SchipaoLB.log("Structure '$structure' not found")
            return
        }

        val originPoint = block.position
            .offset(offsetX, offsetY, offsetZ)

        val rotationValue = if (rotation) StructureRotation.entries.toTypedArray().random()
        else StructureRotation.NONE

        LBStructureManager.getBlocks(structure)!!.forEach {
            val (x, y, z) = it.rotated(rotationValue)
            SchipaoLB.worldProtector.logRemoval(
                block.world.getBlockAt(
                    originPoint.blockX() + x,
                    originPoint.blockY() + y,
                    originPoint.blockZ() + z,
                )
            )
        }

        loadedStructure.place(
            originPoint.toLocation(block.world),
            entities,
            rotationValue,
            Mirror.NONE,
            0,
            1.0f,
            Random(),
        )
    }
}