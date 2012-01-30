--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
library work; 
use work.all;

entity ABS_IEEE_754 is 
port( a: in std_logic_vector(31 downto 0);
      s:out std_logic_vector(31 downto 0)
      );
end ABS_IEEE_754;
  
  
architecture component of ABS_IEEE_754 is
BEGIN

	process(a)
		variable tmp:std_logic_vector(31 downto 0);
	begin
		tmp := '0' & a(30 downto 0);
		s   <= tmp;
	end process;
END component;
                  

