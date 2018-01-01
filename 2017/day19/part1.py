
def parse_grid():
    grid = []
    with open('input.txt', 'r') as f:
        sound = None
        for line in f:
            grid.append([l for l in line])
    return grid

def get_start_pos(network):
    y_range = len(network)
    x_range = len(network[0])
    for y in range(0, y_range):
        if network[y][0] == '-':
            return [y,0,'r']
        if network[y][x_range - 1] == '-':
            return [y,x_range - 1, 'l']
    for x in range(0, x_range):
        if network[0][x] == '|':
            return [0,x,'d']
        if network[y_range-1][x] == '|':
            return [y_range-1,x,'u']

# Y,X values for directions
dirs = {
    'd': [1,0,'d'],
    'u': [-1,0,'u'],
    'l': [0,-1,'l'],
    'r': [0,1,'r'],
}
# Potential directions when reaching an intersection
dirs['d*'] = [dirs[d] for d in 'lr']
dirs['u*'] = [dirs[d] for d in 'lr']
dirs['l*'] = [dirs[d] for d in 'du']
dirs['r*'] = [dirs[d] for d in 'du']

def get_char(network, pos):
    return network[pos[0]][pos[1]]

def get_next_pos(network, pos):
    if pos[2] == 'd':
        return [pos[0]+1, pos[1], 'd']
    elif pos[2] == 'u':
        return [pos[0]-1, pos[1], 'u']
    elif pos[2] == 'l':
        return [pos[0], pos[1]-1, 'l']
    elif pos[2] == 'r':
        return [pos[0], pos[1]+1, 'r']
    elif pos[2][1] == '*':
        for d in dirs[pos[2]]:
            new_pos = [pos[0] + d[0], pos[1] + d[1], d[2]]
            c = get_char(network, new_pos)
            if c != ' ':
                return new_pos

total = []
def do_next(network, pos):
    next_pos = get_next_pos(network, pos)
    next_c = get_char(network, next_pos)
    if next_c in ['|','-'] or next_c.isalpha():
        if next_c.isalpha():
            total.append(next_c)
        return next_pos
    elif next_c == '+':
        return next_pos[:2] + [next_pos[2] + '*']
    else:
        raise Exception("Unknown character '{}' at position {}".format(next_c, next_pos))

network = parse_grid()
pos = get_start_pos(network)
steps = 1
while True:
    try:
        pos = do_next(network, pos)
        steps += 1
    except Exception as e:
        print "Encountered letters: {}".format(''.join(total))
        print "Total steps was: {}".format(steps)
        print e
        break




