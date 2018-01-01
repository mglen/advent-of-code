base = ".#./..#/###"

def flip_horizontal(grid):
    return [l[::-1] for l in grid]

def flip_vertical(grid):
    return grid[::-1]

def rotate_90cw(grid):
    return zip(*reversed(grid))

def rotate_180(grid):
    return rotate_90cw(rotate_90cw(grid))

def rotate_90ccw(grid):
    return rotate_90cw(rotate_90cw(rotate_90cw(grid)))

def rotations(grid):
    def split(grid):
        return [[x for x in y] for y in grid.split('/')]
    def join(garray):
        return tuple(tuple(y) for y in garray)
    garray = split(grid)
    # Base
    result = [join(garray)]
    # 90cw
    result.append(join(
        rotate_90cw(garray)
        ))
    # 180
    result.append(join(
        rotate_180(garray)
        ))
    # 270/90ccw
    result.append(join(
        rotate_90ccw(garray)
        ))
    # Flip vertical
    result.append(join(
        flip_vertical(garray)
        ))
    # Flip horizontal
    result.append(join(
        flip_horizontal(garray)
        ))
    # Flip horizontal + 90cw
    result.append(join(
        rotate_90cw(flip_horizontal(garray))
        ))
    # Flip horizontal + 90ccw
    result.append(join(
        rotate_90ccw(flip_horizontal(garray))
        ))
    return result

def split(grid):
    return tuple(tuple(x for x in y) for y in grid.split('/'))

def display_grid(grid):
    result = [] 
    for y in grid:
        result.append(' '.join(y))
    return '\n'.join(result)

def count_grid(grid):
    result = 0
    for row in grid:
        for c in row:
            if c == '#':
                result += 1
    return result

s2_rules = {}
s3_rules = {}

with open('input.txt','r') as f:
    for line in f:
        in_grid, out_grid = line.strip().split(' => ')
        if len(in_grid.split('/')) == 2:
            for grid in rotations(in_grid):
                s2_rules[grid] = split(out_grid)
        else:
            for grid in rotations(in_grid):
                s3_rules[grid] = split(out_grid)

grid = split(base)
for i in range(1,19):
    if len(grid) % 2 == 0:
        new_size = len(grid) / 2 * 3
        new_grid = [[None for k in xrange(new_size)] for j in xrange(new_size)]
        for y in range(0,len(grid),2):
            for x in range(0,len(grid),2):
                slice2 = ((grid[y][x], grid[y][x+1]),(grid[y+1][x], grid[y+1][x+1]))
                offset_y = y / 2 * 3
                offset_x = x / 2 * 3
                output = s2_rules[slice2]
                for oy in range(0,3):
                    for ox in range(0,3):
                        new_grid[offset_y + oy][offset_x + ox] = output[oy][ox]
        grid = new_grid
    elif len(grid) % 3 == 0:
        new_size = len(grid) / 3 * 4
        new_grid = [[None for k in xrange(new_size)] for j in xrange(new_size)]
        for y in range(0,len(grid),3):
            for x in range(0,len(grid),3):
                slice3 = ((grid[y][x], grid[y][x+1], grid[y][x+2]),
                        (grid[y+1][x], grid[y+1][x+1], grid[y+1][x+2]),
                        (grid[y+2][x], grid[y+2][x+1], grid[y+2][x+2]))
                offset_y = y / 3 * 4
                offset_x = x / 3 * 4
                output = s3_rules[slice3]
                for oy in range(0,4):
                    for ox in range(0,4):
                        new_grid[offset_y + oy][offset_x + ox] = output[oy][ox]
        grid = new_grid
    else:
        raise Exception("Unexpeted grid size: {}".format(len(grid)))
    print "Iteration {} pixels: {}".format(i, count_grid(grid))

