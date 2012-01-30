
--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use IEEE.Numeric_Std.all;
library work; 
use work.all;

entity supegal is
    port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      true:out std_logic_vector (31 downto 0)
      );
  end supegal;
  
  
  architecture corps of supegal is
      component superieur is 
      port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      sup:out std_logic_vector(31 downto 0)
      );
  end component;
  
  component egalite is 
   port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      egal:out std_logic_vector(31 downto 0)
      );
  end component;
  signal egal,sup:std_logic_vector(31 downto 0);
  
  begin
      equality: egalite port map(a,b,egal);
      superiority: superieur port map (a,b, sup);
      
      true<=egal or sup;
  end corps;
  
  

