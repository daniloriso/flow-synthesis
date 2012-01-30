
--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
--use ieee.std_logic_arith.all;
--use ieee.std_logic_unsigned.all;
use IEEE.Numeric_Std.all;
library work; 
use work.all;

entity NOT_EQUAL_IEEE_754 is 
port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      egal: out std_logic_vector(31 downto 0)
      --egal:out std_ulogic
      );
  end NOT_EQUAL_IEEE_754;
  
  architecture corps of NOT_EQUAL_IEEE_754 is
      begin
          process(a,b)
              constant vrai:std_logic_vector(31 downto 0):="00111111100000000000000000000000";
              constant faux:std_logic_vector(31 downto 0):="00000000000000000000000000000000";
            

              begin
                 if (a = b) then
                     egal <= faux;
                   else 
                   egal<=vrai;
               end if;
       end process;
end corps;