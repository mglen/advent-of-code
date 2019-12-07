import math

def calculate(x, y, serial):
    rack_id = x + 10
    step2 = rack_id * y
    step3 = step2 + serial
    step4 = step3 * rack_id
    step5 = math.floor(step4 / 100 % 10) 
    return step5 - 5

#print(calculate(122,79,57))
def power_value(data, _x, _y, size):
    result = 0
    for x in range(_x, _x + size):
        for y in range(_y, _y + size):
            result += data[y][x]
    return result

def get_powers(data):
    for x in range(0, 297):
        for y in range(0, 297):
            for size in range(2, 299-max(x, y)):
                try:
                    yield (power_value(data, x, y, size), x+1, y+1, size)
                except Exception as e:
                    print("failed at", x, y, size)
                    print("error was", str(e))
                    return

grid = []
for y in range(1,301):
    grid.append(list(map(lambda x: calculate(x, y, 2694), range(1,301))))
print(max(get_powers(grid), key=lambda v: v[0]))

