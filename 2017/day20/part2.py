class Point:
    def __init__(self, x,y,z):
        self.x, self.y, self.z = x,y,z

    def __eq__(self, point):
        return (isinstance(point, self.__class__)
            and self.x == point.x
            and self.y == point.y
            and self.z == point.z)
    
    def __add__(self, point):
        return Point(self.x + point.x, self.y + point.y, self.z + point.z)
    
    def __sub__(self, point):
        return Point(self.x - point.x, self.y - point.y, self.z + point.z)

    def __hash__(self):
        return hash((self.x, self.y, self.z))

    def distance(self, point):
        d = self - point
        return abs(d.x) + abs(d.y) + abs(d.z)

class Particle:
    def __init__(self, p, v, a):
        self.p, self.v, self.a = p,v,a

    def move(self):
        self.v += self.a
        self.p += self.v

def delete_collisions(particles):
    point_indexes = {}
    to_delete = set()
    for idx, val in particles.iteritems():
        point = val.p
        if point in point_indexes:
            to_delete.add(point_indexes[point])
            to_delete.add(idx)
        else:
            point_indexes[point] = idx
    for idx in to_delete:
        del particles[idx]

def parse():
    with open('input.txt','r') as f:
        import re
        particles = {}
        for idx, line in enumerate(f):
            m = re.match("p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>", line)
            vals = [int(v) for v in m.groups()]
            p = Point(*vals[0:3])
            v = Point(*vals[3:6])
            a = Point(*vals[6:9])
            particles[idx] = Particle(p,v,a)
    return particles

if __name__ == '__main__':
    particles = parse()
    delete_collisions(particles)
    for i in range(0,10000):
        if i % 1000 == 0:
            print "{}%".format(i/100)
        for particle in particles.values():
            particle.move()
        delete_collisions(particles)

    print "Remaining particles: {}".format(len(particles.keys()))
