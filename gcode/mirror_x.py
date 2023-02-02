#!/usr/bin/env python3

################################################
#
# Alle Koordinaten an der Y-Achse spiegeln
# und nach rechts schieben (-> keine negativen Koordinaten)
# Aufruf: mirror_x.py FILE
#
###############################################

import sys

if len(sys.argv) < 2:
    printf("Fehler: Zu wenige Argumente")
    sys.exit(1)

filename = sys.argv[1]

# Datei auslesen
lines = None
with open(filename, 'r') as f:
    lines = f.readlines()

# Größte Koordinate finden
x_max = 0
for line in lines:
    line = line.replace('\n', '')
    coords = line.split(' ')
    for coord in coords:
        if len(coord) < 1:
            continue
        if coord[0] == 'X':
            val = float(coord[1:])
            if val > x_max:
                x_max = val

# Spiegeln
lines_new = list()
for line in lines:
    line = line.replace('\n', '')
    coords = line.split(' ')
    line_new = ''
    for coord in coords:
        string = ''
        if len(coord) < 1:
            continue
        if coord[0] == 'X':
            string += 'X'
            string += str(round(x_max - float(coord[1:]), 2))
            string += ' '
            line_new += string
        else:
            line_new += coord + ' '
    lines_new.append(line_new[:-1] + '\n')

# In Datei schreiben
#print(lines_new)
with open(filename, 'w') as f:
    f.writelines(lines_new)
