function [y, ctr] = f(a,b,c)

ctr(0) = assert(a > b + c);
t1 = 10 * a;
t2 = t1 * b;
t3 = t2 * c;
y = t1 + t2 + t3;
ctr(1) = assert(y > 0);