
def main(data):
    result = set([0])
    follow(data, 0, result)
    return len(result)

def follow(data, start, result):
    values = data[start]
    for v in values:
        if v not in result:
            result.add(v)
            follow(data, v, result) # Might hit stack size limits

def parse(lines):
    data = {}
    for line in lines:
        d = line.split(' <-> ')
        data[int(d[0])] = set(int(x) for x in d[1].split(','))
    return data


with open('input.txt', 'r') as f:
    data = parse(f.readlines())
    answer = main(data)
    print "Group with 0 has size: {}".format(answer)

