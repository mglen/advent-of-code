b = 93
b = b * 100 + 100000
c = b + 17000

class AddException(Exception):
    pass

answer = 0
for b in xrange(b, c+1, 17):
    try:
        for g in xrange(2, b/2):
            for e in xrange(2, b):
                if g * e == b:
                    raise AddException
                elif g * e > b:
                    break
    except AddException:
        answer += 1

print answer
