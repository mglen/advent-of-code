from functools import reduce


def part1(offsets):
    return reduce(lambda x, y: x + y, offsets)


def part2(offsets):
    seen = set()
    current = 0
    while True:
        for offset in offsets:
            current += offset
            if current in seen:
                return current
            seen.add(current)


with open('input.txt','r') as f:
    offsets = list(map(lambda x: int(x), f))

    print("Part 1:", part1(offsets))
    print("Part 2:", part2(offsets))
