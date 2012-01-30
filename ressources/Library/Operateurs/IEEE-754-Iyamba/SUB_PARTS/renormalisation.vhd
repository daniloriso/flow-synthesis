library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
library work; 
use work.all;


entity renormalisation is
port( somme_mantisse: in std_logic_vector(27 downto 0);            
      expo_grand: in std_logic_vector(7 downto 0);
      Z : in Std_Logic_Vector (5 downto 0 );
      m_finale: out std_logic_vector(22 downto 0);
      expo_final: out std_logic_vector (7 downto 0)
    );
end renormalisation;

Architecture corps of renormalisation is

  begin
 
    
  
        process(somme_mantisse,expo_grand,Z)
            
           variable expo_final_tmp: std_logic_vector (7 downto 0);
           variable somme: std_logic_vector (27 downto 0);
           variable sommebis:bit_vector (27 downto 0);
           variable s:bit_vector (27 downto 0);
           variable i:integer;
           variable a: Std_Logic_Vector (5 downto 0 );
           variable tmp:std_logic_vector (22 downto 0);
            
            
            
            begin 
               
          
          somme:=somme_mantisse;
          sommebis:=To_bitvector(somme);
          s:= sommebis sll conv_integer(Z);
			
			 --test sur l'arrondi
			   
			   if ((s(3)='1' and s(4)='1') or (s(3)='1' and (s(2)='1' or s(1)='1' or s(0)='1')) ) then
			       tmp:=To_stdlogicvector(s(26 downto 4))+"00000000000000000000001";
			       m_finale<=tmp;  
			       
			    else
			       m_finale<=To_stdlogicvector(s(26 downto 4));
			    end if;
			
			   
			   if conv_integer(Z)=32 then
			      expo_final_tmp:=expo_grand;
			   else
			      expo_final_tmp:=expo_grand-conv_std_logic_vector(conv_integer(Z),8)+1;
			   
			   end if;
			   
			   expo_final<=expo_final_tmp;
			   
			  end process;
			  
		end corps;
			  
			            

