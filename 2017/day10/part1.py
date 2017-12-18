input_data = raw_input("Problem input: ")
lengths = [int(x) for x in input_data.split(',')]

def rotate(l, n):
    return l[-n:] + l[:-n]

knot = range(0,256)
index, skipval = 0, 0

for length in lengths:
    knot = rotate(knot, -index)
    knot[:length] = reversed(knot[:length])
    knot = rotate(knot, index)
    index = (index + length + skipval) % 256
    skipval += 1

print knot[0] * knot[1]
