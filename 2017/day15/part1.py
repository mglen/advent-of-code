def gen(n, factor):
    while True:
        n = (n* factor) % 2147483647
        yield n

def b16_match(a, b):
    return a % 65536 == b % 65536 # 2**16

a = gen(int(raw_input("Generator A: [65] ") or 65), 16807)
b = gen(int(raw_input("Generator B: [8921] ") or 8921), 48271)

total = 0
for i in xrange(0,40000000):
    if b16_match(a.next(), b.next()):
        total += 1
print total
