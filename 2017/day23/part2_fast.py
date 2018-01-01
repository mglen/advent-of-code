import math

# Snagged from: https://www.reddit.com/r/learnpython/comments/3axrg8/how_does_my_isprime_function_look/
def isprime(number):
    if number < 2:
        return False
    for divisor in xrange(2, int(math.sqrt(number)) + 1):
        if number % divisor == 0:
            return False
    else:
        return True

b = 93
b = b * 100 + 100000
c = b + 17000

answer = 0
for b in xrange(b, c+1, 17):
    if not isprime(b):
        answer += 1
print answer

