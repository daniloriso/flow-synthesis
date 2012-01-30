library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity ABSUB_UINT_16 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
	end;

architecture behavior of ABSUB_UINT_16 is
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp : SIGNED(15 DOWNTO 0);
	begin
		if( UNSIGNED( INPUT_1 ) > UNSIGNED( INPUT_2 )) then
			tmp := UNSIGNED( INPUT_1 ) - UNSIGNED( INPUT_2 );
		else
			tmp := UNSIGNED( INPUT_2 ) - UNSIGNED( INPUT_1 );
		end if;
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp(15 DOWNTO 0) );
	end process;
end;
