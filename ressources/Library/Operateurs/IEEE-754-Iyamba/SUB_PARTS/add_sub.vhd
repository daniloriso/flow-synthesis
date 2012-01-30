library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use IEEE.Numeric_Std.all;
library work; 
use work.all;


entity Add_sub is
port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      add,sub: out std_logic_vector(31 downto 0)
      );
end Add_sub;



architecture corps of add_sub is
    
    component Adder_normal is
        port( a: in std_logic_vector(31 downto 0);
              b: in std_logic_vector(31 downto 0);
              r: out std_logic_vector(31 downto 0)
               );
    end component;
    
    
    component Subber is
        port( a: in std_logic_vector(31 downto 0);
              b: in std_logic_vector(31 downto 0);
              s: out std_logic_vector(31 downto 0)
            );
    end component;
    
    begin
        addition: adder_normal port map(a,b,add);
        soutraction:subber port map(a,b,sub);
    end corps;
    
    
    