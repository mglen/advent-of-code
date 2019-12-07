def solve(data):
    available_steps = set()

    new_items = set(map(lambda x: x[0], data))
    after_items = set(map(lambda x: x[1], data))
    next_steps = new_items.difference(after_items)
    
    available_steps = next_steps | available_steps

    while available_steps:
        print(available_steps)
        do_step = sorted(available_steps)[0]
        available_steps.remove(do_step)
        yield do_step
        data = list(filter(lambda x: x[0] != do_step, data))
        new_items = set(map(lambda x: x[0], data))
        after_items = set(map(lambda x: x[1], data))
        next_steps = new_items.difference(after_items)
        available_steps = next_steps | available_steps




with open('input.txt','r') as f:
    data = []

    for line in f:
        vals = line.split()
        before = vals[1]
        after = vals[7]
        data.append((before,after))

#    def find_ordered_items(filtered_data):
#        print("got_here")
#        new_items = set(map(lambda x: x[0], filtered_data))
#        after_items = set(map(lambda x: x[1], filtered_data))
#        next_steps = sorted(new_items.difference(after_items))
#        print(next_steps)
#        for s in next_steps:
#            yield s
#        new_data = list(filter(lambda x: x[0] not in next_steps, filtered_data))
#        if new_data:
#            print("what")
#            yield from find_ordered_items(new_data)

    print(''.join(solve(data)))


