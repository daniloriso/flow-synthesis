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

entity aligneur_mantisse is
port( 
      m_grand: in std_logic_vector(22 downto 0);
      m_petit: in std_logic_vector(23 downto 0);
     -- expo_egal: in std_ulogic;
      diff_expo_bin: in std_logic_vector (4 downto 0);
      grand_m: out Std_Logic_Vector (26 downto 0 );
      petit_m: out Std_Logic_Vector (26 downto 0 )
      --somme_mantisse: out std_logic_vector(27 downto 0)
      );
  end aligneur_mantisse;
  
  
  architecture corps of aligneur_mantisse is
      
    component Shift is
      port ( Entree : in  Std_Logic_Vector (23 downto 0 ) ;   -- E : input
       D : in  Std_Logic_Vector (4 downto 0 ) ;    -- D : positions
       S : out Std_Logic_Vector (26 downto 0 ) ) ;
   end component;
         
         signal S: Std_Logic_Vector (26 downto 0 );    
         --signal entree: Std_Logic_Vector (23 downto 0 );
         --signal D: Std_Logic_Vector (4 downto 0 );  
         signal pm,gm:std_logic_vector(26 downto 0);
              
         begin 
            -- entree<='1' & m_petit;
             --D<=conv_std_logic_vector(diff_expo,5);
             decaleur: Shift port map (m_petit,diff_expo_bin ,S);
       
              gm<='1' & m_grand & "000";
              pm<= S ;
              grand_m<=gm;
              petit_m<=pm;
         
          end corps;
          
                  
                  
                          
                  
                  
  
  


