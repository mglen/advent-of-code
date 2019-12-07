"""Day 9 part 1

This script only took 26 second to run on my
machine, but used 1252 MB of memory in the process.
With the distinct pattern to the stepping, could
this be solved mathematically?
"""
from collections import defaultdict
from functools import reduce
import itertools


class CircularDoubleLinkedList:

    def __init__(self, init_value):
        self.value = init_value
        self.next = self
        self.prev = self

    def insert(self, value, index):
        """Insert value at index

        index 0 is behind (prev) the current value
        index 1 is after (next) the current value
        """
        new_value = CircularDoubleLinkedList(value)
        add_at = self[index]

        add_at_prev = add_at.prev
        add_at_prev.next, new_value.prev = new_value, add_at_prev
        add_at.prev, new_value.next = new_value, add_at
        return new_value

    def __getitem__(self, key):
        if slice == type(key):
            pass
        elif int == type(key):
            if key == 0:
                return self
            elif key < 0:
                return reduce(lambda res, _: res.prev, range(0, key, -1), self)
            else:
                return reduce(lambda res, _: res.next, range(0, key), self)
        else:
            raise TypeError("Nnno")

    def remove(self, index):
        to_remove = self[index]
        to_remove.prev.next, to_remove.next.prev = to_remove.next, to_remove.prev
        return to_remove

    def __str__(self):
        current = self.next
        result = ["({})".format(self.value)]
        while current != self:
            result.append(str(current.value))
            current = current.next
        return ' '.join(result)


def play(players, up_to):
    marbles = itertools.count()
    game = CircularDoubleLinkedList(next(marbles))
    player_rotation = itertools.cycle(range(1,players+1))
    player_scores = defaultdict(int)

    for marble in marbles:
        current_player = next(player_rotation)

        if marble % 23 == 0:
            removed = game.remove(-7)
            player_scores[current_player] += marble + removed.value
            game = removed.next
        else:
            game = game.insert(marble, 2)

        if marble == up_to:
            return max(player_scores.items(), key=lambda x: x[1])


if __name__ == '__main__':
    winner, score = play(459, 72103)
    print("Player", winner, "won with score", score)
    winner, score = play(459, 72103 * 100)
    print("With 100 times larger, player", winner, "wins with score", score)
