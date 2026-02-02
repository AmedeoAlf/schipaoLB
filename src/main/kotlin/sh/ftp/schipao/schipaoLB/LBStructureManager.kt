package sh.ftp.schipao.schipaoLB

import org.bukkit.Bukkit
import org.bukkit.structure.Structure
import java.io.File

object LBStructureManager {
    private val sm = Bukkit.getStructureManager()
    var structures = mapOf<String, Structure>()
        private set

    fun getStructure(name: String): Structure? {
        val structureFile = File(
            SchipaoLB.dataFolder, "structures/$name.nbt"
        )

        if (!structureFile.exists())
            return null


        val struct = sm.loadStructure(structureFile)
        structures += Pair(name, struct)
        return struct
    }

}