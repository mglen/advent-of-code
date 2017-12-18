import string

def get_pos(p, programs):
    for idx, l in enumerate(programs):
        if l == p:
            return idx
    raise Exception("Input {} was invalid".format(p))

def get_input():
    moves_raw = []
    moves_result = []
    with open('input.txt','r') as f:
        moves_raw = f.readline().split(',')
    for move in moves_raw:
        if move[0] == 's':
            moves_result.append(['s', int(move[1:])])
        elif move[0] == 'x':
            moves_result.append(['x', [int(x) for x in move[1:].split('/')]])
        elif move[0] == 'p':
            moves_result.append(['p', move[1:].split('/')])
    return moves_result

def dance(programs, move_list):
    for move in move_list:
        if move[0] == 's':
            X = move[1]
            programs = programs[-X:] + programs[:-X]
        elif move[0] == 'x':
            A, B = move[1][0], move[1][1]
            programs[B], programs[A] = programs[A], programs[B]
        elif move[0] == 'p':
            A, B = get_pos(move[1][0], programs), get_pos(move[1][1], programs)
            programs[B], programs[A] = programs[A], programs[B]
    return programs


def find_loop(programs, move_list):
    i = 0
    loops = {}
    loop_order = []
#    loop_length = 0
    while True:
        programs = dance(programs, move_list)
        program_string = ''.join(programs)
        if program_string in loops:
#            loop_length = i - loops[pshort]
            break
        loop_order.append(program_string)
        loops[program_string] = i
        i += 1
    return loop_order

progs = [x for x in string.ascii_lowercase[:16]]
move_list = get_input()
loop_order = find_loop(progs, move_list)
loop_val = 1000000000 % len(loop_order)
print loop_order[loop_val - 1]
