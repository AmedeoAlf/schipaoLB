package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.Player
import java.io.File
import java.util.Random

@SerialName("structure")
@Serializable
class StructureOutcome(
    val structure: String,
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val offsetZ: Int = 0,
    val rotation: Boolean = true ) : LBOutcome {

    fun run(block: Block, player: Player) {
        val baseLocation = block.location.clone().add(
            offsetX.toDouble(),
            offsetY.toDouble(),
            offsetZ.toDouble()
        )

        val structureFile = File(
            Bukkit.getPluginsFolder(),
            "SchipaoLB/structures/$structure.nbt"
        )

        if (!structureFile.exists()) {
            player.sendMessage("§cStructure '$structure' not found!")
            return
        }

        val structureManager = Bukkit.getStructureManager()
        val loadedStructure = structureManager.loadStructure(structureFile)

        val rotationValue = if (rotation)
            StructureRotation.entries.toTypedArray().random()
        else
            StructureRotation.NONE

        loadedStructure.place(
            baseLocation,
            false,
            rotationValue,
            Mirror.NONE,
            0,
            1.0f,
            Random()
        )
    }

    override fun run(player: Player, block: Block) {
        TODO("Not yet implemented")
    }
}
