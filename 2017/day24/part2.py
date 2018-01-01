from copy import copy, deepcopy

# Could replce with a datastructure containing length & total, with comparator. To keep memory down.

def get_longest(values):
	max_len = len(max(values, key=lambda x: len(x)))
	return sum(max(values, key=lambda x: sum(x) if len(x) == max_len else 0))

def recurse_longest(connections, tail, current):
    values = [current]
    for i in range(0, len(connections)):
    	if connections[i][0] == tail:
    		conns = deepcopy(connections)
    		a, b = conns.pop(i)
    		values += recurse_longest(conns, b, current + [a, b])
    	elif connections[i][1] == tail:
    		conns = deepcopy(connections)
    		b, a = conns.pop(i)
    		values += recurse_longest(conns, b, current + [a, b])
    return values

connections = []
with open('input.txt','r') as f:
	for line in f:
		connections.append([int(x) for x in line.split('/')])

all_values = recurse_longest(connections, 0, [])
print get_longest(all_values)
