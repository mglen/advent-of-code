# #1 @ 469,741: 22x26
import re

pattern = re.compile("#(\d+) @ (\d+),(\d+): (\d+)x(\d+)")
def parse(line):
    m = pattern.match(line)
    idx, left, top, width, height = map(int, m.groups())
    return {'id': idx, 'left': left, 'top': top, 'width': width, 'height': height}


grid = [[0 for _ in range(1000)] for _ in range(1000)]
def modify_grid(m):
    for x in range(m['left'], m['left'] + m['width'] ):
        for y in range(m['top'], m['top'] + m['height']):
            grid[x][y] += 1

def is_claim_intact(m):
    for x in range(m['left'], m['left'] + m['width'] ):
        for y in range(m['top'], m['top'] + m['height']):
            if grid[x][y] != 1:
                return False
    return True

with open('input.txt', 'r') as f:
    measurements = [parse(line) for line in f]
    for m in measurements:
        modify_grid(m)

    count = 0
    for row in grid:
        for item in row:
            if item > 1:
                count += 1
    print("Part 1 answer is: {}".format(count))

    for m in measurements:
        if is_claim_intact(m):
            print("Part 2 answer is: {}".format(m['id']))

