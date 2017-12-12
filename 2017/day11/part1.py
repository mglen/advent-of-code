import math

class Point:
    def __init__(self,x=0,y=0):
        self.x = x
        self.y = y

    def serialize(self):
        return "{}:{}".format(self.x, self.y)

    def __str__(self):
        return self.serialize()

    def __add__(self, point):
        return Point(self.x + point.x, self.y + point.y)
    
    def __sub__(self, point):
        return Point(self.x - point.x, self.y - point.y)

    def rad(self):
        return math.atan2(self.y, self.x)

    def direction_to(self, other):
        relative = other - self
        half = (math.pi / 180 ) * 34 # Sketchy here. Should be 360 / 6 / 2 == 30 degrees
        for direction, point in directions.iteritems():
            if point.rad() - half <= relative.rad() <= point.rad() + half:
                return direction
        raise Exception("Shouldn't fall here {}".format(relative.rad()))

    def move(self, direction):
        return self + directions[direction]

directions = {
    'ne': Point(.75,.5),
    'se': Point(.75,-.5),
    's' : Point(0,-1),
    'sw': Point(-.75,-.5),
    'nw': Point(-.75,.5),
    'n' : Point(0,1),
}

def parse(line):
    data = []
    for d in line[:-1].split(','):
        data.append(d)
    return data

def main(data):
    end_point = Point()
    for direction in data:
        end_point = end_point.move(direction)
    
    start = Point()
    moves = 0
    while start.x != end_point.x or start.y != end_point.y:
        direction = start.direction_to(end_point)
        start = start.move(direction)
        moves += 1
    return moves

with open('file.txt','r') as f:
    line = f.readline()
    data = parse(line)
    answer = main(data)
    print "Moves to destination is: {}".format(answer)

