class Firewall:

    def __init__(self, data):
        self.layers = {}
        for depth, lrange in data.iteritems():
            self.layers[depth] = Layer(lrange, depth)

    def tick(self):
        for layer in self.layers.values():
            layer.move()

    def disp(self, idx):
        data = []
        for i in range(0, max(self.layers.keys())+1):
            disp = "({})".format(i) if i == idx else " {} ".format(i)
            if i in self.layers:
                data.append("{}- {}".format(disp, self.layers[i]))
            else:
                data.append("{}...".format(disp))
        return "\n".join(data)

    def __str__(self):
        data = []
        for i in range(0, max(self.layers.keys())+1):
            if i in self.layers:
                data.append("{} - {}".format(i, self.layers[i]))
            else:
                data.append("{} ...".format(i))
        return "\n".join(data)

    def scan(self, idx):
        if idx in self.layers:
            layer = self.layers[idx]
            if layer.pos == 1:
                return True
        return False

class Layer:

    def __init__(self, lrange, depth):
        self.depth = depth
        self.range = lrange
        self.direction = 'd'
        self.pos = 1

    def move(self):
        if self.range == 1:
            return
        if self.direction == 'd':
            if self.pos == self.range:
                self.direction = 'u'
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
    delay = 0
    while True:
        if passed(data, delay):
            break
        delay += 1
    return delay

def passed(data, delay):
    for step in range(0, max(data.keys())+1):
        if step in data:
            if (step + delay) % ((data[step]-1) * 2) == 0:
                return False
    return True

def iscaught_delay(data, delay):
    firewall = Firewall(data)
    for i in range(0, delay):
        firewall.tick()
    for i in range(0, max(data.keys())+1):
        if firewall.scan(i):
            return True
        firewall.tick()
    return False

def parse(lines):
    data = {}
    for line in lines:
        d = line.split(': ')
        data[int(d[0])] = int(d[1])
    return data

with open('input.txt', 'r') as f:
    data = parse(f.readlines())
    answer = main(data)
    print "Delay needed: {}".format(answer)
