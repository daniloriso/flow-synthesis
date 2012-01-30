--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--
--
--  Synthese OPAR Version 1 (Demo)
--
--     Compteur de zeros en tete
-- synthétisé le Lundi 18 Juin 2007
--
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--

library IEEE;
use IEEE.STD_LOGIC_1164.All;

entity ZLC is
port ( E : in  Std_Logic_Vector (31 downto 0 ) ;  -- E : bit string
		--clk: in std_logic;
       Z : out Std_Logic_Vector (5 downto 0 ) ) ; -- Z : number of leading zeroes
end ZLC ;

architecture structural of ZLC is
signal P : Std_Logic_Vector (119 downto 0) ; -- internal signals

begin
--process (clk)
--begin
	--if clk'event and clk='1' then

P(119 downto 88) <= not E ; -- row of inverters

 -- row 1
P(87) <= P(119) and P(118) ; P(86) <= P(119) and not P(118) ; 
P(85) <= P(117) and P(116) ; P(84) <= P(117) and not P(116) ; 
P(83) <= P(115) and P(114) ; P(82) <= P(115) and not P(114) ; 
P(81) <= P(113) and P(112) ; P(80) <= P(113) and not P(112) ; 
P(79) <= P(111) and P(110) ; P(78) <= P(111) and not P(110) ; 
P(77) <= P(109) and P(108) ; P(76) <= P(109) and not P(108) ; 
P(75) <= P(107) and P(106) ; P(74) <= P(107) and not P(106) ; 
P(73) <= P(105) and P(104) ; P(72) <= P(105) and not P(104) ; 
P(71) <= P(103) and P(102) ; P(70) <= P(103) and not P(102) ; 
P(69) <= P(101) and P(100) ; P(68) <= P(101) and not P(100) ; 
P(67) <= P(99) and P(98) ; P(66) <= P(99) and not P(98) ; 
P(65) <= P(97) and P(96) ; P(64) <= P(97) and not P(96) ; 
P(63) <= P(95) and P(94) ; P(62) <= P(95) and not P(94) ; 
P(61) <= P(93) and P(92) ; P(60) <= P(93) and not P(92) ; 
P(59) <= P(91) and P(90) ; P(58) <= P(91) and not P(90) ; 
P(57) <= P(89) and P(88) ; P(56) <= P(89) and not P(88) ; 

 -- row 2
P(55) <= P(87) and P(85) ; P(54) <= P(87) and not P(85) ; P(53) <= P(86) or (P(87) and P(84)) ; 
P(52) <= P(83) and P(81) ; P(51) <= P(83) and not P(81) ; P(50) <= P(82) or (P(83) and P(80)) ; 
P(49) <= P(79) and P(77) ; P(48) <= P(79) and not P(77) ; P(47) <= P(78) or (P(79) and P(76)) ; 
P(46) <= P(75) and P(73) ; P(45) <= P(75) and not P(73) ; P(44) <= P(74) or (P(75) and P(72)) ; 
P(43) <= P(71) and P(69) ; P(42) <= P(71) and not P(69) ; P(41) <= P(70) or (P(71) and P(68)) ; 
P(40) <= P(67) and P(65) ; P(39) <= P(67) and not P(65) ; P(38) <= P(66) or (P(67) and P(64)) ; 
P(37) <= P(63) and P(61) ; P(36) <= P(63) and not P(61) ; P(35) <= P(62) or (P(63) and P(60)) ; 
P(34) <= P(59) and P(57) ; P(33) <= P(59) and not P(57) ; P(32) <= P(58) or (P(59) and P(56)) ; 

 -- row 3
P(31) <= P(55) and P(52) ; P(30) <= P(55) and not P(52) ; P(29) <= P(54) or (P(55) and P(51)) ; P(28) <= P(53) or (P(55) and P(50)) ; 
P(27) <= P(49) and P(46) ; P(26) <= P(49) and not P(46) ; P(25) <= P(48) or (P(49) and P(45)) ; P(24) <= P(47) or (P(49) and P(44)) ; 
P(23) <= P(43) and P(40) ; P(22) <= P(43) and not P(40) ; P(21) <= P(42) or (P(43) and P(39)) ; P(20) <= P(41) or (P(43) and P(38)) ; 
P(19) <= P(37) and P(34) ; P(18) <= P(37) and not P(34) ; P(17) <= P(36) or (P(37) and P(33)) ; P(16) <= P(35) or (P(37) and P(32)) ; 

 -- row 4
P(15) <= P(31) and P(27) ; P(14) <= P(31) and not P(27) ; P(13) <= P(30) or (P(31) and P(26)) ; P(12) <= P(29) or (P(31) and P(25)) ; P(11) <= P(28) or (P(31) and P(24)) ; 
P(10) <= P(23) and P(19) ; P(9) <= P(23) and not P(19) ; P(8) <= P(22) or (P(23) and P(18)) ; P(7) <= P(21) or (P(23) and P(17)) ; P(6) <= P(20) or (P(23) and P(16)) ; 

 -- row 5
P(5) <= P(15) and P(10) ; P(4) <= P(15) and not P(10) ; P(3) <= P(14) or (P(15) and P(9)) ; P(2) <= P(13) or (P(15) and P(8)) ; P(1) <= P(12) or (P(15) and P(7)) ; P(0) <= P(11) or (P(15) and P(6)) ; 

Z <= P(5 downto 0) ;
--end if;
--end process;
end structural;
