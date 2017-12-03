def main(input_data):
    numbers = [int(x) for x in input_data]
    numbers.append(numbers[0]) # To deal with loop-around
    return p1(numbers)

def p1(numbers):
    result = 0
    for i in range(0, len(numbers) - 1):
        if numbers[i] == numbers[i+1]:
            result += numbers[i]
    return result

if __name__ == '__main__':
    while True:
        input_data = raw_input('Challenge input: ')
        print main(input_data)
