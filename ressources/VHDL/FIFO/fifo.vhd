-------------------------------------------------------------------------------
-- 
-- Copyright Jamil Khatib 1999
-- 
--
-- This VHDL design file is an open design; you can redistribute it and/or
-- modify it and/or implement it under the terms of the Openip General Public
-- License as it is going to be published by the OpenIP Organization and any
-- coming versions of this license.
-- You can check the draft license at
-- http://www.openip.org/oc/license.html
--
--
-- Creator : Jamil Khatib
-- Date 10/10/99
--
-- version 0.19991226
--
-- This file was tested on the ModelSim 5.2EE
-- The test vecors for model sim is included in vectors.do file
-- This VHDL design file is proved through simulation but not verified on Silicon
-- 
--
-------------------------------------------------------------------------------
-------------------------------------------------------------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

USE ieee.std_logic_unsigned.ALL;



-- Dual port Memory core



ENTITY dpmem IS
generic( ADD_WIDTH : integer := 6 ;
		       WIDTH : integer := 8 );
  PORT (
    clk      : IN  std_logic;                                -- write clock
    reset    : IN  std_logic;                                -- System Reset
    W_add    : IN  std_logic_vector(add_width -1 downto 0);  -- Write Address
    R_add    : IN  std_logic_vector(add_width -1 downto 0);  -- Read Address
    Data_In  : IN  std_logic_vector(WIDTH - 1  DOWNTO 0);    -- input data
    Data_Out : OUT std_logic_vector(WIDTH -1   DOWNTO 0);    -- output Data
    WR       : IN  std_logic--;                                -- Write Enable
    --RE       : IN  std_logic
	 );                               -- Read Enable
END dpmem;

-------------------------------------------------------------------------------
-------------------------------------------------------------------------------

ARCHITECTURE dpmem_v3 OF dpmem IS


  TYPE data_array IS ARRAY (integer range <>) OF std_logic_vector(WIDTH -1  DOWNTO 0);
                                        -- Memory Type
  SIGNAL data : data_array(0 to (2** add_width) );  -- Local data



  --procedure init_mem(signal memory_cell : inout data_array ) is
  --begin

  --  for i in 0 to (2** add_width) loop
  --    memory_cell(i) <= (others => '0');
  --  end loop;

  --end init_mem;


BEGIN  -- dpmem_v3

  PROCESS (clk, reset, data, R_add)

  BEGIN  -- PROCESS
    -- activities triggered by asynchronous reset (active low)
    IF reset = '1' THEN
      --data_out <= (OTHERS => 'X');
      --init_mem (data);

      -- activities triggered by rising edge of clock
    ELSIF clk'event AND clk = '1' THEN
      --IF RE = '1' THEN								-- BLG : Modification pour adapter
      --else												--    mes besoins reels. Cela permet
      --  data_out <= "11110000";					--    d'avoir en permanence en sortie
      --END IF;											--    la prochaine valeur a depiler
      IF WR = '1' THEN
        data(conv_integeR(W_add)) <= Data_In;
      END IF;
    END IF;
    
	 data_out <= data(conv_integer(R_add));	-- 	le comportement de la fifo a

  END PROCESS;
END dpmem_v3;


-------------------------------------------------------------------------------
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;


entity FIFO is
    
    generic (
			ADD_WIDTH : integer := 6;  	-- FIFO word width
			WIDTH     : integer := 8
		); -- Address Width

    port (
		Data_in  : in std_logic_vector(WIDTH - 1 downto 0);  -- Input data
		Data_out : out std_logic_vector(WIDTH - 1 downto 0);     -- Out put data
		clk : in std_logic;  		   -- System Clock
		Reset : in std_logic;  	   -- System global Reset
		RE : in std_logic;  		   -- Read Enable
		WE : in std_logic;  		   -- Write Enable
		Full : out std_logic;  	   -- Full Flag
	  --Half_full : out std_logic;  -- Half Full Flag
	  --Empty  : out std_logic; 	   -- Empty Flag
		NEmpty : out std_logic; 	   -- Empty Flag
		NPBEmpty : out std_logic); 	-- Empty Flag

end FIFO;

-- Exactly teh same as FIFO_v5 but ieee.numeric_std.all is used
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------
-- purpose: FIFO Architecture
architecture ARCH_FIFO of FIFO is

