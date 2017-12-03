def main(input_data):
    numbers = [int(x) for x in input_data]
    numbers.append(numbers[0]) # To deal with loop-around
    return p2(numbers)

def p2(numbers):
    result = 0
    list_len = len(numbers)
    for i in range(0, list_len):
        offset = (i + list_len/2) % list_len
        if numbers[i] == numbers[offset]:
            result += numbers[i]
    return result

if __name__ == '__main__':
    while True:
        input_data = raw_input('Challenge input: ')
        print main(input_data)
