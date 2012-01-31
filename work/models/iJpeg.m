function [R,G,B, ctrPre1, ctrPre2, ctrPre3, ctrPre4, ctrPre5, ctrPre6, ctrInt1, ctrInt2, ctrPost1, ctrPost2, ctrPost3, ctrPost4, ctrPost5, ctrPost6] = iJpegComp(Y,Cb,Cr)

for ii = 1:8
	for jj = 1:8
		ctrPre1(ii, jj) = assert( Y(ii,jj) >= 0);
		ctrPre2(ii, jj) = assert( Y(ii,jj) < 2147483640);
		ctrPre3(ii, jj) = assert( Cb(ii,jj) >= 0);
		ctrPre4(ii, jj) = assert( Cb(ii,jj) < 2147483640);
		ctrPre5(ii, jj) = assert( Cr(ii,jj) >= 0);
		ctrPre6(ii, jj) = assert( Cr(ii,jj) < 2147483640);
	end
end

n = 8;
dyn = 12;
qualite = 80;

%
% QUANTIFICATION INVERSE DES DONNEES A L'AIDE DU FACTEUR DE QUALITE SOUHAITE
%
K= 25 -(qualite/4);
for y=1:n
	for x=1:n
		qtable(y,x) = 1 + K * (1 + x + y);
	end
end
for y=1:n
	for x=1:n
		Yft(y,x)  = Y(y,x)  * qtable(y,x);
		Cbft(y,x) = Cb(y,x) * qtable(y,x);
		Crft(y,x) = Cr(y,x) * qtable(y,x);
	end
end

%
% ON CONVERTIT LES DONNEES DU FORMAT RGB => YCbCr
%
pi = 3.141596;
for i=1:n-1
   C(i)= Qn(sqrt(2/n)  *  cos(i * pi/16), dyn);
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% THE 2d iDCT CORE for the Y data
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
for j =1:8
	X_pair_0   = ( C(4)*Yft(j,1) + C(2)*Yft(j,3) ) + ( C(4)*Yft(j,5) + C(6)*Yft(j,7) );
	X_pair_1   = ( C(4)*Yft(j,1) + C(6)*Yft(j,3) ) - ( C(4)*Yft(j,5) + C(2)*Yft(j,7) );
	X_pair_2   = ( C(4)*Yft(j,1) - C(6)*Yft(j,3) ) - ( C(4)*Yft(j,5) - C(2)*Yft(j,7) );
	X_pair_3   = ( C(4)*Yft(j,1) - C(2)*Yft(j,3) ) + ( C(4)*Yft(j,5) - C(6)*Yft(j,7) );
	X_impair_0 = ( C(1)*Yft(j,2) + C(3)*Yft(j,4) ) + ( C(5)*Yft(j,6) + C(7)*Yft(j,8) );
	X_impair_1 = ( C(3)*Yft(j,2) - C(7)*Yft(j,4) ) - ( C(1)*Yft(j,6) + C(5)*Yft(j,8) );
	X_impair_2 = ( C(5)*Yft(j,2) - C(1)*Yft(j,4) ) + ( C(7)*Yft(j,6) + C(3)*Yft(j,8) );
	X_impair_3 = ( C(7)*Yft(j,2) - C(5)*Yft(j,4) ) + ( C(3)*Yft(j,6) - C(1)*Yft(j,8) );
	T(j,1)     = X_pair_0 + X_impair_0;
	T(j,2)     = X_pair_1 + X_impair_1;
	T(j,3)     = X_pair_2 + X_impair_2;
	T(j,4)     = X_pair_3 + X_impair_3;
	T(j,8)     = X_pair_0 - X_impair_0;
	T(j,7)     = X_pair_1 - X_impair_1;
	T(j,6)     = X_pair_2 - X_impair_2;
	T(j,5)     = X_pair_3 - X_impair_3;
end

% ON RECADRE LES DONNEES A GAUCHE (AVEC ARRONDI)
for y=1:n
	for x=1:n
		T(y,x)  = rshift( T(y,x),  dyn/2 );
	end
end

