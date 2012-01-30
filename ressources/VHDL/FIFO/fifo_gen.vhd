-------------------------------------------------------------------------------
-- Titre       : fifo generique
-- Projet      : 
-------------------------------------------------------------------------------
-- Fichier     : fifo_gen.vhd
-- Auteur      : NOUEL Patrice  <nouel@szut>
-- Compagnie   : ENSEIRB
-- Mise a jour : 2000/10/06
-- Platform    :   
-------------------------------------------------------------------------------
-- Description : Synthetisable
-- 
-------------------------------------------------------------------------------
-- Modification history :
-- 2000/10/06 : creation
-------------------------------------------------------------------------------
--  FIFO  inspiree du circuit IDT72201
--  L'implantation a ete testee sur un circuit XILINX 4006E -4
-- On a obtenu pour ce circuit une fréquence max de 33Mhz
-- Occupation: 78 CLB soit 13%

LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.numeric_std.ALL;

ENTITY fifo_gen IS
  GENERIC(
    port_width : INTEGER := 8 ; -- nombre de bits du mot
    address_width  : integer := 2);  -- nombre de bits adresses  memoire interne
  --   depth =  capacite en mots =  2**address_width 
  PORT(
    d : IN  unsigned(port_width-1 DOWNTO 0);-- entree
    reset_b : IN  std_logic; -- actif au niveau bas
    wclk : IN  std_logic; -- horloge d'ecriture
    we : IN  std_logic; -- autorise l'ecriture sur front montant de wclk
    load : IN  std_logic; -- prioritaire pour l'entree des deux seuils
    rclk : IN  std_logic; -- horloge de lecture
    re  : IN  std_logic; -- autorise la lecture sur front montant de rclk 
    oe_b : IN  std_logic; -- Si '0' rend le bus actif, sinon bus = "zzzz..."
    q : OUT unsigned(port_width-1 DOWNTO 0);-- sortie trois  etats
    empty : OUT std_logic;  -- fifo vide
    pae : OUT std_logic; -- fifo presque vide ( selon le niveau programmable)
    paf : OUT std_logic; -- fifo presque pleine ( selon le niveau programmable)
    full : OUT std_logic); -- fifo pleine
  
END fifo_gen;

-- La legere modification apportee concerne le we2 remplacee par load:
-- Entree load : Elle sert a synchroniser les 2 donneees "seuils" en entree
-- sur deux fronts d'horloge ecriture. Une troisieme donnee ne sera pas prise
-- en compte. En l'absence de Load, il existe un écart par defaut de 7 octets


ARCHITECTURE a_plat OF fifo_gen IS 
  
  CONSTANT depth  : natural := 2**address_width;  -- capacite fifo
  TYPE mem_type   IS ARRAY ( depth -1  DOWNTO 0) OF unsigned (port_width-1  DOWNTO 0);
  SIGNAL mem : mem_type;
  SIGNAL address_int : unsigned(address_width-1 DOWNTO 0);
  SIGNAL din       :  unsigned( port_width-1 DOWNTO 0);  -- entree
  SIGNAL address   :  unsigned(address_width-1 DOWNTO 0);     --adresse ecriture
  SIGNAL dpra      :  unsigned(address_width-1 DOWNTO 0);      -- adresse lecture
  SIGNAL dpo       :  unsigned(port_width-1 DOWNTO 0) ;    -- sortie 8 bits
  SIGNAL writepointer_en, readpointer_en : std_logic;
  SIGNAL ecart : unsigned( address_width DOWNTO 0);  -- 0=vide, depth=plein
  SIGNAL plein, vide  : std_ulogic;
  CONSTANT pardefaut : natural  := 7 ;
  SIGNAL seuil_bas, seuil_haut : unsigned( port_width - 1 DOWNTO 0);
  
