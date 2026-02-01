package sh.ftp.schipao.schipaoLB.outcomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.HeightMap
import org.bukkit.Location
import org.bukkit.RegionAccessor
import org.bukkit.TreeType
import org.bukkit.block.Biome
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.block.data.BlockData
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.util.BlockVector
import org.bukkit.util.BoundingBox
import sh.ftp.schipao.schipaoLB.SchipaoLB
import java.io.File
import java.lang.Class
import java.util.Random
import java.util.function.Consumer
import java.util.function.Predicate
import kotlin.Any

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
        val baseLocation = block.location.clone().add(
            offsetX.toDouble(),
            offsetY.toDouble(),
            offsetZ.toDouble()
        )

        val structureFile = File(
            SchipaoLB.dataFolder,
            "structures/$structure.nbt"
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
            StructurePlaceRegionAccessor(),
            BlockVector(baseLocation.toVector()),
            entities,
            rotationValue,
            Mirror.NONE,
            0,
            1.0f,
            Random()
        )
    }
}


class StructurePlaceRegionAccessor : RegionAccessor {
    // L'unica funzione non mandata direttamente a SchipaoLB.world
    override fun setBlockData(x: Int, y: Int, z: Int, blockData: BlockData) {
        println("${blockData.material.name} placed in $x $y $z")
        SchipaoLB.world.setBlockData(x, y, z, blockData)
    }

    override fun getBiome(x: Int, y: Int, z: Int) = SchipaoLB.world.getBiome(x, y, z)
    override fun getComputedBiome(x: Int, y: Int, z: Int) = SchipaoLB.world.getComputedBiome(x, y, z)
    override fun setBiome(x: Int, y: Int, z: Int, biome: Biome) = SchipaoLB.world.setBiome(x, y, z, biome)

    override fun getBlockState(x: Int, y: Int, z: Int) = SchipaoLB.world.getBlockState(x, y, z)
    override fun getFluidData(x: Int, y: Int, z: Int) = SchipaoLB.world.getFluidData(x, y, z)
    override fun getBlockData(x: Int, y: Int, z: Int) = SchipaoLB.world.getBlockData(x, y, z)
    override fun getType(x: Int, y: Int, z: Int) = SchipaoLB.world.getType(x, y, z)

    override fun generateTree(location: Location, random: Random, type: TreeType) =
        SchipaoLB.world.generateTree(location, random, type)

    override fun generateTree(
        location: Location,
        random: Random,
        type: TreeType,
        stateConsumer: Consumer<in BlockState>?
    ) = SchipaoLB.world.generateTree(location, random, type, stateConsumer)

    override fun generateTree(
        location: Location,
        random: Random,
        type: TreeType,
        statePredicate: Predicate<in BlockState>?
    ) =
        SchipaoLB.world.generateTree(location, random, type, statePredicate)
    override fun spawnEntity(loc: Location, type: EntityType, randomizeData: Boolean) =
        SchipaoLB.world.spawnEntity(loc, type, randomizeData)

    override fun getEntities() =
        SchipaoLB.world.getEntities()

    override fun getLivingEntities() =
        SchipaoLB.world.getLivingEntities()

    override fun <T : Entity?> getEntitiesByClass(cls: Class<T?>) =
        SchipaoLB.world.getEntitiesByClass(cls)

    override fun getEntitiesByClasses(vararg classes: Class<*>) =
        SchipaoLB.world.getEntitiesByClasses(classes[0])

    override fun <T : Entity?> createEntity(location: Location, clazz: Class<T?>) =
        SchipaoLB.world.createEntity(location, clazz)

    override fun <T : Entity?> spawn(
        location: Location,
        clazz: Class<T?>,
        function: Consumer<in T>?,
        reason: CreatureSpawnEvent.SpawnReason
    ) =
        SchipaoLB.world.spawn(location, clazz, function, reason)

    override fun <T : Entity?> spawn(
        location: Location,
        clazz: Class<T?>,
        randomizeData: Boolean,
        function: Consumer<in T>?
    ) =
        SchipaoLB.world.spawn(location, clazz, randomizeData, function)

    override fun getHighestBlockYAt(x: Int, z: Int) =
        SchipaoLB.world.getHighestBlockYAt(x, z)

    override fun getHighestBlockYAt(location: Location) =
        SchipaoLB.world.getHighestBlockYAt(location)

    override fun getHighestBlockYAt(x: Int, z: Int, heightMap: HeightMap) =
        SchipaoLB.world.getHighestBlockYAt(x, z, heightMap)

    override fun getHighestBlockYAt(location: Location, heightMap: HeightMap) =
        SchipaoLB.world.getHighestBlockYAt(location, heightMap)

    override fun <T : Entity?> addEntity(entity: T & Any) =
        SchipaoLB.world.addEntity(entity)

    override fun getMoonPhase() =
        SchipaoLB.world.moonPhase

    override fun getKey() =
        SchipaoLB.world.key

    override fun lineOfSightExists(from: Location, to: Location) =
        SchipaoLB.world.lineOfSightExists(from, to)

    override fun hasCollisionsIn(boundingBox: BoundingBox) =
        SchipaoLB.world.hasCollisionsIn(boundingBox)

    override fun getFeatureFlags() =
        SchipaoLB.world.featureFlags
}