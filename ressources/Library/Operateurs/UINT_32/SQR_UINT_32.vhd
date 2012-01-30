library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity SQR_UINT_32 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(31 DOWNTO 0)
		);
	end;

architecture behavior of SQR_UINT_32 is
begin
	process (INPUT_1)
		VARIABLE tmp : UNSIGNED(63 DOWNTO 0);
	begin
		tmp := UNSIGNED( INPUT_1 ) * UNSIGNED( INPUT_1 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp(31 DOWNTO 0) );
	end process;
end;
