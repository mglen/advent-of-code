import operator
def main(input_data):
    numbers = [int(x) for x in input_data.split()]
    seen = set()
    count = 0
    while True:
        index, value = max(enumerate(numbers), key=operator.itemgetter(1))
        numbers[index] = 0
        for i in range(1, value + 1):
            numbers[(index+i)%len(numbers)] += 1
        count += 1
        if str(numbers) in seen:
            break
        seen.add(str(numbers))
    return count

if __name__ == '__main__':
    while True:
        input_data = raw_input('Challenge input: ')
        print main(input_data)