for i =1:8
	T_pair_0   = ( C(4)*T(1,i) + C(2)*T(3,i) ) + ( C(4)*T(5,i) + C(6)*T(7,i) );
	T_pair_1   = ( C(4)*T(1,i) + C(6)*T(3,i) ) - ( C(4)*T(5,i) + C(2)*T(7,i) );
	T_pair_2   = ( C(4)*T(1,i) - C(6)*T(3,i) ) - ( C(4)*T(5,i) - C(2)*T(7,i) );
	T_pair_3   = ( C(4)*T(1,i) - C(2)*T(3,i) ) + ( C(4)*T(5,i) - C(6)*T(7,i) );
	T_impair_0 = ( C(1)*T(2,i) + C(3)*T(4,i) ) + ( C(5)*T(6,i) + C(7)*T(8,i) );
	T_impair_1 = ( C(3)*T(2,i) - C(7)*T(4,i) ) - ( C(1)*T(6,i) + C(5)*T(8,i) );
	T_impair_2 = ( C(5)*T(2,i) - C(1)*T(4,i) ) + ( C(7)*T(6,i) + C(3)*T(8,i) );
	T_impair_3 = ( C(7)*T(2,i) - C(5)*T(4,i) ) + ( C(3)*T(6,i) - C(1)*T(8,i) );
	Yv(1,i)    = T_pair_0 + T_impair_0;
	Yv(2,i)    = T_pair_1 + T_impair_1;
	Yv(3,i)    = T_pair_2 + T_impair_2;
	Yv(4,i)    = T_pair_3 + T_impair_3;
	Yv(8,i)    = T_pair_0 - T_impair_0;
	Yv(7,i)    = T_pair_1 - T_impair_1;
	Yv(6,i)    = T_pair_2 - T_impair_2;
	Yv(5,i)    = T_pair_3 - T_impair_3;
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% THE 2d DCT CORE for the Cr data
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
for j =1:8
	X_pair_0   = ( C(4)*Cbft(j,1) + C(2)*Cbft(j,3) ) + ( C(4)*Cbft(j,5) + C(6)*Cbft(j,7) );
	X_pair_1   = ( C(4)*Cbft(j,1) + C(6)*Cbft(j,3) ) - ( C(4)*Cbft(j,5) + C(2)*Cbft(j,7) );
	X_pair_2   = ( C(4)*Cbft(j,1) - C(6)*Cbft(j,3) ) - ( C(4)*Cbft(j,5) - C(2)*Cbft(j,7) );
	X_pair_3   = ( C(4)*Cbft(j,1) - C(2)*Cbft(j,3) ) + ( C(4)*Cbft(j,5) - C(6)*Cbft(j,7) );
	X_impair_0 = ( C(1)*Cbft(j,2) + C(3)*Cbft(j,4) ) + ( C(5)*Cbft(j,6) + C(7)*Cbft(j,8) );
	X_impair_1 = ( C(3)*Cbft(j,2) - C(7)*Cbft(j,4) ) - ( C(1)*Cbft(j,6) + C(5)*Cbft(j,8) );
	X_impair_2 = ( C(5)*Cbft(j,2) - C(1)*Cbft(j,4) ) + ( C(7)*Cbft(j,6) + C(3)*Cbft(j,8) );
	X_impair_3 = ( C(7)*Cbft(j,2) - C(5)*Cbft(j,4) ) + ( C(3)*Cbft(j,6) - C(1)*Cbft(j,8) );
	T(j,1) = X_pair_0 + X_impair_0;
	T(j,2) = X_pair_1 + X_impair_1;
	T(j,3) = X_pair_2 + X_impair_2;
	T(j,4) = X_pair_3 + X_impair_3;
	T(j,8) = X_pair_0 - X_impair_0;
	T(j,7) = X_pair_1 - X_impair_1;
	T(j,6) = X_pair_2 - X_impair_2;
	T(j,5) = X_pair_3 - X_impair_3;
end

% ON RECADRE LES DONNEES A GAUCHE (AVEC ARRONDI)
for y=1:n
	for x=1:n
		T(y,x)  = rshift( T(y,x),  dyn/2 );
	end
end


for ii = 1:8
	for jj = 1:8
		ctrInt1(ii, jj) = assert( T(ii,jj) >= -2147483640);
		ctrInt2(ii, jj) = assert( T(ii,jj) < 2147483640);
	end
