from collections import defaultdict

class Turing:

    def __init__(self):
        self.idx = 0
        self.data = defaultdict(int)
        self.state = self.a

    def checksum_at(self, value):
        step = 0
        while True:
            self.state()
            step += 1
            if step == value:
                break
        return sum(self.data.values())

    def a(self):
        cur = self.data[self.idx]
        if cur == 0:
            self.data[self.idx] = 1
            self.idx += 1
        else:
            self.data[self.idx] = 0
            self.idx -= 1
        self.state = self.b

    def b(self):
        cur = self.data[self.idx]
        if cur == 0:
            self.data[self.idx] = 1
            self.idx -= 1
            self.state = self.c
        else:
            self.data[self.idx] = 0
            self.idx += 1
            self.state = self.e

    def c(self):
        cur = self.data[self.idx]
        if cur == 0:
            self.data[self.idx] = 1
            self.idx += 1
            self.state = self.e
        else:
            self.data[self.idx] = 0
            self.idx -= 1
            self.state = self.d

    def d(self):
        cur = self.data[self.idx]
        if cur == 0:
            self.data[self.idx] = 1
            self.idx -= 1
        else:
            self.idx -= 1
        self.state = self.a
    
    def e(self):
        cur = self.data[self.idx]
        if cur == 0:
            self.idx += 1
            self.state = self.a
        else:
            self.data[self.idx] = 0
            self.idx += 1
            self.state = self.f
    
    def f(self):
        cur = self.data[self.idx]
        if cur == 0:
            self.data[self.idx] = 1
            self.idx += 1
            self.state = self.e
        else:
            self.idx += 1
            self.state = self.a

if __name__ == '__main__':
    answer = Turing()
    print answer.checksum_at(12683008)
