package sh.ftp.schipao.schipaoLB

fun parseQuantity(str: String, sep: String = "/"): Pair<String, Int> {
    try {
        val (name, qty) = str.split(sep) + "1"
        return name to qty.toInt()
    } catch (_: Exception) {
        throw Exception("$str should have been in format 'NAME/<qty>' or 'NAME")
    }
}