--IYAMBA ASSA Stage VHDL Juin 2007
--Unité d'addition:bloc complet
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use work.txt_util.all;

library work; 
use work.all;

entity SUB_IEEE_754 is
	port(
		INPUT_1  : in  std_logic_vector(31 downto 0);
		INPUT_2  : in  std_logic_vector(31 downto 0);
		OUTPUT_1 : out std_logic_vector(31 downto 0)
	);
END SUB_IEEE_754;


architecture corps of SUB_IEEE_754 is
	component ADD_IEEE_754 is
	port(
		INPUT_1  : in std_logic_vector(31 downto 0);
      INPUT_2  : in std_logic_vector(31 downto 0);
      OUTPUT_1 : out std_logic_vector(31 downto 0)
	);
	end component;

	signal TMP:std_logic_vector(31 downto 0);

BEGIN
    
    TMP <= not(INPUT_2(31)) & INPUT_2(30 downto 0);
    
    add: ADD_IEEE_754 port map (INPUT_1, TMP, OUTPUT_1);
	 
END corps;

