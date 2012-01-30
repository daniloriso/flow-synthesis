--IYAMBA ASSA Stage VHDL Juin 2007
--Unité d'addition:bloc complet
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

library work; 
use work.all;


entity ABS_FLOAT32 is
port( INPUT_1: in std_logic_vector(31 downto 0);
      OUTPUT_1: out std_logic_vector(31 downto 0)
      );
end ABS_FLOAT32;


architecture corps of ABS_FLOAT32 is
begin

    OUTPUT_1 <= '0' & INPUT_1(30 DOWNTO 0);

end corps;

