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

entity aligneur_mantisse is
port( 
      m_grand: in std_logic_vector(22 downto 0);
      m_petit: in std_logic_vector(23 downto 0);
      diff_expo_bin: in std_logic_vector (4 downto 0);
      grand_m: out Std_Logic_Vector (26 downto 0 );
      petit_m: out Std_Logic_Vector (26 downto 0 )
      );
  end aligneur_mantisse;
  
  
  architecture corps of aligneur_mantisse is
      
    component Shift is
      port ( Entree : in  Std_Logic_Vector (23 downto 0 ) ;   -- E : input
       D : in  Std_Logic_Vector (4 downto 0 ) ;    -- D : positions
       S : out Std_Logic_Vector (26 downto 0 ) ) ;
   end component;
         
         signal S: Std_Logic_Vector (26 downto 0 );    
         signal pm,gm:std_logic_vector(26 downto 0);
              
         begin 
             decaleur: Shift port map (m_petit,diff_expo_bin ,S);
       
              gm<='1' & m_grand & "000";
              pm<= S ;
              grand_m<=gm;
              petit_m<=pm;
         
          end corps;
          
                  
                  
                          
                  
                  
  
  


