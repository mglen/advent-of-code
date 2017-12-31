from collections import defaultdict

registers = defaultdict(int)

def parse():
    with open('input.txt', 'r') as f:
        return f.readlines()

def main(lines):
    i = 0
    while i < len(lines):
        drr = lines[i].split()
        i += 1
        if len(drr) == 3:
            if drr[2].isalpha():
                drr[2] = registers[drr[2]]
            else:
                drr[2] = int(drr[2])
        if drr[0] == 'snd':
            sound = registers[drr[1]]
        elif drr[0] == 'set':
            registers[drr[1]] = drr[2]
        elif drr[0] == 'add':
            registers[drr[1]] += drr[2]
        elif drr[0] == 'mul':
            registers[drr[1]] *= drr[2]
        elif drr[0] == 'mod':
            registers[drr[1]] = registers[drr[1]] % drr[2]
        elif drr[0] == 'rcv':
            if registers[drr[1]] == 0:
                continue
            break
        elif drr[0] == 'jgz':
            if registers[drr[1]] != 0:
                i += drr[2] - 1 # -1 for already stepped
            continue
    return sound

if __name__ == '__main__':
    lines = parse()
    result = main(lines)
    print "Sound was: {}".format(result)
    
