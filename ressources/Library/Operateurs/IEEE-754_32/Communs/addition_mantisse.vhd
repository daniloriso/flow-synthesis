library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
--use work.txt_util.all;

library work; 
use work.all;


entity Addition_mantisse is
port( c: in std_logic;
      d: in std_logic;
      grand_m: in Std_Logic_Vector (26 downto 0 );
      petit_m: in Std_Logic_Vector (26 downto 0 );
      somme_mantisse: out std_logic_vector(27 downto 0);
      Z : out Std_Logic_Vector (5 downto 0 ) 
      );
end Addition_mantisse;
    
     
architecture corps of Addition_mantisse is
     component ZLC is
port ( E : in  Std_Logic_Vector (31 downto 0 ) ; 
       Z : out Std_Logic_Vector (5 downto 0 ) ) ; 
end component;


signal E:Std_Logic_Vector (31 downto 0 );


begin  
    nb_Z: ZLC port map(E,Z);
     
     process (petit_m,grand_m,c,d)
         variable somme: Std_Logic_Vector (27 downto 0 );
         variable tmp1,tmp2: Std_Logic_Vector (27 downto 0 );        

        begin
            
         tmp1:='0' & grand_m;
         tmp2:='0' & petit_m;
         if c=d then
                somme:= tmp1 + tmp2;
                somme_mantisse<=somme;
            else
                somme:= tmp1-tmp2;
                somme_mantisse<=somme;
             end if;
             E<= somme & "0000";  
               
      end process;
     
      
  end corps;
