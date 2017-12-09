with open('input.txt') as input_file:
    data = input_file.readline()
    count, garbage_count, value = 0, 0, 0
    garbage, skip = False, False
    for c in range(0, len(data)):
        if skip:
            skip = False
            continue
        if not garbage:
            if data[c] == '{':
                value += 1
            elif data[c] == '}':
                count += value
                value -= 1
            elif data[c] == '<':
                garbage = True
        else:
            if data[c] == '>':
                garbage = False
            elif data[c] == '!':
                skip = True
            else:
                garbage_count += 1

    print "Group count is: {}".format(count)
    print "Garbage count is: {}".format(garbage_count)
