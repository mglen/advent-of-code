from collections import defaultdict

def freqs(line):
    letter_freqs = defaultdict(int)
    for c in line:
        letter_freqs[c] += 1
    two = False
    three = False
    for v in letter_freqs.values():
        if v == 2:
            two = True
        if v == 3:
            three = True
    return (two, three)


with open('input.txt', 'r') as f:
    twos = 0
    threes = 0
    for line in f:
        two, three = freqs(line)
        if two:
            twos += 1
        if three:
            threes += 1
    print(twos * threes)


