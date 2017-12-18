class generator(var current: Long, val factor: Long) {
  def next: Long = {
    current = (current * factor) % 2147483647
    current
  }
}
val a = new generator(65, 16807)
val b = new generator(8921, 48271)

var total = 0
for (i <- 1 to 40000000) {
//  if (BigInt(a.next).toByteArray.takeRight(2) sameElements BigInt(b.next).toByteArray.takeRight(2)) {
  if (a.next % 65536 == b.next % 65536) {
    total += 1
  }
}
println(total)