BEGIN  -- a_plat

  din <= d ;
  
  ram_double : PROCESS (wclk, rclk)
  BEGIN  -- PROCESS
    IF (wclk = '1' AND wclk'event) THEN
      --     address_int <= address;
      IF writepointer_en = '1' THEN
        mem(To_integer('0' & address)) <= din;
      END IF;
    END IF;

    IF (rclk = '1' AND rclk'event) THEN 
      IF readpointer_en = '1' THEN 
        dpo <= mem(to_integer('0' & dpra));
        --  spo <= mem(To_integer(address_int));
      END if ;
    END IF;
  END PROCESS;
  

  write_pointer: PROCESS (reset_b, wclk)
    VARIABLE c : natural RANGE 0 TO 2**address_width-1;
  BEGIN  -- PROCESS
    IF reset_b = '0'  THEN
      c := 0;
    ELSIF rising_edge(wclk) THEN
      IF writepointer_en = '1' THEN 
        IF c < depth -1 THEN 
          c := c +1;
        ELSE
          c := 0;
        END IF;
      END IF;
    END IF; 
    address <= to_unsigned( c , address_width);
  END PROCESS;

  writepointer_en <= we AND ( NOT plein) AND (NOT load);
  
  read_pointer: PROCESS (reset_b, rclk)
    VARIABLE c : natural RANGE 0 TO 2**address_width-1;
  BEGIN  -- PROCESS
    IF reset_b = '0'  THEN
      c := 0;
    ELSIF rising_edge(rclk) THEN 
      IF readpointer_en = '1' THEN
        IF c < depth -1 THEN 
          c := c +1;
        ELSE
          c := 0;
        END IF;
      END IF;
    END IF;
    dpra <= to_unsigned( c , address_width);
  END PROCESS;

  readpointer_en <= re AND ( NOT vide) AND (NOT load);

  ecart <= '0' & (address - dpra)  ;

  -- purpose : full est vrai si ecart = max -1 et ecriture
  -- type    : sequential
  -- inputs  : we, reset_b, ecart
  -- outputs : full
  flag1 : PROCESS (wclk, reset_b)
  BEGIN  -- PROCESS flags
    IF reset_b = '0' THEN                -- asynchronous reset (active low)
      plein <= '0';
    ELSIF wclk'event AND wclk = '1' THEN     -- rising clock edge
      IF ecart =  to_unsigned(depth -1, ecart'length) AND writepointer_en = '1' THEN
        plein <= '1';
      ELSIF readpointer_en = '1' THEN
        plein  <= '0';
      END IF; 
    END IF;
  END PROCESS flag1;

  full <= plein;
  
  -- purpose : empty  est vrai si ecart = 1 et lecture
  -- type    : sequential
  -- inputs  : re, reset_b, ecart
  -- outputs : empty
  flag2 : PROCESS (rclk, reset_b)
  BEGIN  -- PROCESS flags
    IF reset_b = '0' THEN                -- asynchronous reset (active low)
      vide  <= '1';
    ELSIF rclk'event AND rclk = '1' THEN     -- rising clock edge
      IF ecart =  to_unsigned(1 , ecart'length) AND readpointer_en = '1' THEN
        vide <= '1';
      ELSIF writepointer_en = '1' THEN
        vide  <= '0';
      END IF; 
    END IF;
  END PROCESS flag2;

  empty <= vide ;

  q <= dpo WHEN oe_b = '0'  ELSE (OTHERS => 'Z') ;

  -- purpose : entrees des valeurs seuils
  -- type    : combinational
  -- inputs  : wclk, reset_b
  -- outputs : seuil_bas, seuil_haut
  seuils : PROCESS (wclk, reset_b)
    VARIABLE c : natural RANGE 2 DOWNTO 0;
  BEGIN  -- PROCESS seuils
    IF reset_b = '0' THEN
      c :=0;
      seuil_haut <= to_unsigned(depth -pardefaut, seuil_haut'length);
      seuil_bas <=to_unsigned( pardefaut, seuil_bas'length);
    ELSIF rising_edge(wclk) THEN 
      IF load = '1' THEN
        CASE c  IS
          WHEN 0 => seuil_haut <= d;
                    c := 1;
          WHEN 1 => seuil_bas <= d;
                    c := 2 ;
          WHEN OTHERS => NULL;
        END CASE;
      END IF;
    END IF;
  END PROCESS seuils;
  
  flag3 : PROCESS (rclk, reset_b)
  BEGIN  -- PROCESS flags
    IF reset_b = '0' THEN                -- asynchronous reset (active low)
      pae  <= '1';
    ELSIF rclk'event AND rclk = '1' THEN     -- rising clock edge
      IF  ecart < seuil_bas THEN
        pae <= '1';
      ELSE
        pae  <= '0';
      END IF; 
    END IF;
  END PROCESS flag3;


  flag4 : PROCESS (wclk, reset_b)
  BEGIN  -- PROCESS flags
    IF reset_b = '0' THEN                -- asynchronous reset (active low)
      paf  <= '0';                      -- vide
    ELSIF rclk'event AND rclk = '1' THEN     -- rising clock edge
      IF ecart < seuil_haut THEN
        paf <= '1';
      ELSE
        paf  <= '0';
      END IF; 
    END IF;
  END PROCESS flag4;
  
END;