def recur(data): # returns (size, [metadata], node_value)
    child_count, metadata_count = data[:2]
    size = 2

    child_nodes = {}
    inner_metadata = []
    for idx in range(1, child_count+1):
        s, m, v = recur(data[size:])
        inner_metadata += m
        size += s
        child_nodes[idx] = v
    
    this_size = size + metadata_count
    this_metadata = data[size:this_size]

    if child_count:
        node_value = sum([child_nodes.get(v, 0) for v in this_metadata])
    else:
        node_value = sum(this_metadata)

    return (this_size, inner_metadata + this_metadata, node_value)


with open('input.txt','r') as f:
    entries = list(map(int, f.readline().split()))

    _, _, node_value = recur(entries)
    print("Value of root node is: ", node_value)
