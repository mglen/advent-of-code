def recur(data): # returns (size, [metadata])
    child_count, metadata_count = data[:2]
    size = 2

    inner_metadata = []
    for _ in range(0, child_count):
        s, m = recur(data[size:])
        inner_metadata += m
        size += s
    
    this_size = size + metadata_count
    this_metadata = data[size:this_size]
    return (this_size, inner_metadata + this_metadata)

with open('input.txt', 'r') as f:
    entries = list(map(int, f.readline().split()))

    _, metadata = recur(entries)
    print("Sum of metadata: ", sum(metadata))
