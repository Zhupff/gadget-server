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
    val result = ArrayList<String>()
    data.forEach { node ->
        val char = node.first()
        val paramsStr = if (node.length > 0) {
            node.substring(1).replace(' ', ',')
        } else ""
        val paramsStrList = paramsStr.split(',')
        when (char) {
            'a', 'A' -> parseA(char, paramsStrList, result)
            'c', 'C' -> parseC(char, paramsStrList, result)
            'h', 'H' -> parseH(char, paramsStrList, result)
            'l', 'L' -> parseL(char, paramsStrList, result)
            'm', 'M' -> parseM(char, paramsStrList, result)
            's', 'S' -> parseS(char, paramsStrList, result)
            'v', 'V' -> parseV(char, paramsStrList, result)
            'z', 'Z' -> parseZ(result)
            else -> result.add(node)
        }
    }
    result.forEach { println(it) }
}

private fun parseA(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 7
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 'a') {
                "PathNode.RelativeArcTo("
            } else if (char == 'A') {
                "PathNode.ArcTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseC(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 6
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 'c') {
                "PathNode.RelativeCurveTo("
            } else if (char == 'C') {
                "PathNode.CurveTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseH(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 1
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 'h') {
                "PathNode.RelativeHorizontalTo("
            } else if (char == 'H') {
                "PathNode.HorizontalTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseL(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 2
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 'l') {
                "PathNode.RelativeLineTo("
            } else if (char == 'L') {
                "PathNode.LineTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseM(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 2
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 'm') {
                "PathNode.RelativeMoveTo("
            } else if (char == 'M') {
                "PathNode.MoveTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseS(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 4
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 's') {
                "PathNode.RelativeQuadTo("
            } else if (char == 'S') {
                "PathNode.QuadTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseV(char: Char, paramsStrList: List<String>, result: MutableList<String>) {
    val paramsSize = 1
    for (i in paramsStrList.indices step paramsSize) {
        val sb = StringBuilder(
            if (char == 'v') {
                "PathNode.RelativeVerticalTo("
            } else if (char == 'V') {
                "PathNode.VerticalTo("
            } else ""
        )
        buildParamsStr(paramsStrList.subList(i, i + paramsSize), sb)
        sb.append("),")
        result.add(sb.toString())
    }
}

private fun parseZ(result: MutableList<String>) {
    result.add("PathNode.Close,")
}

private fun buildParamsStr(paramsList: List<String>, sb: StringBuilder) {
    paramsList.forEachIndexed { index, s ->
        sb.append(s).append(if (index != paramsList.lastIndex) "F," else "F")
    }
}