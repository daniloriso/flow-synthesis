
--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
library work; 
use work.all;

entity EQUAL_IEEE_754 is 
port(
	INPUT_1  : in std_logic_vector(31 downto 0);
	INPUT_2  : in std_logic_vector(31 downto 0);
	OUTPUT_1 : out std_logic_vector(31 downto 0)
);
end EQUAL_IEEE_754;
  

architecture corps of EQUAL_IEEE_754 is
begin

	process(INPUT_1, INPUT_2)
		constant vrai : std_logic_vector(31 downto 0) := "00111111100000000000000000000000";
		constant faux : std_logic_vector(31 downto 0) := "00000000000000000000000000000000";
   begin
		if (INPUT_1 = INPUT_2) then
			OUTPUT_1 <= vrai;
		else 
			OUTPUT_1 <= faux;
		end if;
	end process;

end corps;