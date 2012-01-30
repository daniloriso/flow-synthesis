# Ceci est un commentaire en en-tete du script
# normalement cela ne devrait pas être chargé
# lors de la phase d'analyse.
#
# Auteur : Bertrand LE GAL
#
# Description : script pour valider...
#
Testing(%1)
dyn_LoadInputRange(%1.bitwise)
dyn_ComputeDynamique()
dyn_ComputeBitwise()
dyn_AnalyzeBitwise()
DreamPlot
