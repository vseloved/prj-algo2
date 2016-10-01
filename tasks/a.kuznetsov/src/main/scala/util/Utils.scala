package util

object Utils {
  def printMatrix[T](xs: Array[Array[T]], replace: T, withValue: T): Unit = {
    for (l <- xs) {
      printArray(l,replace, withValue)
    }
  }

  def printArray[T](xs: Array[T], replace: T, withValue: T): Unit = {
    print("[")
    for (x <- xs) {
      if (x == replace) {
        print(withValue + ", ")
      } else {
        print(x + ", ")
      }
    }
    println("]")
  }

}
