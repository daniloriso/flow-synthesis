


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity ABS_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of ABS_SIGNED IS
begin
	process (INPUT_1)
		VARIABLE tmp : SIGNED(BITWIDTH-1 DOWNTO 0);
	begin
		tmp := ABS( SIGNED( INPUT_1 ) );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp );
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity ABSUB_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of ABSUB_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp : SIGNED(BITWIDTH-1 DOWNTO 0);
	begin
		tmp := ABS( SIGNED( INPUT_1 ) - SIGNED( INPUT_2 ) );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp(BITWIDTH-1 DOWNTO 0) );
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity DEMUX2_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC;
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_2 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of DEMUX2_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
	begin
		IF( INPUT_2 = '0' ) THEN
			OUTPUT_1 <= INPUT_1;
			OUTPUT_2 <= (OTHERS=>'Z');
		ELSE
			OUTPUT_1 <= (OTHERS=>'Z');
			OUTPUT_2 <= INPUT_1;
		END IF;
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity DEMUX4_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_2 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_3 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_4 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of DEMUX4_SIGNED IS
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


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
entity MAC_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_3  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of MAC_SIGNED IS
begin
	process (INPUT_1, INPUT_2, INPUT_3)
		VARIABLE tmp : SIGNED((2*BITWIDTH)-1 DOWNTO 0);
	begin
		tmp := SIGNED( INPUT_1 ) * SIGNED( INPUT_2 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( (tmp((2*BITWIDTH)-1)&tmp(BITWIDTH-2 DOWNTO 0)) +  SIGNED( INPUT_3 ));
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MAX_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of MAX_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
	begin
		IF SIGNED( INPUT_1 ) < SIGNED( INPUT_2 ) THEN
			OUTPUT_1 <= STD_LOGIC_VECTOR( INPUT_1 );
		ELSE
			OUTPUT_1 <= STD_LOGIC_VECTOR( INPUT_2 );
		END IF;
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MIN_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of MIN_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
	begin
		IF SIGNED( INPUT_1 ) > SIGNED( INPUT_2 ) THEN
			OUTPUT_1 <= STD_LOGIC_VECTOR( INPUT_1 );
		ELSE
			OUTPUT_1 <= STD_LOGIC_VECTOR( INPUT_2 );
		END IF;
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MUL_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of MUL_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp : SIGNED((2*BITWIDTH)-1 DOWNTO 0);
	begin
		tmp := SIGNED( INPUT_1 ) * SIGNED( INPUT_2 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp(BITWIDTH-1 DOWNTO 0) );
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MUX2_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_3  :in  STD_LOGIC;
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of MUX2_SIGNED IS
begin
	process (INPUT_1, INPUT_2, INPUT_3)
	begin
		IF( INPUT_3 = '0' ) THEN
			OUTPUT_1 <= INPUT_1;
		ELSE
			OUTPUT_1 <= INPUT_2;
		END IF;
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MUX4_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_3  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_4  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_5  :in  STD_LOGIC_VECTOR(1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of MUX4_SIGNED IS
begin
	process (INPUT_1, INPUT_2, INPUT_3, INPUT_4, INPUT_5)
	begin
		CASE INPUT_5 IS
			WHEN "00" => OUTPUT_1 <= INPUT_1;
			WHEN "01" => OUTPUT_1 <= INPUT_2;
			WHEN "10" => OUTPUT_1 <= INPUT_3;
			WHEN "11" => OUTPUT_1 <= INPUT_4;
			WHEN OTHERS => OUTPUT_1 <= INPUT_1;
		END CASE;
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity REG_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC; -- CLOCK
		INPUT_3  :in  STD_LOGIC; -- RESET
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of REG_SIGNED IS
begin
	process(INPUT_2)
	begin
		IF( INPUT_2'EVENT AND INPUT_2 = '1' ) THEN
			IF( INPUT_3 = '1' )THEN
				OUTPUT_1 <= STD_LOGIC_VECTOR(TO_UNSIGNED(0, BITWIDTH));
			ELSE
				OUTPUT_1 <= INPUT_1;
			END IF;
		END IF;
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity SQR_DIFF_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of SQR_DIFF_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp1 : SIGNED(BITWIDTH-1 DOWNTO 0);
		VARIABLE tmp2 : SIGNED((2*BITWIDTH)-1 DOWNTO 0);
	begin
		tmp1 := SIGNED( INPUT_1 ) - SIGNED( INPUT_2 );
		tmp2 := tmp1 * tmp1;
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp2(BITWIDTH-1 DOWNTO 0) );
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity SQR_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of SQR_SIGNED IS
begin
	process (INPUT_1)
		VARIABLE tmp : SIGNED((2*BITWIDTH)-1 DOWNTO 0);
	begin
		tmp := SIGNED( INPUT_1 ) * SIGNED( INPUT_1 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp(BITWIDTH-1 DOWNTO 0) );
	end process;
end;


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity SUB_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of SUB_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp : SIGNED(BITWIDTH-1 DOWNTO 0);
	begin
		tmp := SIGNED( INPUT_1 ) - SIGNED( INPUT_2 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp );
	end process;
end;



library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity ADD_SIGNED IS
	GENERIC (
		CONSTANT BITWIDTH : NATURAL := 16
	);
	port ( 
		INPUT_1  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		INPUT_2  :in  STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0);
		OUTPUT_1 :out STD_LOGIC_VECTOR(BITWIDTH-1 DOWNTO 0)
		);
	end;

architecture behavior of ADD_SIGNED IS
begin
	process (INPUT_1, INPUT_2)
		VARIABLE tmp : SIGNED(BITWIDTH-1 DOWNTO 0);
	begin
		tmp := SIGNED( INPUT_1 ) + SIGNED( INPUT_2 );
		OUTPUT_1 <= STD_LOGIC_VECTOR( tmp );
	end process;
end;
