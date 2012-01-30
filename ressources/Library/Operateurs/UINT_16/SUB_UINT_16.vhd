library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity SUB_UINT_16 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
	end;

architecture behavior of SUB_UINT_16 is
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp : UNSIGNED(15 DOWNTO 0);
	begin
		tmp := UNSIGNED( INPUT_1 ) - UNSIGNED( INPUT_2 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp );
	end process;
end;
