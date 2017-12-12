
def main(data):
    all_groups = set()
    for key in data.keys():
        #TODO: Don't call if key is in an already found gorup
        some_group = get_group(data, key)
        all_groups.add(frozenset(some_group))
    return len(all_groups)

def get_group(data, digit):
    result = set([digit])
    def follow(start):
        values = data[start]
        for v in values:
            if v not in result:
                result.add(v)
                follow(v) # Might hit stack size limits
    follow(digit)
    return result

def parse(lines):
    data = {}
    for line in lines:
        d = line.split(' <-> ')
        data[int(d[0])] = set(int(x) for x in d[1].split(','))
    return data


with open('input.txt', 'r') as f:
    data = parse(f.readlines())
    answer = main(data)
    print "Total of all groups is: {}".format(answer)

