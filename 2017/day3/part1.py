import math

def main(input_num):
    coords = get_index(input_num)
    return abs(coords[0]) + abs(coords[1])

def get_index(number):
    square_size = math.ceil(math.sqrt(input_num))
    square_min = int((square_size-1) ** 2)
    square_max = int(square_size ** 2)

    x = y = 0

    if number > square_max - square_size:
        x = number - (square_max - math.floor(square_size/2))
        y = -math.floor(square_size/2)
        if square_max % 2 == 0: #Needs to be one less due to even sides
            x -= 1
    else:
        y = (square_min + math.ceil(square_size/2)) - number
        x = -math.floor(square_size/2)

    if square_max % 2 == 0: # Evens have TOP/RIGHT side. Odds have BOTTOM/LEFT
        x = -x
        y = -y

    return [x,y]

while True:
    input_num = int(raw_input("Number: "))
    print main(input_num)

