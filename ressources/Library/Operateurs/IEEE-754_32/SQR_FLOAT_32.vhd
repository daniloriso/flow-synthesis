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


entity SQR_FLOAT32 is
port( INPUT_1: in std_logic_vector(31 downto 0);
      OUTPUT_1: out std_logic_vector(31 downto 0)
      );
end SQR_FLOAT32;

architecture corps of SQR_FLOAT32 is
	component MUL_FLOAT32 is
		 port( INPUT_1: in std_logic_vector(31 downto 0);
			INPUT_2: in std_logic_vector(31 downto 0);
			OUTPUT_1: out std_logic_vector(31 downto 0)
			);
	end component;
begin
    MULT_OPR: MUL_FLOAT32 port map (INPUT_1, INPUT_1, OUTPUT_1);
end corps;

