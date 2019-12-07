
def parse(line):
    static, dynamic = line.split(', ')

    symbol1, data = static.split('=')
    val1 = int(data)

    symbol2, data = dynamic.split('=')
    start, end = list(map(int, data.split('..')))

    assert symbol1 in {'x','y'}
    assert symbol2 in {'x','y'}

    for i in range(start, end+1):
        if symbol1 == 'x':
            yield (val1, i)
        else:
            yield (i, val1)

def minus_y(water):
    return (water[0], water[1]-1)

def add_y(water):
    return (water[0], water[1]+1)

def add_x(water):
    return (water[0]+1, water[1])

def minus_x(water):
    return (water[0]-1, water[1])

# There will totall be an infinite issue with going down then back up
# ^ Fixed that. Now need to return all sides water can spill off
def check_row(water, coords):
    row_stack = [water]
    potential_water = set([water])
    continue_coords = []

    if add_y(water) not in coords:
        return {
            'potential-water': potential_water,
            'continue-coords': [add_y(water)]
        }

    right_coord = add_x(water)
    while True: # Right
        if right_coord in coords:
            break
        potential_water.add(right_coord)
        if add_y(right_coord) not in coords:
            continue_coords.append(add_y(right_coord))
            break
        right_coord = add_x(right_coord)

    left_coord = minus_x(water)
    while True: # Right
        if left_coord in coords:
            break
        potential_water.add(left_coord)
        if add_y(left_coord) not in coords:
            continue_coords.append(add_y(left_coord))
            break
        left_coord = minus_x(left_coord)


#    while row_stack:
#        current = row_stack.pop()
#        if add_y(current) not in coords:
#            return add_y(current)# Keeps going down
#        right = add_x(current)
#        left = minus_x(current)
#        if right not in potential_water and right not in coords:
#            row_stack.append(right)
#        if left not in potential_water and left not in coords:
#            row_stack.append(left)
#        potential_water.add(current)
    return {
        'potential-water': potential_water,
        'continue-coords': continue_coords
    }

def draw(coords, initial_coords):
    max_y = max(coords, key=lambda x: x[1])[1]
    min_y = min(coords, key=lambda x: x[1])[1]
    max_x = max(coords, key=lambda x: x[0])[0]
    min_x = min(coords, key=lambda x: x[0])[0]

    def put_ch(coord):
        if coord in initial_coords:
            return '#'
        elif coord in coords:
            return '~'
        return '.'

    result = []
    for y in range(min_y, max_y+1):
        result.append(
                [put_ch((x,y)) for x in range(min_x, max_x+1)]
                )

    for line in result:
        print(''.join(line))


with open('input.txt') as f:
    coords = set()
    for line in f:
        for v in parse(line):
            coords.add(v)

    max_y = max(coords, key=lambda x: x[1])[1]
    min_y = min(coords, key=lambda x: x[1])[1]

    water_stack = [(500,min_y)]

    answer = set()
    initial_coords = coords.copy()

    while water_stack:
#        water_stack = sorted(water_stack, key=lambda coord: coord[1], reverse=True)
        water = water_stack.pop()
#        if water in answer:
#            continue
        result = check_row(water, coords)

        answer.update(result['potential-water'])
        coords.update(result['potential-water'])

        # Start filling up the bucket
        if not result['continue-coords']:
            water_stack.append(minus_y(water))
        else:
            water_stack += \
                filter(lambda coord: coord[1] <= max_y, result['continue-coords'])
    print(len(answer))
    draw(answer, initial_coords)


