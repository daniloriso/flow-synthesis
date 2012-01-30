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

entity inferieur is 
port( a: in std_logic_vector(31 downto 0);
      b: in std_logic_vector(31 downto 0);
      inf:out std_logic_vector(31 downto 0)
      );
  end inferieur;


architecture corps of inferieur is
    begin
        process(a,b)
            constant v:std_logic_vector(31 downto 0):="00111111100000000000000000000000";
            constant f:std_logic_vector(31 downto 0):="00000000000000000000000000000000";
            
            begin
              
                if (a(31)='0' and b(31)='1') then
                    inf<=f;
                elsif (a(31)='1' and b(31)='0') then
                    inf<=v;
                  
                else
                   if (a(31)='1' and b(31)='1') then
                       if a(30 downto 23)< b(30 downto 23) then
                           inf<=f;
                           --inf<='0';
                       
                       elsif a(30 downto 23)> b(30 downto 23) then
                           inf<=v;
                           --inf<='1';
                       else           -- if a(30 downto 23)=b(30 downto 23) then
                       
                          if a(22 downto 0)<b(22 downto 0) then
                              inf<=f;
                            
                           elsif a(22 downto 0)>b(22 downto 0) then
                               inf<=v;
                           else       --if a(22 downto 0)=b(22 downto 0) then
                           inf<=f;
                       end if;
                    end if;
                end if;
                    
                    if (a(31)='0' and b(31)='0') then
                        if a(30 downto 23)< b(30 downto 23) then
                            inf<=v;
                      
                        elsif a(30 downto 23)> b(30 downto 23) then
                            inf<=f;
                
                       else     --if a(30 downto 23)= b(30 downto 23) then
                       
                          if a(22 downto 0)>b(22 downto 0) then
                              inf<=f;
                           
                          elsif a(22 downto 0)< b(22 downto 0) then
                              inf<=v;
                          else     ---if a(22 downto 0)= b(22 downto 0) then
                              inf<=f;
                          end if;
                    end if;
                end if;
                
            end if;
            
                    
            end process;
        end corps;
            
            
                    
                        
            

