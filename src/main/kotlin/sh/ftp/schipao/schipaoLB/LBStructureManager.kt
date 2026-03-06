@file:Suppress("UnstableApiUsage")

package sh.ftp.schipao.schipaoLB

import de.tr7zw.nbtapi.NBT
import io.papermc.paper.math.BlockPosition
import io.papermc.paper.math.Position
import org.bukkit.Bukkit
import org.bukkit.structure.Structure
import java.io.File

object LBStructureManager {
    private val sm = Bukkit.getStructureManager()
    var structures = mapOf<String, TaggedStructure?>()
        private set

    data class TaggedStructure(val structure: Structure, val blocks: List<BlockPosition>)

    fun getStructure(name: String): Structure? {
        loadStructureInCache(name)
        return structures[name]?.structure
    }

    fun getBlocks(name: String): List<BlockPosition>? {
        loadStructureInCache(name)
        return structures[name]?.blocks
    }

    private fun loadStructureInCache(name: String) {
        if (structures.contains(name)) return
        structures += Pair(
            name, File(
            SchipaoLB.dataFolder, "structures/$name.nbt"
        )
            .takeIf { it.exists() }
            ?.let { TaggedStructure(sm.loadStructure(it), parseBlocks(it)) }
        )
    }

    private fun parseBlocks(file: File) = NBT.getFileHandle(file)
        .getCompoundList("blocks")
        .map { it.getIntegerList("pos")!! }
        .map { Position.block(it[0], it[1], it[2])}

}