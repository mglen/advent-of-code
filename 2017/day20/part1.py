import re
with open('input.txt','r') as f:
    min_idx = 9999
    min_acc = 9999
    for idx, line in enumerate(f):
        m = re.search("a=<(-?\d+),(-?\d+),(-?\d+)>", line)
        acceleration = sum([abs(int(v)) for v in m.groups()])
        if acceleration < min_acc:
            min_acc = acceleration
            min_idx = idx
    print min_idx
