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

entity infegal is
    port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      true:out std_logic_vector (31 downto 0)
      );
  end infegal;
  
  
  architecture corps of infegal is
      component inferieur is 
      port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      inf:out std_logic_vector(31 downto 0)
      );
  end component;
  
  component egalite is 
   port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      egal:out std_logic_vector(31 downto 0)
      );
  end component;
  signal egal,inf:std_logic_vector(31 downto 0);
  
  begin
      equality: egalite port map(a,b,egal);
      inferiority: inferieur port map (a,b, inf);
      
      true<=egal or inf;
  end corps;
  
  
