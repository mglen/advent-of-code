from collections import defaultdict

infections = defaultdict(int)
x, y = 0, 0
direction = 'u'

def turn_left():
    global direction
    if direction == 'u':
        direction = 'l'
    elif direction == 'l':
        direction = 'd'
    elif direction == 'd':
        direction = 'r'
    elif direction == 'r':
        direction = 'u'
def turn_right():
    global direction
    if direction == 'u':
        direction = 'r'
    elif direction == 'r':
        direction = 'd'
    elif direction == 'd':
        direction = 'l'
    elif direction == 'l':
        direction = 'u'
def turn_reverse():
    global direction
    if direction == 'u':
        direction = 'd'
    elif direction == 'r':
        direction = 'l'
    elif direction == 'd':
        direction = 'u'
    elif direction == 'l':
        direction = 'r'

state_next = {
    0: 1,
    1: 2,
    2: 3,
    3: 0
}
def change_state():
    global infections
    global x, y
    infections['{}:{}'.format(y,x)] = state_next[infections['{}:{}'.format(y,x)]]

def move():
    global direction
    global x, y
    if direction == 'u':
        y -= 1
    elif direction == 'r':
        x += 1
    elif direction == 'd':
        y += 1
    elif direction == 'l':
        x -= 1

with open('input.txt','r') as f:
    lines = f.readlines()
    y = len(lines) / 2
    x = len(lines[0].strip()) / 2
    for cy, line in enumerate(lines):
        for cx, c in enumerate(line):
            if c == '#':
                infections['{}:{}'.format(cy,cx)] = 2
total = 0
for i in xrange(0,10000000):
    if infections['{}:{}'.format(y,x)] == 0: # Clean
        turn_left()
    elif infections['{}:{}'.format(y,x)] == 1: # Weakened
        total += 1
    elif infections['{}:{}'.format(y,x)] == 2: # Infected
        turn_right()
    elif infections['{}:{}'.format(y,x)] == 3: # Flagged
        turn_reverse()
    change_state()
    move()

print total
