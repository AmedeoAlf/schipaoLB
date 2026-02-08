package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition
import org.bukkit.DyeColor
import org.bukkit.Material

fun parseQuantity(str: String, sep: String = "/"): Pair<String, Int> {
    try {
        val (name, qty) = str.split(sep) + "1"
        return name to qty.toInt()
    } catch (_: Exception) {
        throw Exception("$str should have been in format 'NAME/<qty>' or 'NAME")
    }
}

operator fun BlockPosition.component1() = blockX()
operator fun BlockPosition.component2() = blockY()
operator fun BlockPosition.component3() = blockZ()

fun Material.woolDyeColor() = when (this) {
    Material.WHITE_WOOL -> DyeColor.WHITE
    Material.ORANGE_WOOL -> DyeColor.ORANGE
    Material.MAGENTA_WOOL -> DyeColor.MAGENTA
    Material.LIGHT_BLUE_WOOL -> DyeColor.LIGHT_BLUE
    Material.YELLOW_WOOL -> DyeColor.YELLOW
    Material.LIME_WOOL -> DyeColor.LIME
    Material.PINK_WOOL -> DyeColor.PINK
    Material.GRAY_WOOL -> DyeColor.GRAY
    Material.LIGHT_GRAY_WOOL -> DyeColor.LIGHT_GRAY
    Material.CYAN_WOOL -> DyeColor.CYAN
    Material.PURPLE_WOOL -> DyeColor.PURPLE
    Material.BLUE_WOOL -> DyeColor.BLUE
    Material.BROWN_WOOL -> DyeColor.BROWN
    Material.GREEN_WOOL -> DyeColor.GREEN
    Material.RED_WOOL -> DyeColor.RED
    Material.BLACK_WOOL -> DyeColor.BLACK
    else -> null
}

fun DyeColor.toWool() = when (this) {
    DyeColor.WHITE -> Material.WHITE_WOOL
    DyeColor.ORANGE -> Material.ORANGE_WOOL
    DyeColor.MAGENTA -> Material.MAGENTA_WOOL
    DyeColor.LIGHT_BLUE -> Material.LIGHT_BLUE_WOOL
    DyeColor.YELLOW -> Material.YELLOW_WOOL
    DyeColor.LIME -> Material.LIME_WOOL
    DyeColor.PINK -> Material.PINK_WOOL
    DyeColor.GRAY -> Material.GRAY_WOOL
    DyeColor.LIGHT_GRAY -> Material.LIGHT_GRAY_WOOL
    DyeColor.CYAN -> Material.CYAN_WOOL
    DyeColor.PURPLE -> Material.PURPLE_WOOL
    DyeColor.BLUE -> Material.BLUE_WOOL
    DyeColor.BROWN -> Material.BROWN_WOOL
    DyeColor.GREEN -> Material.GREEN_WOOL
    DyeColor.RED -> Material.RED_WOOL
    DyeColor.BLACK -> Material.BLACK_WOOL
}