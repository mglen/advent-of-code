#include <stdio.h>

int main() {
	int a = 1;
	int b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;

	b = 93;
	c = b;
	if (a != 0) {
		goto FROM3;
	}
	goto FROM4;
FROM3:  b = b * 100;
	b = b + 100000;
	c = b;
	c = c + 17000;
FROM4:  f = 1;
	d = 2;
FROM24: e = 2;
FROM20: g = d;
	g = g * e;
	g = g - b;
	if (g != 0) {
		goto FROM15;
	}
	f = 0;
FROM15: e = e + 1;
	g = e;
	g = g - b;
	if (g != 0) {
		goto FROM20;
	}
	d = d + 1;
	g = d;
	g = g - b;
	if (g != 0) {
		goto FROM24;
	}
	if (f != 0) {
		goto FROM25;
	}
	h = h + 1;
FROM25: g = b;
	g = g - c;
	if (g != 0) {
		goto FROM29;
	}
	goto FROM30;
FROM29: b = b + 17;
	goto FROM4; /* FROM32 */

FROM30: printf("done, value of h: %d\n", h);
}
