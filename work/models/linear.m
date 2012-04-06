function [y, as] = linear(x, a, b)

as(0) = assert(a + b > 0);
as(1) = assert(x > 10);
y = a * x + b;
as(2) = assert(y > 0);