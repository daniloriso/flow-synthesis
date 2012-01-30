----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    14:19:19 11/02/2006 
-- Design Name: 
-- Module Name:    MaFIFO - MaFIFO_Arch 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity MaFIFO is
    generic (
			fADD_WIDTH : integer := 6;  	-- Address Width
			fWIDTH     : integer := 16		-- FIFO word width
		); 
    Port ( data_in 	: in  STD_LOGIC_VECTOR(fWIDTH-1 downto 0);
           data_out  : out STD_LOGIC_VECTOR(fWIDTH-1 downto 0);
           reset 	   : in  STD_LOGIC;
           clk 		: in  STD_LOGIC;
           read 	   : in  STD_LOGIC;
           write 	   : in  STD_LOGIC;
           full 	   : out STD_LOGIC;
           NPBEmpty  : out STD_LOGIC);
end MaFIFO;

architecture MaFIFO_Arch of MaFIFO is

--COMPONENT fifo_gen IS
--  PORT(
--    d : IN  unsigned(7 DOWNTO 0);-- entree
--    reset_b : IN  std_logic; -- actif au niveau bas
--    wclk : IN  std_logic; -- horloge d'ecriture
--    we : IN  std_logic; -- autorise l'ecriture sur front montant de wclk
--    load : IN  std_logic; -- prioritaire pour l'entree des deux seuils
--    rclk : IN  std_logic; -- horloge de lecture
--    re  : IN  std_logic; -- autorise la lecture sur front montant de rclk 
--    oe_b : IN  std_logic; -- Si '0' rend le bus actif, sinon bus = "zzzz..."
--    q : OUT unsigned(7 DOWNTO 0);-- sortie trois  etats
--    empty : OUT std_logic;  -- fifo vide
--    pae : OUT std_logic; -- fifo presque vide ( selon le niveau programmable)
--    paf : OUT std_logic; -- fifo presque pleine ( selon le niveau programmable)
--    full : OUT std_logic
--  );
--END COMPONENT;

COMPONENT FIFO is
    generic (
			ADD_WIDTH : integer := 6;  	-- FIFO word width
			WIDTH     : integer := 8
	); -- Address Width
	port (
	  Data_in   : in std_logic_vector (WIDTH-1 downto 0);   -- Input data
	  Data_out  : out std_logic_vector(WIDTH-1 downto 0);  -- Out put data
	  clk       : in std_logic;  		-- System Clock
	  Reset     : in std_logic;  	   -- System global Reset
	  RE        : in std_logic;  		-- Read Enable
	  WE        : in std_logic;  		-- Write Enable
	  Full      : out std_logic;  	-- Full Flag
	  --Half_full : out std_logic;  	-- Half Full Flag
	  --Empty     : out std_logic; 	   -- Empty Flag
	  NPBEmpty  : out std_logic--;
	  --NEmpty    : out std_logic
	  );
end COMPONENT;

begin

  Instance: FIFO
	GENERIC MAP (ADD_WIDTH => fADD_WIDTH, WIDTH => fWIDTH)
	port map(
	   Data_in => data_in,
	   Data_out => data_out,
		clk   => clk,
  		Reset => reset,
		We    => write,
		Re    => read,
		--null => Half_full,
		--Empty     => empty,
		NPBEmpty   => NPBEmpty,
		Full      => full
	 );

--  Instance: fifo_gen
--  port map(	d  => unsigned(data_in),
--				reset_b => not reset,
--				wclk       => clk,
--		we     => write,
--		load       => '0',
--		rclk       => clk,
--		re      => read,
--		oe_b       => '0',
--		q	 => sortie,
--		empty     => empty,
--		full      => full
--	 );
--
--	data_out <= conv_std_logic_vector(sortie, 8);

end MaFIFO_Arch;


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity GENERIC_FIFO is
    generic (
      DATA : integer := 6; -- Address width
      BITS : integer := 16 -- FIFO word width
    ); 
  PORT ( 
      data_in  : in   STD_LOGIC_VECTOR (BITS-1 downto 0);
      data_out : out  STD_LOGIC_VECTOR (BITS-1 downto 0);
      reset    : in   STD_LOGIC;
      clk      : in   STD_LOGIC;
      read     : in   STD_LOGIC;
      write    : in   STD_LOGIC;
      full     : out  STD_LOGIC;
      empty    : out  STD_LOGIC
    );
end GENERIC_FIFO;

architecture GENERIC_FIFO_Arch of GENERIC_FIFO is

   COMPONENT FIFO is
   GENERIC (
			ADD_WIDTH : integer := 6;  	-- FIFO word width
			WIDTH     : integer := 8
	); -- Address Width
	PORT (
	  Data_in   : in std_logic_vector (WIDTH-1 downto 0);   -- Input data
	  Data_out  : out std_logic_vector(WIDTH-1 downto 0);  -- Out put data
	  clk       : in std_logic;  		-- System Clock
	  Reset     : in std_logic;  	   -- System global Reset
	  RE        : in std_logic;  		-- Read Enable
	  WE        : in std_logic;  		-- Write Enable
	  Full      : out std_logic;  	-- Full Flag
	  NPBEmpty  : out std_logic--;
	  );
   END COMPONENT;

begin

  Instance: FIFO
	GENERIC MAP (ADD_WIDTH => DATA, WIDTH => BITS)
	port map(
	   Data_in  => data_in,
	   Data_out => data_out,
		clk      => clk,
  		Reset    => reset,
		We       => write,
		Re       => read,
		NPBEmpty => empty,
		Full     => full
	 );

end GENERIC_FIFO_Arch;
