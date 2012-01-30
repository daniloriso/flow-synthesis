
--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--Unité d'addition:bloc d'entrée
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use IEEE.Numeric_Std.all;
library work; 
use work.all;

entity selection is 
port( INPUT_1: in std_logic_vector(31 downto 0);
      INPUT_2: in std_logic_vector(31 downto 0);
      m_grand: out std_logic_vector(22 downto 0);
      m_petit: out std_logic_vector(23 downto 0);
      --expo_egal: out std_ulogic;
      expo_grand: out std_logic_vector(7 downto 0);
      --expo_petit: out std_logic_vector(7 downto 0);
     -- diff_expo: out integer;
      diff_expo_bin: out std_logic_vector (4 downto 0);
      sr: out std_logic);
  end selection;

architecture corps of selection is
begin
    
    process(INPUT_1,INPUT_2)
       variable sa: std_ulogic;
       variable sb: std_ulogic;
       variable ea: std_logic_vector(7 downto 0);
       variable eb:std_logic_vector(7 downto 0);
       variable ma: std_logic_vector(22 downto 0);
       variable mb: std_logic_vector(22 downto 0);   
       variable diff_expo_inter: integer;
  
        
        begin
		
            sa:=INPUT_1(31);
            sb:=INPUT_2(31);
            ea:=INPUT_1(30 downto 23);
            eb:=INPUT_2(30 downto 23);
            ma:=INPUT_1(22 downto 0);
            mb:=INPUT_2(22 downto 0);
            
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
          
           else     --elsif conv_integer(ea)<conv_integer(eb) then
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
        