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


entity DIV_IEEE_754 is
PORT (
	INPUT_1	: IN  Std_Logic_Vector(31 DOWNTO 0)  ;
	INPUT_2	: IN  Std_Logic_Vector(31 DOWNTO 0)  ;
	OUTPUT_1	: OUT Std_Logic_Vector(31 DOWNTO 0)
);
end DIV_IEEE_754;
     
    
architecture corps OF DIV_IEEE_754 is
BEGIN
        process(INPUT_1, INPUT_2)
            variable quotient:Std_Logic_Vector(24 DOWNTO 0); 
            variable dividende:Std_Logic_Vector(47 DOWNTO 0);
            variable diviseur:Std_Logic_Vector(47 DOWNTO 0) ;
            variable signe: std_ulogic;
            variable expo: std_logic_vector (7 downto 0);
            variable i:integer;
            variable temp:std_logic_vector (47 downto 0);  
            variable quot:Std_Logic_Vector(23 DOWNTO 0);       
            variable tmp:bit_vector (23 downto 0);
               
           BEGIN
                dividende:="000000000000000000000000000000000000000000000000";
                diviseur:="000000000000000000000000000000000000000000000000";

                dividende := '1' & INPUT_1(22 downto 0)& dividende(23 downto 0);
                diviseur  := '1' & INPUT_2(22 downto 0)& diviseur(23 downto 0);
                
                --détermination du diviseur:
                

                for i in 0 to 24 loop
                    if dividende >= diviseur then
                        quotient(24-i):='1';       
                        temp  := dividende - diviseur;
                        dividende:=temp;
                        diviseur:= '0' & diviseur(47 downto 1);
                        
                    else
                        quotient(24-i) :='0';      
                        diviseur:= '0' & diviseur(47 downto 1);
                    end if; 
                    end loop;
                    
                    
        ----Signe de l'opération
        
        signe:=INPUT_1(31) xor INPUT_2(31);
        
         ---détermination de l'exposant
        expo:=INPUT_1(30 downto 23)-INPUT_2(30 downto 23)+conv_std_logic_vector(127,8);
        if (quotient(24)='1') then 
             OUTPUT_1(22 downto 0)<=quotient(23 downto 1);
             expo:=expo;
             else
            OUTPUT_1(22 downto 0)<= quotient(22 downto 0);
            expo:=expo-1;
        end if;
     
       OUTPUT_1(31)<=signe;
       OUTPUT_1(30 downto 23)<=expo;
       
     end process;      
   end corps;
   
       
       
       
       
 
                
        
        
    
        
                        
                    
                
                
                
