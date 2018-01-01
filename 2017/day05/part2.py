def main(data):
    instructions = parse(data)
    index = 0
    jumps = 0
    while True:
        try:
            val = instructions[index]
            if val >= 3:
                instructions[index] -= 1
            else:
                instructions[index] += 1
            index += val
            jumps += 1
        except IndexError:
            return jumps

def parse(input_data):
    return [int(x) for x in input_data.split("\n")]

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
        checksum = main(data)
        print "Jump count is: {}".format(checksum)
