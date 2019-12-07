
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


def check_row2(water, sand_coords, water_coords, filling=False):
    potential_water = {water}
    continue_coords = []

    if not filling:
        if add_y(water) not in sand_coords:
            return {'potential-water': potential_water, 'continue-coords': [add_y(water)]}
        else:
            right_coord = add_x(water)
            while True:
                if right_coord in sand_coords:
                    break
                potential_water.add(right_coord)
                below_right = add_y(right_coord)
                if below_right not in sand_coords:
                    continue_coords.append(below_right)
                    break
                right_coord = add_x(right_coord)

            left_coord = minus_x(water)
            while True:
                if left_coord in sand_coords:
                    break
                potential_water.add(left_coord)
                below_left = add_y(left_coord)
                if below_left not in sand_coords:
                    continue_coords.append(below_left)
                    break
                left_coord = minus_x(left_coord)

    else:
        right_coord = add_x(water)
        while True:
            if right_coord in sand_coords:
                break
            potential_water.add(right_coord)
            below_right = add_y(right_coord)
            if below_right not in sand_coords and \
                    below_right not in water_coords:
                continue_coords.append(below_right)
                break
            right_coord = add_x(right_coord)

        left_coord = minus_x(water)
        while True:
            if left_coord in sand_coords:
                break
            potential_water.add(left_coord)
            below_left = add_y(left_coord)
            if below_left not in sand_coords and \
                    below_left not in water_coords:
                continue_coords.append(below_left)
                break
            left_coord = minus_x(left_coord)
    return {
        'potential-water': potential_water,
        'continue-coords': continue_coords
    }


def check_row(water, coords, water_coords, filling=False):
    potential_water = set([water])
    continue_coords = []

    if water in water_coords and not filling:
        return { 'potential-water': potential_water, 'continue-coords': [] }
    
    if add_y(water) not in coords:
        return {
            'potential-water': potential_water,
            'continue-coords': [add_y(water)]
        }

    right_coord = add_x(water)
    while True:
        if right_coord in coords:
            break
        potential_water.add(right_coord)
        if add_y(right_coord) not in coords:
            continue_coords.append(add_y(right_coord))
            break
        right_coord = add_x(right_coord)

    left_coord = minus_x(water)
    while True:
        if left_coord in coords:
            break
        potential_water.add(left_coord)
        if add_y(left_coord) not in coords:
            continue_coords.append(add_y(left_coord))
            break
        left_coord = minus_x(left_coord)

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
    sand_coords = set()
    for line in f:
        for v in parse(line):
            sand_coords.add(v)

    max_y = max(sand_coords, key=lambda x: x[1])[1]
    min_y = min(sand_coords, key=lambda x: x[1])[1]

    water_stack = [((500,min_y), False)]

    water_coords = set()

    while water_stack:
        water, is_filling = water_stack.pop()
        result = check_row2(water, sand_coords, water_coords, is_filling)

        water_coords.update(result['potential-water'])

        # Start filling up the bucket
        if not result['continue-coords']: 
            water_stack.append((minus_y(water), True))
        else:
            water_stack += \
                    map(lambda x: (x, False), filter(lambda coord: coord[1] <= max_y, result['continue-coords']))
    print(len(water_coords))
    draw(water_coords, sand_coords)


