library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use IEEE.Numeric_Std.all;
library work; 
use work.all;

entity selection is 
port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      m_grand: out std_logic_vector(22 downto 0);
      m_petit: out std_logic_vector(23 downto 0);
      expo_grand: out std_logic_vector(7 downto 0);
      diff_expo_bin: out std_logic_vector (4 downto 0);
      sr: out std_logic);
  end selection;

architecture corps of selection is
begin
    
    process(a,b)
       variable sa: std_ulogic;
       variable sb: std_ulogic;
       variable ea: std_logic_vector(7 downto 0);
       variable eb:std_logic_vector(7 downto 0);
       variable ma: std_logic_vector(22 downto 0);
       variable mb: std_logic_vector(22 downto 0);   
       variable diff_expo_inter: integer;
  
        
        begin
		
            sa:=a(31);
            sb:=b(31);
            ea:=a(30 downto 23);
            eb:=b(30 downto 23);
            ma:=a(22 downto 0);
            mb:=b(22 downto 0);
            
          if (conv_integer(ea) = conv_integer(eb)) then
              expo_grand<= ea;
              diff_expo_inter:= 0;
              if conv_integer(ma)>conv_integer(mb) then
                  m_grand<= ma;
                  m_petit<= '1' & mb;
                  sr<= sa ;
              else 
                 m_grand<=mb;
                 m_petit<='1' & ma;
                 sr<=sb;
                 
              end if;

          elsif conv_integer(ea)>conv_integer(eb) then
              expo_grand<= ea;
              m_grand<= ma;
              m_petit<= '1' & mb;
              diff_expo_inter:= conv_integer(ea)-conv_integer(eb);
              sr<= sa;
          
           else     --if conv_integer(ea)<conv_integer(eb) then
              expo_grand<= eb;
              m_grand<= mb;
              m_petit<= '1' & ma;
              diff_expo_inter:= conv_integer(eb)-conv_integer(ea);
              sr<= sb;

          end if;
          
         if conv_integer(diff_expo_inter)>=27 then
         diff_expo_inter:=27;
        end if;
        diff_expo_bin<=conv_std_logic_vector(diff_expo_inter,5);

      end process;
   end corps;
        