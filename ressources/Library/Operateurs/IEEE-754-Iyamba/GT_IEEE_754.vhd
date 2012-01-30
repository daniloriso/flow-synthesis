--------------------------------------------------------------------------------------
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

entity superieur is 
port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      sup:out std_logic_vector(31 downto 0)
      );
  end superieur;


architecture corps of superieur is
    begin
        process(a,b)
             variable v:std_logic_vector(31 downto 0):="00111111100000000000000000000000";
              variable f:std_logic_vector(31 downto 0):="00000000000000000000000000000000";
            
            begin
               
                if (a(31)='0' and b(31)='1') then  --cas ou les deux sont de signes opposés
                    sup<=v;
                elsif (a(31)='1' and b(31)='0') then --idem
                    sup<=f;
                else
                
                if (a(31)='1' and b(31)='1') then   -- cas ou les deux sont négatif
                   if a(30 downto 23)=b(30 downto 23) then
                       if a(22 downto 0)<b(22 downto 0) then
                           sup<=v;
                        elsif a(22 downto 0)>b(22 downto 0) then
                           sup<=f;
                        else       -- if a(22 downto 0)= b(22 downto 0) then
                       
                              sup<=f;
                          end if;
                    elsif a(30 downto 23)< b(30 downto 23) then
                        sup<=v;
                    else    --if a(30 downto 23)> b(30 downto 23) then
                    
                        sup<=f;
                    end if;
                    
                end if;
                    
                    if (a(31)='0' and b(31)='0') then      --cas ou les deux sont positifs
                       if a(30 downto 23)= b(30 downto 23) then
                          if a(22 downto 0)>b(22 downto 0) then
                              sup<=v;
                          
                          elsif a(22 downto 0)< b(22 downto 0) then
                              sup<=f;
                          else             --if a(22 downto 0)= b(22 downto 0) then
                          
                              sup<=f;
                          end if;
                     
                    elsif a(30 downto 23)> b(30 downto 23) then
                        sup<=v;
                       --end if;
                    else                --if a(30 downto 23)< b(30 downto 23) then
                  
                        sup<=f;
                          end if;
                       end if; 
                   end if;  
                    
            end process;
        end corps;
            
            
                    
                        
            