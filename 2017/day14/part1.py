input_data = raw_input("Puzzle input: ")

def rotate(l, n):
    return l[-n:] + l[:-n]

def hash_it(input_data):
    lengths = [ord(x) for x in input_data]
    lengths += [17, 31, 73, 47, 23]
    knot = range(0,256)
    index = 0
    skipval = 0
    for i in range(0,64):
        for length in lengths:
            knot = rotate(knot, -index)
            knot[:length] = reversed(knot[:length])
            knot = rotate(knot, index)
            index = (index + length + skipval) % 256
            skipval += 1
    answ = []
    for d in range(0,16):
        answ.append(eval('^'.join(str(x) for x in knot[d*16:d*16+16])))
    return  ''.join('{:08b}'.format(d) for d in answ)

total = 0
for i in range(0,128):
    line = hash_it("{}-{}".format(input_data, i))
    for c in line:
        if c == '1':
            total +=1
print total
