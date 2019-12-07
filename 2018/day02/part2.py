from collections import defaultdict

def solve(lines, offset=0):
    sublines = set()
    for line in lines:
        subline = line[:offset] + line[offset+1:]
        if subline in sublines:
            return subline
        sublines.add(subline)

with open('input.txt', 'r') as f:
    lines = [l for l in f]

    for i in range(0, len(lines[0])):
        result = solve(lines, i)
        if result:
            print("Answer is: {}".format(result))
            break
    else:
        print("No answer found?")

