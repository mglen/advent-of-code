from copy import deepcopy

# Stack depth shouldn't exceed the # of lines in input.txt
def recurse_most(connections, tail, total):
    values = [total]
    for i in range(0, len(connections)):
    	if connections[i][0] == tail:
    		conns = deepcopy(connections)
    		a, b = conns.pop(i)
    		values.append(recurse_most(conns, b, total + a + b))
    	elif connections[i][1] == tail:
    		conns = deepcopy(connections)
    		b, a = conns.pop(i)
    		values.append(recurse_most(conns, b, total + a + b))
    return max(values)


connections = []
with open('input.txt','r') as f:
	for line in f:
		connections.append([int(x) for x in line.split('/')])

print recurse_most(connections, 0, 0)
