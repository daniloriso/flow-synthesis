--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--Fonction carré
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use IEEE.Numeric_Std.all;
library work; 
use work.all;


ENTITY MAC_IEEE_754 IS
	PORT (
		INPUT_1         : IN  Std_Logic_Vector(31 DOWNTO 0)  ;
		INPUT_2		: IN  Std_Logic_Vector(31 DOWNTO 0)  ;
		INPUT_3         : IN  Std_Logic_Vector(31 DOWNTO 0)  ;
		OUTPUT_1        : OUT Std_Logic_Vector(31 DOWNTO 0) );
END MAC_IEEE_754 ;

Architecture corps of MAC_IEEE_754 is
	
	COMPONENT MUL_IEEE_754 IS
	PORT (
				INPUT_1         : IN  Std_Logic_Vector(31 DOWNTO 0)  ;
				INPUT_2         : IN  Std_Logic_Vector(31 DOWNTO 0)  ;
				OUTPUT_1        : OUT Std_Logic_Vector(31 DOWNTO 0) );
	END COMPONENT ;

	COMPONENT ADD_IEEE_754 IS
	PORT (
				INPUT_1         : IN  Std_Logic_Vector(31 DOWNTO 0)  ;
				INPUT_2         : IN  Std_Logic_Vector(31 DOWNTO 0)  ;
				OUTPUT_1        : OUT Std_Logic_Vector(31 DOWNTO 0) );
	END COMPONENT ;

	SIGNAL TEMP : Std_Logic_Vector(31 DOWNTO 0);

begin
    
	Mult: MUL_IEEE_754 port map(INPUT_1,INPUT_2,TEMP);
	Addi: ADD_IEEE_754 port map(TEMP,INPUT_3,OUTPUT_1);

end corps;    


