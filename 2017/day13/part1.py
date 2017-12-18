class Firewall:
    def __init__(self, data):
        self.layers = {}
        for depth, lrange in data.iteritems():
            self.layers[depth] = Layer(lrange, depth)

    def tick(self):
        for layer in self.layers.values():
            layer.move()

    def scan(self, idx):
        if idx in self.layers:
            layer = self.layers[idx]
            if layer.pos == 1:
                return layer.range * layer.depth
        return 0

class Layer:
    def __init__(self, lrange, depth):
        self.depth = depth
        self.range = lrange
        self.direction = 'down'
        self.pos = 1

    def move(self):
        if self.range == 1:
            return
        if self.direction == 'down':
            if self.pos == self.range:
                self.direction = 'up'
                self.pos -= 1
            else:
                self.pos += 1
        else:
            if self.pos == 1:
                self.direction = 'down'
                self.pos += 1
            else:
                self.pos -= 1


def main(data):
    firewall = Firewall(data)
    score = 0
    for i in range(0, max(data.keys())+1):
        score += firewall.scan(i)
        firewall.tick()
    return score

def parse(lines):
    data = {}
    for line in lines:
        d = line.split(': ')
        data[int(d[0])] = int(d[1])
    return data

with open('input.txt', 'r') as f:
    data = parse(f.readlines())
    answer = main(data)
    print "Score: {}".format(answer)
