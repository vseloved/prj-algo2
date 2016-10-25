package util


object Benchmark {
  def time(name: String, fun: (() => Unit)): Double = {
    val start = System.nanoTime()
    fun()
    (System.nanoTime() - start) / 1000000d
  }

  def loadTest[T](names: Seq[String], data: Seq[T], fun: (T) => Unit): Unit = {
    assert(names.size == data.size)
    println("Warming up...")

    println("name\tsamples\t0.1\t0.5\t0.9\t0.99\t0.999")
    for (x <- names.indices) {
      val t = time(names(x), () => fun(data(x)))
      println("%s\t%f".format(names(x), t))
    }
  }
}
