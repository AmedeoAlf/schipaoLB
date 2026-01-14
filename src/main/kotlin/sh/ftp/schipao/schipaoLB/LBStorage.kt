package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import io.papermc.paper.math.Position
import org.bukkit.block.Block

val Block.position: BlockPosition get() = Position.block(location)
typealias LBStorage = MutableSet<BlockPosition>