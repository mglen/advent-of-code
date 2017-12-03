from part1 import format, get_input

def main(data):
    spreadsheet = format(data)
    return get_total_checksum(spreadsheet)

def get_total_checksum(spreadsheet):
    result = 0
    for row in spreadsheet:
        result += get_row_checksum(row)
    return result

# Uses assumption that only two numbers evenly divide
def get_row_checksum(row):
    for idx, item in enumerate(row):
        for sitem in row[idx+1:]:
            a = float(item) / float(sitem)
            b = float(sitem) / float(item)
            if a.is_integer():
                return a
            elif b.is_integer():
                return b

if __name__ == '__main__':
    while True:
        data = get_input()
        checksum = main(data)
        print "Checksum is: {}".format(checksum)