-- constant values
	constant MAX_ADDR  : std_logic_vector(ADD_WIDTH -1 downto 0)  := (others => '1');
	constant MIN_ADDR  : std_logic_vector(ADD_WIDTH -1 downto 0)  := (others => '0');
	--constant HALF_ADDR : std_logic_vector(ADD_WIDTH -1 downto 0)  := "011111";--(ADD_WIDTH -1 downto ADD_WIDTH -1 => '0' ,others => '1');

   signal R_ADD   : std_logic_vector(ADD_WIDTH - 1 downto 0);  -- Read Address
   signal W_ADD   : std_logic_vector(ADD_WIDTH - 1 downto 0);  -- Write Address
   signal D_ADD   : std_logic_vector(ADD_WIDTH - 1 downto 0);  -- Diff Address

   --signal REN_INT : std_logic;  		-- Internal Read Enable
   signal WEN_INT : std_logic;  		-- Internal Write Enable

	signal  sFull :  std_logic;  	-- Full Flag
	signal  sEmpty  :  std_logic; 	-- Empty Flag


	component dpmem
	    generic (ADD_WIDTH : integer := 6;
	   			     WIDTH : integer := 8 );

   port (clk : in std_logic;
	    reset : in std_logic;
	  	 w_add : in std_logic_vector(ADD_WIDTH -1 downto 0 );
	    r_add : in std_logic_vector(ADD_WIDTH -1 downto 0 );
	    data_in : in std_logic_vector(WIDTH - 1 downto 0);
	    data_out : out std_logic_vector(WIDTH - 1 downto 0 );
	    WR  : in std_logic--;
	    --RE  : in std_logic
		 );
	end component;

	
    
begin  -- FIFO_v6

-------------------------------------------------------------------------------        

memcore: dpmem 
generic map (ADD_WIDTH => ADD_WIDTH,
				WIDTH => WIDTH)
	port map (clk => clk,
			 reset => reset,
			 w_add => w_add,
			 r_add => r_add,
			 Data_in => data_in,
			 data_out => data_out,
			 wr => wen_int--,
			 --re => ren_int
			 );

-------------------------------------------------------------------------------


-------------------------------------------------------------------------------

Add_gen: process(clk,reset)    
   VARIABLE COUNTER : UNSIGNED(ADD_WIDTH-1 downto 0);  -- Diff Address
   begin  -- process ADD_gen

      -- activities triggered by asynchronous reset (active low)
      if reset = '1' then
			W_ADD <= (others =>'0');
	   	R_ADD <= (others =>'0');
	   	D_ADD <= (others =>'0');
			-- activities triggered by rising edge of clock
      elsif clk'event and clk = '1'  then
			COUNTER := UNSIGNED( D_ADD );
			
			if WE = '1' and ( sFULL = '0') then
				W_ADD   <= STD_LOGIC_VECTOR( UNSIGNED(W_ADD) + 1 );
				COUNTER := COUNTER + 1;
--        else
--	   		W_ADD <= W_ADD;
--			D_ADD <= D_ADD;
			end if;
			
			if RE = '1' and ( sEMPTY = '0') then
				R_ADD   <= STD_LOGIC_VECTOR( UNSIGNED(R_ADD) + 1 );
				ASSERT(COUNTER /= 0) REPORT "ERREUR DE LECTURE (COUNTER == 0)";
				COUNTER := COUNTER - 1;
--        else
--	   		R_ADD <= R_ADD;
--			D_ADD <= D_ADD;
			end if;

			D_ADD <= STD_LOGIC_VECTOR( COUNTER );
		end if;
		
--	    R_ADD  <= q2;
--		W_ADD  <= q1;
--		D_ADD  <= q3;

   end process ADD_gen;

-------------------------------------------------------------------------------

	sFULL     <=  '1' when (D_ADD(ADD_WIDTH - 1 downto 0) = MAX_ADDR)  else '0';
	sEMPTY    <=  '1' when UNSIGNED(D_ADD) = TO_UNSIGNED(0, ADD_WIDTH) else '0';
	--NEmpty    <=  '1' when (D_ADD(ADD_WIDTH - 1 downto 0) = (MIN_ADDR+1)) AND ( RE = '1' AND WE = '0' ) else '0';
	wen_int   <=  '1' when (WE = '1' and ( sFULL = '0')) else '0';

	FULL  <=  sFULL;
	
	myEMPTY: process(D_ADD,RE,WE,sEMPTY)
   begin  -- process ADD_gen
		IF (UNSIGNED(D_ADD) = TO_UNSIGNED(1, ADD_WIDTH)) AND (RE = '1' AND WE = '0') THEN
			NPBEmpty <= '1';
		ELSIF (UNSIGNED(D_ADD) = TO_UNSIGNED(0, ADD_WIDTH)) AND (RE = '1' AND WE = '1') THEN
			NPBEmpty <= '1';
		ELSIF (sEMPTY = '1') AND (WE = '1' AND WE = '0') THEN
			NPBEmpty <= '0';
		ELSE
			NPBEmpty <= sEMPTY;
		END IF;
   end process myEMPTY;
-------------------------------------------------------------------------------

        
end ARCH_FIFO;



