
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--
--
--  Synthese OPAR Version 1 (Demo)
--
--             Decaleur
-- synthétisé le Lundi 18 Juin 2007
--
--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--

library IEEE;
use IEEE.STD_LOGIC_1164.All;

entity Shift is
port ( Entree : in  Std_Logic_Vector (23 downto 0 ) ;   -- E : input
       D : in  Std_Logic_Vector (4 downto 0 ) ;    -- D : positions
		 --clk:in std_logic;
       S : out Std_Logic_Vector (26 downto 0 ) ) ; -- Z : E shifted D positions
end Shift ;

architecture structural of Shift is
signal sticky : Std_Logic ; -- sticky bit
signal P0, P1, P2, P3, P4, P5 : Std_Logic_Vector (25 downto 0) ; -- internal signals

begin
--process (clk)
--begin
--if clk'event and clk='1' then

P5 <= Entree & "00" ; -- guard and round bits

with D(4) select P4 <= P5 when '0' , "0000000000000000" & P5(25 downto  16) when others ;

with D(3) select P3 <= P4 when '0' , "00000000" & P4(25 downto 8 ) when others ;

with D(2) select P2 <= P3 when '0' , "0000" & P3(25 downto 4) when others ;

with D(1) select P1 <= P2 when '0' , "00" & P2(25 downto 2) when others ;

with D(0) select P0 <= P1 when '0' , "0" & P1(25 downto 1) when others ;

sticky <= ((P5(15) or P5(14) or P5(13) or P5(12) or P5(11) or P5(10) or P5(9) or P5(8) or
            P5(7) or P5(6) or P5(5) or P5(4) or P5(3) or P5(2) or P5(1) or P5(0)) and D(4)) or
          ((P4(7) or P4(6) or P4(5) or P4(4) or P4(3) or P4(2) or P4(1) or P4(0)) and D(3)) or
          ((P3(3) or P3(2) or P3(1) or P3(0)) and D(2)) or
          ((P2(1) or P2(0)) and D(1)) or (P1(0) and D(0)) ;
S <= P0 & sticky ;
--end if;
--end process;
end structural ;