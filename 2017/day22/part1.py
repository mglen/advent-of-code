from collections import defaultdict

infections = defaultdict(bool)
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
                infections['{}:{}'.format(cy,cx)] = True

total = 0
for i in xrange(0,10000):
    if infections['{}:{}'.format(y,x)]:
        del infections['{}:{}'.format(y,x)]
        turn_right()
        move()
    else:
        infections['{}:{}'.format(y,x)] = True
        total += 1
        turn_left()
        move()

print total
