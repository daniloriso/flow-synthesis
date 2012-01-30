library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity REG_UINT_32 is
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC;
		INPUT_3  :in  STD_LOGIC;
		OUTPUT_1 :out STD_LOGIC_VECTOR(31 DOWNTO 0)
		);
	end;

architecture behavior of REG_UINT_32 is
	SIGNAL data : STD_LOGIC_VECTOR(31 DOWNTO 0);
begin
	process (INPUT_2, INPUT_3)
	begin
		IF( INPUT_3 = '1' ) THEN
			data <= "00000000000000000000000000000000";
		ELSIF( INPUT_2'EVENT AND INPUT_2 = '1' ) THEN
			data <= INPUT_1;
		END IF;
		
		OUTPUT_1 <= data;
	end process;
end;
