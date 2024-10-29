package zhupff.gadget.client.script

private fun main() {
}

private fun parseSVGPath(path: String) {
    val data = ArrayList<String>()
    var i = 0
    var j = 0
    while (true) {
        val c = path[j]
        if (c.isLetter()) {
            if (j > i) {
                val sub = path.substring(i, j)
                data.add(sub)
                i = j
                continue
            }
        }
        j++
        if (j >= path.length) {
            data.add(path.substring(i))
            break
        }
    }
    data.forEach { node ->
        val char = node.first()
        val paramsStr = if (node.length > 0) {
            node.substring(1).replace(' ', ',')
        } else ""
        val paramsBuilder = StringBuilder()
        paramsStr
            .split(',')
            .forEach {
                paramsBuilder.append(it).append("F,")
            }
        var params = paramsBuilder.toString()
        if (params.length > 0) {
            params = params.substring(0, params.length - 1)
        }

        val p = when(char) {
            'a' -> "PathNode.RelativeArcTo(${params}),"
            'A' -> "PathNode.ArcTo(${params}),"
            'c' -> "PathNode.RelativeCurveTo(${params}),"
            'C' -> "PathNode.CurveTo(${params}),"
            'h' -> "PathNode.RelativeHorizontalTo(${params}),"
            'H' -> "PathNode.HorizontalTo(${params}),"
            'l' -> "PathNode.RelativeLineTo(${params}),"
            'L' -> "PathNode.LineTo(${params}),"
            'm' -> "PathNode.RelativeMoveTo(${params}),"
            'M' -> "PathNode.MoveTo(${params}),"
            's' -> "PathNode.RelativeQuadTo(${params}),"
            'S' -> "PathNode.QuadTo(${params}),"
            'v' -> "PathNode.RelativeVerticalTo(${params}),"
            'V' -> "PathNode.VerticalTo(${params}),"
            'z', 'Z' -> "PathNode.Close,"
            else -> node
        }
        println(p)
    }
}