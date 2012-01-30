cd($SAMPLES/Demo/MultiDelay/DCT8)
script(scripts/AreaSynthesis.s)
hls_GenDynamicMooreArchitecture2(design/SM_DCT_8_taps_Signed_16b_design.vhd,$SIGNED_ASIC90)
CoolEdit(design/SM_DCT_8_taps_Signed_16b_design.vhd)
