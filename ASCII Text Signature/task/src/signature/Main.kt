package signature

import java.io.File

object FONTS {

    fun createFontFromFile(fontFile: MutableList<String>): MutableList<MutableList<String>> {
        val (lines, characters) = fontFile[0].split(" ").map { it.toInt() }
        var increase = 1
        val font = MutableList(characters) { MutableList(lines + 1) {" "} }
        for (i in 0 until characters)  {
            for (j in 0 .. lines) {
                font[i][j] = fontFile[increase]
                increase++
            }
        }
        return font
    }

    fun convertWord(word: String, font: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
        val inputToFont = MutableList(word.length) { MutableList(font[0].size) {""} }
        for (wordChar in word.indices) {
            for (fontLetters in 0 until font.size) {
                if (word[wordChar] == font[fontLetters][0][0]) {
                    for (lines in 1 until font[0].size) {
                        inputToFont[wordChar][lines] = font[fontLetters][lines]
                    }
                } else if (word[wordChar] == ' ') {
                    for (lines in 1 until font[0].size) {
                        inputToFont[wordChar][lines] = if (font[0].size == 4) "     " else "          "
                    }
                }
            }
        }
        return inputToFont
    }

    fun createBadge(name: MutableList<MutableList<String>>,status: MutableList<MutableList<String>>){
        //Gets correct width of badge and create blank template
        var nameWidth = 0
        for (i in name.indices){
            nameWidth += name[i][1].length
        }
        var statusWidth = 0
        for (i in status.indices){
            statusWidth += status[i][1].length
        }

        val width = if (nameWidth > statusWidth) nameWidth + 8 else statusWidth + 8
        val badge = MutableList(15) { MutableList(width) {' '} }
        //Builds border around badge
        for (i in 0 until width) {
            badge[0][i] = '8'
            badge[14][i] = '8'
        }
        for (i in 1..13) {
            badge[i][0] = '8'
            badge[i][1] = '8'
            badge[i][width - 2] = '8'
            badge[i][width - 1] = '8'
        }
        //Adds name to badge
        var nameCharStartingPoint =
            if (statusWidth > nameWidth && width % 2 == 0 && nameWidth % 2 == 1) {
                width / 2 - nameWidth / 2 - 1
            } else if (statusWidth > nameWidth) {
                width / 2 - nameWidth / 2
            } else {
                4
            }
        var increase = 0
        for (i in 0 until name.size) {
            for (j in 1 until name[i].size) {
                for (k in nameCharStartingPoint..nameCharStartingPoint + name[i][j].length) {
                    try {
                        badge[j][k] = name[i][j][increase]
                        increase++
                    } catch (e: Exception) {
                        continue
                    }
                }
                increase = 0

            }
            nameCharStartingPoint += name[i][1].length
        }
        //Adds status to badge
        var statusCharStartingPoint =
            if (statusWidth < nameWidth && width % 2 == 0 && statusWidth % 2 == 1) {
                width / 2 - statusWidth / 2 - 1
            } else if (statusWidth < nameWidth) {
                width / 2 - statusWidth / 2
            } else {
                4
            }

        increase = 0
        for (i in 0 until status.size) {
            for (j in 11 .. 13) {
                for (k in statusCharStartingPoint..statusCharStartingPoint + status[i][j - 10].length) {
                    try {
                        badge[j][k] = status[i][j - 10][increase]
                        increase++
                    } catch (e: Exception) {
                        continue
                    }
                }
                increase = 0

            }
            statusCharStartingPoint += status[i][1].length
        }
        //Prints completed badge
        for (i in 0..14) {
            for (j in 0 until width) {
                print(badge[i][j])
            }
            println()
        }
    }

}


fun main() {
    val romanFont = File("C:/roman.txt").readLines(Charsets.US_ASCII).toMutableList()
    val mediumFont = File("C:/medium.txt").readLines(Charsets.US_ASCII).toMutableList()

    println("Enter name and surname:")
    val name = readLine()!!
    println("Enter person's status:")
    val status = readLine()!!
    val roman = FONTS.convertWord(name, FONTS.createFontFromFile(romanFont))
    val medium = FONTS.convertWord(status, FONTS.createFontFromFile(mediumFont))
    FONTS.createBadge(roman, medium)
}
