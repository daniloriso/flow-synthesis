library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity ABS_UINT_16 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
	end;

architecture behavior of ABS_UINT_16 is
begin
	process (INPUT_1)
		VARIABLE tmp : UNSIGNED(15 DOWNTO 0);
	begin
		tmp := UNSIGNED( INPUT_1 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp );
	end process;
end;
