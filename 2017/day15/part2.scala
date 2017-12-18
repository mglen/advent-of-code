class Generator(var current: Long, val factor: Long, val mod: Long) {
  def next: Long = {
    current = (current * factor) % 2147483647
    if (current % mod == 0) current
    else next
  }
}

def calculate(a: Generator, b: Generator): Long = {
  def loop(total: Long, pairs: Long): Long = {
    if (pairs == 5000000) return total
    if (a.next % 65536 == b.next % 65536) loop(total + 1, pairs + 1)
    else loop(total, pairs + 1)
  }
  loop(0, 0)
}

val a = new Generator(65, 16807, 4)
val b = new Generator(8921, 48271, 8)
println(calculate(a, b))
