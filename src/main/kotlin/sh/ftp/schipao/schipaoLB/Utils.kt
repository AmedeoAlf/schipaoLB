package sh.ftp.schipao.schipaoLB

import io.papermc.paper.math.BlockPosition

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
