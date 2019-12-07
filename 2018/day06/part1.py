from collections import defaultdict

with open('input.txt','r') as f:
    coords = []
    for line in f:
        x, y = map(int, line.split(', '))
        coords.append((x,y))

    min_x = min(coords, key=lambda v: v[0])[0] -1
    max_x = max(coords, key=lambda v: v[0])[0] +1
    min_y = min(coords, key=lambda v: v[1])[1] -1
    max_y = max(coords, key=lambda v: v[1])[1] +1

    # key: coords tuple, val: running total
    results = defaultdict(int)

    def closest(xv, yv):
        min_distance = 999
        min_coords = None
        for xc, yc in coords:
            distance = abs(xv - xc) + abs(yv - yc)
            if distance == min_distance:
                min_coords = None
            elif distance < min_distance:
                min_distance = distance
                min_coords = (xc, yc)
        return min_coords


    for x in range(min_x, max_x):
        for y in range(min_y, max_y):
            closest_coord = closest(x, y)
            if closest_coord:
                results[closest_coord] += 1

    # Eliminate edge points
    to_delete = set()
    for x in range(min_x, max_x):
        to_delete.add(closest(x, min_y))
        to_delete.add(closest(x, max_y))
    for y in range(min_y, max_y):
        to_delete.add(closest(min_x, y))
        to_delete.add(closest(max_x, y))

    to_delete.remove(None)

    for k in to_delete:
        del results[k]

    print("Part 1", max(results.items(), key=lambda x: x[1]))

    region_points = 0
    for x in range(min_x, max_x):
        for y in range(min_y, max_y):
            distance = 0
            for coord in coords:
                distance += abs(x - coord[0]) + abs(y - coord[1])
            if distance < 10000:
                region_points += 1
    print("Part 2", region_points)

