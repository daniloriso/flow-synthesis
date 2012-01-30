function [Y] = dct2d( X )

% CONSTANT DECLARATION
N=8;
pi = 3.141596;
dyn = 12; % bits per coefficient

% INIT OF THE COEFFICIENT ARRAY
ref = sqrt(2/N);
for i=1:N-1
   % C(i)= Qn(ref * cos(i*pi/16), dyn);
   C(i) = i;
end

% THE COMPUTATION CORE
Y(1) = C(4) *((X(1)+X(8)) +        (X(2)+X(7)) +        (X(3)+X(6)) +        (X(4)+X(5)));
Y(3) = C(2) * (X(1)+X(8)) + C(6) * (X(2)+X(7)) - C(6) * (X(3)+X(6)) - C(2) * (X(4)+X(5));
Y(5) = C(4) * (X(1)+X(8)) - C(4) * (X(2)+X(7)) - C(4) * (X(3)+X(6)) + C(4) * (X(4)+X(5));
Y(7) = C(6) * (X(1)+X(8)) - C(2) * (X(2)+X(7)) + C(2) * (X(3)+X(6)) - C(6) * (X(4)+X(5));
Y(2) = C(1) * (X(1)-X(8)) + C(3) * (X(2)-X(7)) + C(5) * (X(3)-X(6)) + C(7) * (X(4)-X(5));
Y(4) = C(3) * (X(1)-X(8)) - C(7) * (X(2)-X(7)) - C(1) * (X(3)-X(6)) - C(5) * (X(4)-X(5));
Y(6) = C(5) * (X(1)-X(8)) - C(1) * (X(2)-X(7)) + C(7) * (X(3)-X(6)) + C(3) * (X(4)-X(5));
Y(8) = C(7) * (X(1)-X(8)) - C(5) * (X(2)-X(7)) + C(3) * (X(3)-X(6)) - C(1) * (X(4)-X(5));