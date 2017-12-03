import math

class Point:
    def __init__(self,x,y):
        self.x = x
        self.y = y

    def serialize(self):
        return "{}:{}".format(int(self.x), int(self.y))

    def mod(self, x, y):
        return Point(self.x + x, self.y + y)

class Spiral:
    def __init__(self):
        self.data = {}
        
    def __getitem__(self, point):
        return self.data[point.serialize()]

    def __setitem__(self, point, value):
        self.data[point.serialize()] = value

    def __str__(self):
        out = []
        for k,v in sorted(self.data.iteritems(), key=lambda (k,v): (v,k)):
             out.append("{} -> {}".format(k,v))
        return "\n".join(out)

    def get_adjacent(self, point):
        result = []
        for p in [
            point.mod(0,1), # top
            point.mod(1,1), # top-right
            point.mod(1,0), # right
            point.mod(1,-1), # bottom-right
            point.mod(0,-1), # bottom
            point.mod(-1,-1), # bottom-left
            point.mod(-1,0), # left
            point.mod(-1,1), # top-left
        ]:
            try:
                result.append(self.__getitem__(p))
            except KeyError:
                pass
        return result


def main(input_num):
    data = Spiral()
    data[get_coords(1)] = 1 # Seed first value
    iterator = 2
    last_val = 0
    while last_val < input_num:
        point = get_coords(iterator)
        adjacent = data.get_adjacent(point)
        last_val = sum(adjacent)
        data[point] = last_val
        iterator += 1
    return last_val

def get_coords(number):
    square_size = math.ceil(math.sqrt(number))
    square_min = int((square_size-1) ** 2)
    square_max = int(square_size ** 2)

    point = Point(0,0)

    if number > square_max - square_size:
        point.x = number - (square_max - math.floor(square_size/2))
        point.y = -math.floor(square_size/2)
        if square_max % 2 == 0: #Needs to be one less due to even sides
            point.x -= 1
    else:
        point.y = (square_min + math.ceil(square_size/2)) - number
        point.x = -math.floor(square_size/2)

    if square_max % 2 == 0: # Evens have TOP/RIGHT side. Odds have BOTTOM/LEFT
        point.x = -point.x
        point.y = -point.y

    return point

while True:
    input_num = int(raw_input("Number: "))
    print main(input_num)

