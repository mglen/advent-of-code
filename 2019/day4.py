from collections import defaultdict

def isincrementing(dig):
    d = [int(di) for di in str(dig)]
    return d[0] <= d[1] <= d[2] <= d[3] <= d[4] <= d[5]

def has_same(dig):
    d = [int(di) for di in str(dig)]
    return d[0] == d[1] or d[1] == d[2] or d[2] == d[3] \
            or d[3] == d[4] or d[4] == d[5]

def has_same_only_two(dig):
    d = defaultdict(int)
    for di in str(dig):
        d[di] += 1

    return any([v == 2 for v in d.values()])

part1_count = 0
part2_count = 0
for digit in range(138241, 674034):
    if isincrementing(digit):
        if has_same(digit):
            part1_count += 1
        if has_same_only_two(digit):
            part2_count += 1

print("Part1:", part1_count)
print("Part2:", part2_count)
