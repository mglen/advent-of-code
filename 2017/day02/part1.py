def main(data):
    spreadsheet = format(data)
    return get_checksum(spreadsheet)

def format(data):
    spreadsheet = []
    lines = data.split("\n")
    for line in lines:
        spreadsheet.append([int(x) for x in line.split()])
    return spreadsheet

def get_checksum(spreadsheet):
    result = 0
    for row in spreadsheet:
        result += max(row) - min(row)
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
        checksum = main(data)
        print "Checksum is: {}".format(checksum)
