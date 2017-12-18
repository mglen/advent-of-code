class LinkedList: 
    def __init__(self, item):
        self.head = item
        item.next = item
        self.length = 1
    
    # Circular
    def __getitem__(self, key):
        go = self.head
        for i in xrange(0,key % self.length):
            go = go.next
        return go

    def insert(self, index, item):
        before = self[index]
        before_index = index % self.length
        item.next, before.next = before.next, item
        self.length += 1
        return before_index + 1

    def __str__(self):
        last = self.head
        result = [last.value]
        for i in xrange(1,self.length):
            last = last.next
            result.append(last.value)
        return str(result)

class Item:
    def __init__(self, value):
        self.value = value
        self.next = None


ll = LinkedList(Item(0))
inserted_at = 0
for i in xrange(1,2018):
    inserted_at = ll.insert(inserted_at + 367, Item(i))
print ll[inserted_at].next.value
