from collections import defaultdict
# [1518-04-12 00:36] falls asleep
# [1518-02-22 00:58] wakes up
# [1518-05-26 23:59] Guard #71 begins shift

class gtype:
    SLEEP = 0
    WAKE  = 1
    SWITCH = 2

def parse(line):
    year, month, day = map(int, line[1:11].split('-'))
    hour, minute = map(int, line[12:17].split(':'))
    epochish = minute + (hour * 60) + (day * 24 * 60) + (month * 31 * 24 * 60) + (year * 365 * 31 * 24 * 60)
    if "wakes" in line[19:]:
        return (epochish, gtype.WAKE, minute)
    elif "falls" in line[19:]:
        return (epochish, gtype.SLEEP, minute)
    elif "Guard" in line[19:]:
        idx = int(line[19:].split()[1][1:])
        return (epochish, gtype.SWITCH, minute, idx)
    else:
        raise Exception("Could not parse", line)


with open('input.txt','r') as f:
    # k: guard id, v: guard schedules
    def init():
        return {'minutes': 0, 'ranges': []}
    guard_data = defaultdict(init)

    data = sorted(map(parse, f), key=lambda x: x[0])

    current_id = data[0][3]
    minutes_asleep = set()
    sleep_start = None
    for item in data[1:]:
        if item[1] == gtype.SLEEP:
            sleep_start = item[2]
        elif item[1] == gtype.WAKE:
            guard_data[current_id]['minutes'] += item[2] - sleep_start
            minutes_asleep.update(set(range(sleep_start, item[2])))
        elif item[1] == gtype.SWITCH:
            guard_data[current_id]['ranges'].append(minutes_asleep)
            current_id = item[3]
            minutes_asleep = set()
        else:
            raise Exception("Item shouldn't be", item)

    worst_guard = max(guard_data.items(), key=lambda x: x[1]['minutes'])
    print("The most least good guard is", worst_guard[0])
    times = worst_guard[1]['ranges']
    most = defaultdict(int)
    for s in times:
        for k in s:
            most[k] += 1
    worst_time = max(most.items(), key=lambda x: x[1])
    print("worst time", worst_time)

