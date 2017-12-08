#      key = program name, value = parent program
import re

def main(data):
    links = {}
    tower = parse(data)
    for item in tower:
        for child in item['children']:
            links[child] = item['program']
        if not item['program'] in links:
            links[item['program']] = None
    anyitem = links.itervalues().next()
    while links[anyitem] != None:
        anyitem = links[anyitem]
    print anyitem

def parse(input_data):
    result = []
    for line in input_data.split("\n"):
        print line
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
        print result[-1]
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
