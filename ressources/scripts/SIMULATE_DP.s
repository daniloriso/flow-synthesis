# DESCR : Le script AreaSynthesis a ete developpe afin de permettre la synthese sous contrainte
# DESCR : de ressource l'application FIR 16 taps. Les contraintes materielles utilisees sont les
# DESCR : suivantes : 
# DESCR : - 4 Multiplieurs
# DESCR : - 4 Additionneurs
#
# HELP : Le script AreaSynthesis lance l'ensemble des commandes necessaires afin de generer une
# HELP : architecture monomode sous contrainte materielle. Le script a besoin lors de son appel
# HELP : au minimum d'un parametre specifiant la latence maximum post-synthese :
# HELP : (1) Contrainte de latence (nombre de cycles d'horloge) : TYPE ENTIER
#
# AUTHOR : Bertrand LE GAL
#
# ARGS   : 2
#
exec(ghdl -a --workdir=work %2.vhd)
exec(ghdl -a --workdir=work %1.vhd)
exec(ghdl -a --workdir=work txt_util.vhd)
exec(ghdl -a --workdir=work %1_tb.vhd)
exec(ghdl -i --workdir=work %2.vhd)
exec(ghdl -i --workdir=work %1.vhd)
exec(ghdl -i --workdir=work %1_tb.vhd)
exec(ghdl -a --workdir=work txt_util.vhd)
exec(ghdl -m --workdir=work %1_tb)
exec(ghdl -r --workdir=work %1_tb)
