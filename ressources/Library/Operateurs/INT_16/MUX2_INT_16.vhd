library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity MUX2_INT_16 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_3  :in  STD_LOGIC;
		OUTPUT_1 :out STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
	end;

architecture behavior of MUX2_INT_16 is
begin
	process (INPUT_1, INPUT_2)
	begin
		IF( INPUT_3 = '0' ) THEN
			OUTPUT_1 <= INPUT_1;
		ELSE
			OUTPUT_1 <= INPUT_2;
		END IF;
	end process;
end;
