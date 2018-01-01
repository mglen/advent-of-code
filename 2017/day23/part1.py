from collections import defaultdict

def set(X, Y, register):
    register[X] = (register[Y] if Y.isalpha() else int(Y))

def sub(X, Y, register):
    register[X] -= (register[Y] if Y.isalpha() else int(Y))

def mul(X, Y, register):
    register[X] *= (register[Y] if Y.isalpha() else int(Y))

def jnz(X, Y, register):
    if (register[X] if X.isalpha() else int(X)) == 0:
        return 0
    return (register[Y] if Y.isalpha() else int(Y)) - 1

lines = []
with open('input.txt','r') as f:
    lines = f.readlines()

answer = 0
idx = 0
r = defaultdict(int)
while idx < len(lines):
    cmd = lines[idx].split()
    if cmd[0] == 'jnz':
        idx += jnz(*cmd[1:], register=r)
    else:
        if cmd[0] == 'mul':
            answer += 1
        globals()[cmd[0]](*cmd[1:], register=r)
    idx += 1

print answer
