from collections import deque

def to_bools(line):
    return tuple([c == '#' for c in line])

def changes(line):
    _from, _to = line.split(' => ')
    to = True if _to.strip() == '#' else False
    return (to_bools(_from), to)

def next_generation(state, changes_list):
    mod_state = state.copy()
    # Extend front of pots
    for i in range(0, 4):
        if state[i][0]:
            front_pot_id = state[0][1]
            mod_state = [(False, p) for p in range((front_pot_id-4+i),front_pot_id)] + mod_state
            break
    # Extend end of pots
    for i in range(0, 4):
        rev_id = -(i+1)
        if state[rev_id][0]:
#            pot_id = state[rev_id][1]
#            mod_state = mod_state + [(False, p) for p in range(pot_id-rev_id, (pot_id-rev_id)+(4+rev_id))]
            last_pot_id = state[-1][1]
            mod_state = mod_state + [(False, p) for p in range(last_pot_id+1, (last_pot_id+1)+(4-i))]
            break

    new_state = mod_state.copy()
    current = tuple(map(lambda x: x[0], mod_state[:5]))
    for idx in range(5, len(mod_state)):
        current = current[1:] + (mod_state[idx][0],)
        if current in changes_list:
            new_state[idx-2] = (changes_list[current], new_state[idx-2][1])
        else:
            new_state[idx-2] = (False, new_state[idx-2][1])
    return new_state


def printt(data):
    print(''.join(['#' if v[0] else '.' for v in data]))

def total_pot_ids(data):
    return sum(v[1] for v in data if v[0])

with open('test-input.txt','r') as f:
    raw_initial_state = to_bools(f.readline().split(':')[1].strip())
    print(raw_initial_state)
    initial_state = list(zip(raw_initial_state, range(0, 50)))
    print(initial_state)
    f.readline()
    changes_list = dict([changes(line) for line in f])

    state = initial_state
    printt(state)
    for i in range(1,21):
        state = next_generation(state, changes_list)
        printt(state)
        print(list(map(lambda x: x[1], state)))
    print(total_pot_ids(state))

# 785 too low
