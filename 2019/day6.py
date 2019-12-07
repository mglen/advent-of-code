from itertools import chain, takewhile
DEBUG = False

def get_orbits():
    orbits = {}
    with open('day6.txt') as f:
        for line in f:
            orbited, orbiter = line.strip().split(')')
            orbits[orbiter] = orbited
    return orbits


def orbits_from(obj, orbits):
    result = []
    orbit = orbits.get(obj)
    while orbit:
        result.append(orbit)
        new_orbit = orbits.get(orbit)
        if DEBUG: print("transversing", orbit, '->', new_orbit)
        orbit = new_orbit
    return result


def distance_between(obj_a, obj_b, orbits):
    a_orbits = orbits_from(obj_a, orbits)
    b_orbits = orbits_from(obj_b, orbits)

    common_orbits = set(a_orbits) & set(b_orbits)
    does_not_intersect = lambda obj: obj not in common_orbits

    return sum(1 for _ in chain(
        takewhile(does_not_intersect, a_orbits),
        takewhile(does_not_intersect, b_orbits)))


def part1(orbits):
    total = 0
    for obj in orbits.keys():
        total += len(orbits_from(obj, orbits))
    return total


def part2(orbits):
    return distance_between('YOU', 'SAN', orbits)


if __name__ == '__main__':
    orbits = get_orbits()
    print("Part 1:", part1(orbits))
    print("Part 2:", part2(orbits))
