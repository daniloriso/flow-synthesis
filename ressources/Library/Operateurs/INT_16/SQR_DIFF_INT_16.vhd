library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity SQR_SUB_INT_16 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
	end;

architecture behavior of SQR_SUB_INT_16 is
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp1 : UNSIGNED(15 DOWNTO 0);
		VARIABLE tmp2 : UNSIGNED(31 DOWNTO 0);
	begin
		if( UNSIGNED( INPUT_1 ) > UNSIGNED( INPUT_2 ) ) then
			tmp1 := UNSIGNED( INPUT_1 ) - UNSIGNED( INPUT_2 );
		else
			tmp1 := UNSIGNED( INPUT_2 ) - UNSIGNED( INPUT_1 );
		end if;
		tmp2 := tmp1 * tmp1;
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp2(15 DOWNTO 0) );
	end process;
end;
