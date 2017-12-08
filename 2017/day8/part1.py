def main(data):
    code = "from collections import defaultdict\ndefaultdic = defaultdict(int)\n"
    for line in data.split("\n"):
        var, action, value, iff, var2, compare, value2 = line.split()
        code += "if defaultdic['{}'] {} {}: defaultdic['{}'] {} {}\n".format(var2, compare, value2, var, transpose(action), value)
    code += "print max(defaultdic.values())"
    exec(code)

def transpose(action):
    if action == "inc":
        return "+="
    return "-="

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
