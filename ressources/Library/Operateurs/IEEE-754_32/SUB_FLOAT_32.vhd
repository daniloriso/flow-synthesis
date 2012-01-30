--IYAMBA ASSA Stage VHDL Juin 2007
--Unité d'addition:bloc complet
--
--------------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

library work; 
use work.all;


entity SUB_FLOAT32 is
port( INPUT_1: in std_logic_vector(31 downto 0);
      INPUT_2: in std_logic_vector(31 downto 0);
      OUTPUT_1: out std_logic_vector(31 downto 0)
      );
end SUB_FLOAT32;


architecture corps of SUB_FLOAT32 is
 COMPONENT selection is 
   port( 
         INPUT_1: in std_logic_vector(31 downto 0);
         INPUT_2: in std_logic_vector(31 downto 0);
         m_grand: out std_logic_vector(22 downto 0);
         m_petit: out std_logic_vector(23 downto 0);
         --expo_egal: out std_ulogic;
         expo_grand: out std_logic_vector(7 downto 0);
          diff_expo_bin: out std_logic_vector (4 downto 0);
         sr: out std_logic);
  end component;
  
  COMPONENT aligneur_mantisse is
    port( 
           m_grand: in std_logic_vector(22 downto 0);
      m_petit: in std_logic_vector(23 downto 0);
      --expo_egal: in std_ulogic;
      diff_expo_bin: in std_logic_vector (4 downto 0);
      grand_m: out Std_Logic_Vector (26 downto 0 );
      petit_m: out Std_Logic_Vector (26 downto 0 )
          );
   end component;


  COMPONENT renormalisation is
   port( 
         somme_mantisse: in std_logic_vector(27 downto 0);           
        expo_grand: in std_logic_vector(7 downto 0);
        Z : in Std_Logic_Vector (5 downto 0 );
         m_finale: out std_logic_vector(22 downto 0);
         --j: out integer;
         expo_final: out std_logic_vector (7 downto 0)
       );
   end component;
   
   component Addition_mantisse is
port( c: in std_logic;
      d: in std_logic;
      grand_m: in Std_Logic_Vector (26 downto 0 );
      petit_m: in Std_Logic_Vector (26 downto 0 );
      somme_mantisse: out std_logic_vector(27 downto 0);
      Z: out Std_Logic_Vector (5 downto 0 )
      );
      
  end component;
  
 
  signal m_grand: std_logic_vector (22 downto 0);
  signal m_petit: std_logic_vector (23 downto 0);
  signal petit_m,grand_m: std_logic_vector (26 downto 0);
  signal signe: std_ulogic;
  signal somme: std_logic_vector(27 downto 0);
  signal somme_finale: std_logic_vector(22 downto 0);
  signal expo: std_logic_vector (7 downto 0);
  signal expo_diff: std_logic_vector (4 downto 0);
  signal grand_expo:std_logic_vector(7 downto 0);
  signal Z: Std_Logic_Vector (5 downto 0 );
 
  
  signal NOT_INPUT_2: Std_Logic_Vector (31 downto 0 );
  
   
 begin
     
	  NOT_INPUT_2 <= not(INPUT_2(31)) & INPUT_2(30 downto 0);
	  
     sel: selection port map(INPUT_1,NOT_INPUT_2,m_grand,m_petit,grand_expo,expo_diff, signe);
     aligneur: aligneur_mantisse port map(m_grand, m_petit,expo_diff,grand_m,petit_m );
     addition: Addition_mantisse port map(INPUT_1(31),NOT_INPUT_2(31),grand_m,petit_m,somme,Z);
     norme: renormalisation port map(somme,grand_expo,Z,somme_finale,expo);
	  OUTPUT_1<= signe & expo & somme_finale;

end corps;     
     
