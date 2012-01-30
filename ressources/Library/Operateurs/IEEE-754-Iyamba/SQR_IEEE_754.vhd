--------------------------------------------------------------------------------------
--IYAMBA ASSA Stage VHDL Juin 2007
--Fonction carré
--
--------------------------------------------------------------------------------------

LIBRARY IEEE ;
USE IEEE.STD_LOGIC_1164.ALL ;
USE IEEE.STD_LOGIC_SIGNED.ALL ;
USE IEEE.STD_LOGIC_ARITH.ALL ;

ENTITY SQR_IEEE_754 IS
PORT (
		INPUT_1         : IN  Std_Logic_Vector(31 DOWNTO 0);
		OUTPUT_1        : OUT Std_Logic_Vector(31 DOWNTO 0)  
);
END SQR_IEEE_754 ;


ARCHITECTURE comportementale OF SQR_IEEE_754 IS 
BEGIN

	Process_Calcul : PROCESS (INPUT_1)
		VARIABLE sa, sc : Std_Logic;
		VARIABLE ea, ec : Std_Logic_Vector(7  DOWNTO 0);
		VARIABLE ma, mc : Std_Logic_Vector(22 DOWNTO 0);
		
		-- VARIABLES NECESSAIRES POUR LE CALCUL DE LA MANTISSE
		VARIABLE na         : Std_Logic_Vector(23 DOWNTO 0) := "000000000000000000000000";
		VARIABLE nb         : Std_Logic_Vector(23 DOWNTO 0) := "000000000000000000000000";
		VARIABLE nr         : Std_Logic_Vector(23 DOWNTO 0) := "000000000000000000000000";
		VARIABLE nc         : Std_Logic_Vector(47 DOWNTO 0);
		VARIABLE nd         : Std_Logic_Vector(45 DOWNTO 0);
		VARIABLE ne         : Std_Logic_Vector(22 DOWNTO 0);
		VARIABLE t          : INTEGER;

	BEGIN
		
				-- DECOMPOSITION DU NOMBRE A
				sa := INPUT_1( 31 );
				ea := INPUT_1( 30 DOWNTO 23 );
				ma := INPUT_1( 22 DOWNTO 0  );
				
				-- DECOMPOSITION DU NOMBRE B
				--sb := INPUT_1( 31 );
				--eb := INPUT_1( 30 DOWNTO 23 );
				--mb := INPUT_1( 22 DOWNTO 0  );

				na(22 DOWNTO 0) := ma; na(23) := '1';
				--nb(22 DOWNTO 0) := mb; nb(23) := '1';
		        
				nc := unsigned(na) * unsigned(na);

		        -- nd = (nc.test(47)==1)?nc.range(46, 1):nc.range(45, 0);
				if( nc(47) = '1' ) then
					nd := nc(46 DOWNTO 1);
				else
					nd := nc(45 DOWNTO 0);
				end if;

				-- ON RECUPERE NOTRE NOUVELLE MANTISSE
		        ne := nd(45 DOWNTO 23);

				-- GESTION DE L'ARRONDI DE LA MANTISSE
				
				   		  if( nd(22) = '1' ) then
		                if( nd(21) = '1' OR nd(20) = '1' OR nd(19) = '1' OR nd(18) = '1' OR nd(17) = '1' OR nd(16) = '1' OR nd(15) = '1' OR nd(14) = '1'
							 OR nd(13) = '1' OR nd(12) = '1' OR nd(11) = '1' OR nd(10) = '1' OR nd(9) = '1' OR nd(8) = '1' OR nd(7) = '1' OR
							 nd(6) = '1' OR nd(5) = '1' OR nd(4) = '1' OR nd(3) = '1' OR nd(2) = '1' OR nd(1) = '1' OR nd(0) = '1' ) then
						
							mc := unsigned(ne) + 1;	-- TODO : verifier que l'on ne genere pas une retenue !

							
						else
							mc := ne;
						end if;
				else
						mc := ne;
				end if;

				-- GESTION DU CALCUL DE L'EXPOSANT
				if( nc(47) = '1' ) then
					t  := CONV_INTEGER(ea) + CONV_INTEGER(ea) + 1;
				else
					t  := CONV_INTEGER(ea) + CONV_INTEGER(ea);
				end if;
				
				t := (t - 127);
		      	
				-- TODO: Il faut gerer les exceptions au niveau de l'exposant
				ec := CONV_STD_LOGIC_VECTOR(t, 8);

				-- GESTION DU SIGNE DU PRODUIT          
				sc := sa XOR sa;
			
				-- RECOMPOSITION DU NOMBRE C
				OUTPUT_1( 31 )           <= sc;
				OUTPUT_1( 30 DOWNTO 23 ) <= ec;
				OUTPUT_1( 22 DOWNTO 0  ) <= mc;
				 
	END PROCESS Process_Calcul ;

END comportementale ;