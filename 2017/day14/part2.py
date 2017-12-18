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

def get_squares(data):
    result = []
    for i in range(0,128):
        line = hash_it("{}-{}".format(data, i))
        result.append([c for c in line])
    return result

def get_adjacent(blocks, x, y):
    if x < 0 or y < 0:
        return 0
    try:
        if not blocks[x][y] == '1':
            return 0
    except:
        return 0
    blocks[x][y] = '#'
    get_adjacent(blocks, x+1, y)
    get_adjacent(blocks, x-1, y)
    get_adjacent(blocks, x, y+1)
    get_adjacent(blocks, x, y-1)
    return 1

blocks = get_squares(input_data)

total = 0
for x in range(0,128):
    for y in range(0,128):
        total += get_adjacent(blocks, x, y)

print total
