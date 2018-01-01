### Calculating part 2

Broke down the program to understand how it was executing (where the loops were).
After figuring out the major loops and the numbers involved, calculated the final
answer in [part2.py](part2.py). I ended up struggling on this problem, and receiving
a major amount of help from @noblepayne (thank you!). To go over some of my hurdles:
* I struggled with breaking down the program, and decided to try crunching
 the problem overnight by translating it to C with `goto` statements [part2.c](part2.c).
 It took ~755 minutes on a `Intel(R) Xeon(R) CPU E31240 @ 3.30GHz`, and
 gave out a wrong answer of 940. I'd like to go back and find the error here.
* I thought I'd figured out the clever answer by checking for all primes of `b`, then
 subtracting 1000. This was almost right, and I ended up going in circles and getting
 the answer via the slower method before realizing my error here (now in [part2_fast.py](part2_fast.py)).


```
set b 93
set c b
jnz a 2 # always
	jnz 1 5 # Never executed
mul b 100
sub b -100000  # b = 109300
set c b
sub c -17000   # c = 126300

set f 1 # Last line goes here
set d 2
	set e 2
		set g d
		mul g e
		sub g b
		jnz g 2 # sets f == 0 once e 1/2 b
		set f 0
		sub e -1
		set g e
		sub g b
		jnz g -8 # adds 1 to e, until e == b
	sub d -1
	set g d
	sub g b # Repeat other loop, another b times until d == b
	jnz g -13
jnz f 2 # Should be 0 first time this hits.
	sub h -1
set g b 
sub g c
jnz g 2
jnz 1 3 # END
sub b -17 # Add 17
jnz 1 -23 # Goes to set f 1
```
