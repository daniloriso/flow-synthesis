library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity DEMUX4_UINT_32 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(31 DOWNTO 0);
		OUTPUT_2 :out STD_LOGIC_VECTOR(31 DOWNTO 0);
		OUTPUT_3 :out STD_LOGIC_VECTOR(31 DOWNTO 0);
		OUTPUT_4 :out STD_LOGIC_VECTOR(31 DOWNTO 0)
		);
	end;

architecture behavior of DEMUX4_UINT_32 is
begin
	process (INPUT_1, INPUT_2)
	begin
		CASE INPUT_2 IS
			WHEN "00"   => OUTPUT_1 <= INPUT_1;
			WHEN "01"   => OUTPUT_2 <= INPUT_1;
			WHEN "10"   => OUTPUT_3 <= INPUT_1;
			WHEN "11"   => OUTPUT_4 <= INPUT_1;
			WHEN OTHERS => NULL;
		END CASE;
	end process;
end;