end

for i =1:8
	T_pair_0   = ( C(4)*T(1,i) + C(2)*T(3,i) ) + ( C(4)*T(5,i) + C(6)*T(7,i) );
	T_pair_1   = ( C(4)*T(1,i) + C(6)*T(3,i) ) - ( C(4)*T(5,i) + C(2)*T(7,i) );
	T_pair_2   = ( C(4)*T(1,i) - C(6)*T(3,i) ) - ( C(4)*T(5,i) - C(2)*T(7,i) );
	T_pair_3   = ( C(4)*T(1,i) - C(2)*T(3,i) ) + ( C(4)*T(5,i) - C(6)*T(7,i) );
	T_impair_0 = ( C(1)*T(2,i) + C(3)*T(4,i) ) + ( C(5)*T(6,i) + C(7)*T(8,i) );
	T_impair_1 = ( C(3)*T(2,i) - C(7)*T(4,i) ) - ( C(1)*T(6,i) + C(5)*T(8,i) );
	T_impair_2 = ( C(5)*T(2,i) - C(1)*T(4,i) ) + ( C(7)*T(6,i) + C(3)*T(8,i) );
	T_impair_3 = ( C(7)*T(2,i) - C(5)*T(4,i) ) + ( C(3)*T(6,i) - C(1)*T(8,i) );
	Cbv(1,i)    = T_pair_0 + T_impair_0;
	Cbv(2,i)    = T_pair_1 + T_impair_1;
	Cbv(3,i)    = T_pair_2 + T_impair_2;
	Cbv(4,i)    = T_pair_3 + T_impair_3;
	Cbv(8,i)    = T_pair_0 - T_impair_0;
	Cbv(7,i)    = T_pair_1 - T_impair_1;
	Cbv(6,i)    = T_pair_2 - T_impair_2;
	Cbv(5,i)    = T_pair_3 - T_impair_3;
end


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% THE 2d DCT CORE for the Cr data
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
for j =1:8
	X_pair_0   = ( C(4)*Crft(j,1) + C(2)*Crft(j,3) ) + ( C(4)*Crft(j,5) + C(6)*Crft(j,7) );
	X_pair_1   = ( C(4)*Crft(j,1) + C(6)*Crft(j,3) ) - ( C(4)*Crft(j,5) + C(2)*Crft(j,7) );
	X_pair_2   = ( C(4)*Crft(j,1) - C(6)*Crft(j,3) ) - ( C(4)*Crft(j,5) - C(2)*Crft(j,7) );
	X_pair_3   = ( C(4)*Crft(j,1) - C(2)*Crft(j,3) ) + ( C(4)*Crft(j,5) - C(6)*Crft(j,7) );
	X_impair_0 = ( C(1)*Crft(j,2) + C(3)*Crft(j,4) ) + ( C(5)*Crft(j,6) + C(7)*Crft(j,8) );
	X_impair_1 = ( C(3)*Crft(j,2) - C(7)*Crft(j,4) ) - ( C(1)*Crft(j,6) + C(5)*Crft(j,8) );
	X_impair_2 = ( C(5)*Crft(j,2) - C(1)*Crft(j,4) ) + ( C(7)*Crft(j,6) + C(3)*Crft(j,8) );
	X_impair_3 = ( C(7)*Crft(j,2) - C(5)*Crft(j,4) ) + ( C(3)*Crft(j,6) - C(1)*Crft(j,8) );
	T(j,1) = X_pair_0 + X_impair_0;
	T(j,2) = X_pair_1 + X_impair_1;
	T(j,3) = X_pair_2 + X_impair_2;
	T(j,4) = X_pair_3 + X_impair_3;
	T(j,8) = X_pair_0 - X_impair_0;
	T(j,7) = X_pair_1 - X_impair_1;
	T(j,6) = X_pair_2 - X_impair_2;
	T(j,5) = X_pair_3 - X_impair_3;
end

% ON RECADRE LES DONNEES A GAUCHE (AVEC ARRONDI)
for y=1:n
	for x=1:n
		T(y,x)  = rshift( T(y,x),  dyn/2 );
	end
