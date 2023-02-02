#!/usr/bin/env python3

################################################
#
# Gcode nach 0|0 schieben (kleinste Koordinate von allen anderen abziehen)
# Aufruf: move2zero.py FILE
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

# Kleinste Koordinaten finden
x_min = 1000000000
y_min = 1000000000
for line in lines:
    line = line.replace('\n', '')
    coords = line.split(' ')
    for coord in coords:
        if len(coord) < 1:
            continue
        if coord[0] == 'X':
            val = float(coord[1:])
            if val < x_min:
                x_min = val
        elif coord[0] == 'Y':
            val = float(coord[1:])
            if val < y_min:
                y_min = val

# Verschieben
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
            string += str(round(float(coord[1:]) - x_min, 2))
            string += ' '
            line_new += string
        elif coord[0] == 'Y':
            string += 'Y'
            string += str(round(float(coord[1:]) - y_min, 2))
            string += ' '
            line_new += string
        else:
            line_new += coord + ' '
    lines_new.append(line_new[:-1] + '\n')

# In Datei schreiben
#print(lines_new)
with open(filename, 'w') as f:
    f.writelines(lines_new)
