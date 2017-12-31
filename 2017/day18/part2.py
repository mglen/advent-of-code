from collections import defaultdict

class StopException(Exception):
    def __init__(self, message, value):
        self.message = message
        self.value = value

def snd(X, register, queue):
    queue.append(register[X])

def set(X, Y, register):
    register[X] = (register[Y] if Y.isalpha() else int(Y))

def add(X, Y, register):
    register[X] += (register[Y] if Y.isalpha() else int(Y))

def mul(X, Y, register):
    register[X] *= (register[Y] if Y.isalpha() else int(Y))

def mod(X, Y, register):
    register[X] %= (register[Y] if Y.isalpha() else int(Y))

def rcv(X, register, queue):
    register[X] = queue.pop(0)

def jgz(X, Y, register):
    if (register[X] if X.isalpha() else int(X)) <= 0:
        return 1
    return (register[Y] if Y.isalpha() else int(Y))

def parse():
    with open('input.txt','r') as f:
        return f.readlines()

def main(lines):
    answer = 0
    lines_length = len(lines)
    idx = [0,0]
    q = [[],[]]
    r = [defaultdict(int), defaultdict(int)]
    r[1]['p'] = 1
    locked = [False,False]
    pid = 1 # Opposite of starting pid
    while True:
        pid = 0 if pid == 1 else 1
        while idx[pid] < lines_length:
            cmd = lines[idx[pid]].split()
            if cmd[0] == 'snd':
                snd(cmd[1], register=r[pid], queue=q[0 if pid == 1 else 1])
                if pid == 1:
                    answer += 1
            elif cmd[0] == 'rcv':
                try:
                    rcv(cmd[1], register=r[pid], queue=q[pid])
                    locked[pid] = False
                except Exception:
                    other = 0 if pid == 1 else 1
                    if locked[other] and len(q[other]) == 0:
                        raise StopException("Got stuck", answer)
                    locked[pid] = True
                    break
            elif cmd[0] == 'jgz':
                idx[pid] += jgz(*cmd[1:], register=r[pid])
                continue
            else:
                globals()[cmd[0]](*cmd[1:], register=r[pid])
            idx[pid] += 1
        if idx[0] >= lines_length and idx[1] >= lines_length:
            raise StopException("Finished execution", answer)


if __name__ == '__main__':
    lines = parse()
    try:
        main(lines)
    except StopException as e:
        print "Answer is: {}".format(e.value)
    print "Finished"

