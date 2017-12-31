from collections import defaultdict

class Program:

    def __init__(self, pid):
        self.registers = defaultdict(int)
        self.registers['p'] = pid
        self.sends = 0
        self.queue = []
        self.locked = False
        self.partner = None
        self.done = False

    def stopped(self):
        return self.done or (self.locked and len(self.queue) == 0)

    def snd(self, X):
        self.partner.queue.append(self.registers[X])
        self.sends += 1

    def set(self, X, Y):
        self.registers[X] = (self.registers[Y] if Y.isalpha() else int(Y))

    def add(self, X, Y):
        self.registers[X] += (self.registers[Y] if Y.isalpha() else int(Y))

    def mul(self, X, Y):
        self.registers[X] *= (self.registers[Y] if Y.isalpha() else int(Y))

    def mod(self, X, Y):
        self.registers[X] %= (self.registers[Y] if Y.isalpha() else int(Y))

    def rcv(self, X):
        try:
            self.registers[X] = self.queue.pop(0)
            self.locked = False
            return False
        except Exception:
            if self.partner.locked and len(self.partner.queue) == 0:
                raise Exception("Both processes are locked")
            self.locked = True
            return True

    def jgz(self, X, Y):
        if (self.registers[X] if X.isalpha() else int(X)) <= 0:
            return 1
        return (self.registers[Y] if Y.isalpha() else int(Y))

    def get_runner(self, instructions):
        self.idx = 0
        while self.idx < len(instructions):
            cmd = instructions[self.idx].split()
            if cmd[0] == 'snd':
                self.snd(cmd[1])
            elif cmd[0] == 'rcv':
                if self.rcv(*cmd[1:]):
                    yield
                    continue
            elif cmd[0] == 'jgz':
                self.idx += self.jgz(*cmd[1:])
                continue
            else:
                getattr(self, cmd[0])(*cmd[1:])
            self.idx += 1
        self.done = True

if __name__ == '__main__':
    with open('input.txt','r') as f:
        lines = f.readlines()
        p0 = Program(0)
        p1 = Program(1)
        p0.partner = p1
        p1.partner = p0
        r0 = p0.get_runner(lines)
        r1 = p1.get_runner(lines)

        import signal
        import sys
	def handle_sigint(sig, frame):
            print('Debugging results are:')
	    sys.exit(0)
	signal.signal(signal.SIGINT, handle_sigint)

        while not p0.stopped() or not p1.stopped():
            # Not handling StopIteration, but not applicable to my input
            try:
                r0.next()
                r1.next()
            except Exception as e:
                print "Exception was: {}".format(e)
                print "PID 1 sends are: {}".format(p1.sends)
                break
        