end

for i =1:8
	T_pair_0   = ( C(4)*T(1,i) + C(2)*T(3,i) ) + ( C(4)*T(5,i) + C(6)*T(7,i) );
	T_pair_1   = ( C(4)*T(1,i) + C(6)*T(3,i) ) - ( C(4)*T(5,i) + C(2)*T(7,i) );
	T_pair_2   = ( C(4)*T(1,i) - C(6)*T(3,i) ) - ( C(4)*T(5,i) - C(2)*T(7,i) );
	T_pair_3   = ( C(4)*T(1,i) - C(2)*T(3,i) ) + ( C(4)*T(5,i) - C(6)*T(7,i) );
	T_impair_0 = ( C(1)*T(2,i) + C(3)*T(4,i) ) + ( C(5)*T(6,i) + C(7)*T(8,i) );
	T_impair_1 = ( C(3)*T(2,i) - C(7)*T(4,i) ) - ( C(1)*T(6,i) + C(5)*T(8,i) );
	T_impair_2 = ( C(5)*T(2,i) - C(1)*T(4,i) ) + ( C(7)*T(6,i) + C(3)*T(8,i) );
	T_impair_3 = ( C(7)*T(2,i) - C(5)*T(4,i) ) + ( C(3)*T(6,i) - C(1)*T(8,i) );
	Crv(1,i)    = T_pair_0 + T_impair_0;
	Crv(2,i)    = T_pair_1 + T_impair_1;
	Crv(3,i)    = T_pair_2 + T_impair_2;
	Crv(4,i)    = T_pair_3 + T_impair_3;
	Crv(8,i)    = T_pair_0 - T_impair_0;
	Crv(7,i)    = T_pair_1 - T_impair_1;
	Crv(6,i)    = T_pair_2 - T_impair_2;
	Crv(5,i)    = T_pair_3 - T_impair_3;
end


% ON RECADRE LES DONNEES A GAUCHE (AVEC ARRONDI)
for y=1:n
	for x=1:n
		Yv(y,x)  = rshift( Yv(y,x),  dyn );
		Cbv(y,x) = rshift( Cbv(y,x), dyn ); 
		Crv(y,x) = rshift( Crv(y,x), dyn );
	end
end

% ON CONVERTIT LES DONNEES DU FORMAT YCbCr => RGB
for y=1:n
	for x=1:n
		Rf(y,x) =  Qn(1,dyn) * Yv(y,x)                                       + Qn(1.402,   dyn) * (Crv(y,x) - 128);
		Gf(y,x) =  Qn(1,dyn) * Yv(y,x) - Qn(0.34414, dyn) * (Cbv(y,x) - 128) - Qn(0.71414, dyn) * (Crv(y,x) - 128);
		Bf(y,x) =  Qn(1,dyn) * Yv(y,x) + Qn(1.772,   dyn) * (Cbv(y,x) - 128);
	end
end

% ON RECADRE LES DONNEES A GAUCHE (AVEC ARRONDI)
for y=1:n
	for x=1:n
		Rf(y,x) = rshift( Rf(y,x), 3*dyn/2 );
		Gf(y,x) = rshift( Gf(y,x), 3*dyn/2 ); 
		Bf(y,x) = rshift( Bf(y,x), 3*dyn/2 );
	end
end

% ON S'ASSURE QU'ELLES SONT DANS LE BON INTERVALLE
for y=1:n
	for x=1:n
		R(y,x) = max(min(Rf(y,x),255),0);
		G(y,x) = max(min(Gf(y,x),255),0);
		B(y,x) = max(min(Bf(y,x),255),0);
	end
end



for ii = 1:8
	for jj = 1:8
		ctrPost1(ii, jj) = assert( R(ii,jj) >= 0);
		ctrPost2(ii, jj) = assert( R(ii,jj) < 2147483640);
		ctrPost3(ii, jj) = assert( G(ii,jj) >= 0);
		ctrPost4(ii, jj) = assert( G(ii,jj) < 2147483640);
		ctrPost5(ii, jj) = assert( B(ii,jj) >= 0);
		ctrPost6(ii, jj) = assert( B(ii,jj) < 2147483640);
	end
end
