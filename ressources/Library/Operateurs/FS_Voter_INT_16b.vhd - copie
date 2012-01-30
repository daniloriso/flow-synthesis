LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
USE IEEE.NUMERIC_STD.ALL;

library work;
use work.all;

ENTITY VOTEUR_16b IS
PORT(
     INPUT_1  : IN  Std_Logic_Vector(49 DOWNTO 0 );
     OUTPUT_1 : OUT Std_Logic_Vector(49 DOWNTO 0 )
    );
END VOTEUR_16b;


ARCHITECTURE VOTEUR_16b_arch OF VOTEUR_16b IS
BEGIN

 PROC_VOTEUR_16b : PROCESS (INPUT_1)
       VARIABLE DATA_1  : Std_Logic_Vector(15 DOWNTO 0 );
       VARIABLE DATA_2  : Std_Logic_Vector(15 DOWNTO 0 );
       VARIABLE DATA_3  : Std_Logic_Vector(15 DOWNTO 0 );
       VARIABLE DATA_S  : Std_Logic_Vector(15 DOWNTO 0 );
       VARIABLE WARNING : Std_Logic;
       VARIABLE ERROR   : Std_Logic;
       VARIABLE EQ_D1_D2   : Std_Logic;
       VARIABLE EQ_D1_D3   : Std_Logic;
       VARIABLE EQ_D2_D3   : Std_Logic;
 BEGIN
      DATA_1  := INPUT_1(15 DOWNTO 0);
      DATA_2  := INPUT_1(31 DOWNTO 16);
      DATA_3  := INPUT_1(47 DOWNTO 32);
                 -- VERIFICATION DE L'EGALITE RESULTAT 1 <=> RESULTAT 2
      IF(DATA_1 = DATA_2) THEN
              EQ_D1_D2 := '0';
          ELSE
              EQ_D1_D2 := '1';
          END IF;

        -- VERIFICATION DE L'EGALITE RESULTAT 1 <=> RESULTAT 3
      IF(DATA_1 = DATA_3) THEN
              EQ_D1_D3 := '0';
          ELSE
              EQ_D1_D3 := '1';
          END IF;

        -- VERIFICATION DE L'EGALITE RESULTAT 2 <=> RESULTAT 3
      IF(DATA_2 = DATA_3) THEN
              EQ_D2_D3 := '0';
          ELSE
              EQ_D2_D3 := '1';
          END IF;

        -- SELECTION DE LA VALEUR DE SORTIE
      IF(EQ_D1_D2 = '0') THEN
          DATA_S := DATA_1;
      ELSIF(EQ_D1_D3 = '0') THEN
          DATA_S := DATA_1;
      ELSE
          DATA_S := DATA_2;
      END IF;

        -- CALCUL DE LA VALEUR DES SIGNAUX ERROR ET WARNING
      ERROR   := INPUT_1(49) OR (EQ_D1_D2 AND EQ_D1_D3 AND EQ_D2_D3);
      WARNING := INPUT_1(48) OR  EQ_D1_D2 OR  EQ_D1_D3 OR  EQ_D2_D3;

          -- MISE A JOUR DE LA SORTIE DU VOTEUR
      OUTPUT_1 <= ERROR & WARNING & DATA_S & DATA_S & DATA_S;
        END PROCESS PROC_VOTEUR_16b;

END VOTEUR_16b_arch;
