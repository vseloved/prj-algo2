package dynamic_programming


object TextJustifier {

  def justify(text: String, width: Int): Seq[Seq[String]] = {
    val words = text.replaceAll("[^\\w]", " ").split("\\s+")
    val wordLengths = words map (x => x.length)

    val size = words.length
    val lineCosts = Array.fill(size, size)(Int.MaxValue)
    for (i <- 0 until size) {
      for (j <- i until size) {
        lineCosts(i)(j) = lineCost(wordLengths.slice(i, j + 1), width)
      }
    }

    var i, j = size - 1
    var result = List[(Int, Int)]()
    while (j >= 0) {
      i = j
      var minLine = (i, j)
      var minCost = lineCosts(i)(j)
      while (i >= 0) {
        if (lineCosts(i)(j) < minCost) {
          minLine = (i, j)
          minCost = lineCosts(i)(j)
        }
        i -= 1
      }
      result = minLine :: result
      j = minLine._1 - 1
    }
    result.map(x => words.slice(x._1, x._2 + 1).toList)
  }

  private def lineCost(words: Seq[Int], maxLength: Int): Int = {
    if (words.isEmpty) 0
    else {
      val result = Math.pow(maxLength - words.sum - words.length + 1, 3).toInt

      if (result >= 0)
        result
      else
        Int.MaxValue
    }
  }

  def toString(lines: Seq[Seq[String]]): String = {
    lines
      .foldLeft("")((text, line) => text + line.foldLeft("")((l, w) => l + " " + w) + "\n")
  }

}
