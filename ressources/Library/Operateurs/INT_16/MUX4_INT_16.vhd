library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity MUX4_INT_16 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_3  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_4  :in  STD_LOGIC_VECTOR(15 DOWNTO 0);
		INPUT_5  :in  STD_LOGIC_VECTOR(1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
	end;

architecture behavior of MUX4_INT_16 is
begin
	process (INPUT_1, INPUT_2, INPUT_3, INPUT_4, INPUT_5)
	begin
		CASE INPUT_5 IS
			WHEN "00" => OUTPUT_1 <= INPUT_1;
			WHEN "01" => OUTPUT_1 <= INPUT_2;
			WHEN "10" => OUTPUT_1 <= INPUT_3;
			WHEN "11" => OUTPUT_1 <= INPUT_4;
		END CASE;
	end process;
end;
