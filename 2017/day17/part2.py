inserted_at = 0
after_zero = 0
for i in xrange(1,50000000):
    inserted_at = ((inserted_at + 367) % i) + 1
#    if inserted_at == 0:
#        print "shouldn't get here"
    if inserted_at == 1:
        after_zero = i
print last_i
