#      key = program name, value = parent program
import re

def main(data):
    rawtower = parse(data)
    make_graph(rawtower)

def make_graph(raw_tower):
    links = {}
    for item in raw_tower:
        for child in item['children']:
            links[child] = item['program']
        if not item['program'] in links:
            links[item['program']] = None

    topitem = get_top(links)

    # Restructure links object
    for item in raw_tower:
        item['parent'] = links[item['program']]
        links[item['program']] = item

    # Calculate weight for each item
    for item in raw_tower:
        item['total_weight'] = get_tree_weight(item['program'], links)

    current = topitem
    tier = 0
    while True:
        print "On tier {} with {}".format(tier, current)
        print links[current]
        weight = {links[child]['program']: links[child]['total_weight'] for child in links[current]['children']}
        print weight
        if all(weight.values()[0] == w for w in weight.values()):
            break
        oddweight = get_odd_out(weight.values())
        for k,v in weight.iteritems():
            if v == oddweight:
                current = k
        tier += 1

    print "GOT TO HERE!"
    goodweight = 0
    for c in links[links[current]['parent']]['children']:
        print links[c]
        if c == current: continue
        goodweight = links[c]['total_weight']
    print "Odd one was: {}".format(current)
    print "Weight should be: {}".format(links[current]['total_weight'] - goodweight)

def get_odd_out(weights):
    if len(weights) == 1:
        return weights[0]
    if len(weights) == 2:
        return weights[0]
    if weights[0] == weights[1]:
        for w in weights:
            if w != weights[0]:
                return w
    else:
        if weights[0] == weights[2]:
            return weights[1]
        else:
            return weights[0]

def get_top(links):
    anyitem = links.itervalues().next()
    while links[anyitem] != None:
        anyitem = links[anyitem]
    return anyitem

def get_tree_weight(start, links):
    if not links[start]['children']:
        return links[start]['weight']
    else:
        return sum([get_tree_weight(child, links) for child in links[start]['children']]) + links[start]['weight']

def parse(input_data):
    result = []
    for line in input_data.split("\n"):
        a = line.split('->')
        m = re.match("(\w+) \((\d+)\)", a[0])
        children = []
        if len(a) > 1:
            children = [x.strip() for x in a[1].split(',')]
        result.append({
            "program": m.groups()[0],
            "weight": int(m.groups()[1]),
            "children": children,
            })
    return result

def get_input():
    print("Give your input. EOF (CTRL+D) to end:")
    data = []
    while True:
        try:
            data.append(raw_input())
        except EOFError:
            break
    return "\n".join(data)

if __name__ == '__main__':
    while True:
        data = get_input()
        main(data)
