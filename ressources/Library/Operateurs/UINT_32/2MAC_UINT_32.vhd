library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library work;

entity D_MAC_UINT_32 is
	port (
		INPUT_1  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		INPUT_3  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		INPUT_4  :in  STD_LOGIC_VECTOR(31 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(31 DOWNTO 0)
		);
	end;

architecture behavior of D_MAC_UINT_32 is
begin
	process (INPUT_1, INPUT_2, INPUT_3, INPUT_4)
		VARIABLE tmp1 : UNSIGNED(63 DOWNTO 0);
		VARIABLE tmp2 : UNSIGNED(63 DOWNTO 0);
		VARIABLE tmp3 : UNSIGNED(31DOWNTO 0);
	begin
		tmp1 := UNSIGNED( INPUT_1 ) * UNSIGNED( INPUT_2 );
		tmp2 := UNSIGNED( INPUT_3 ) * UNSIGNED( INPUT_4 );
		tmp3 := tmp1(31 DOWNTO 0)   + tmp2(31 DOWNTO 0);
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp3 );
	end process;
end;
