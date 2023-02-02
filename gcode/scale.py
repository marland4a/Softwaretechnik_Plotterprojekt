#!/usr/bin/env python3

##############################################
#
# Gcode / X/Y-Koordinaten skalieren
# Aufruf: scale.py SCALE FILE
#
#############################################

import sys

if len(sys.argv) < 3:
    print("Fehler: Nicht genug Parameter")
    sys.exit(1)

scale = float(sys.argv[1])
filename = sys.argv[2]

# Read all lines
lines = None
with open(filename, 'r') as f:
    lines = f.readlines()

lines_new = list()
# Iterate over lines, multiply with scale
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
            string += str(round(float(coord[1:]) * scale, 2))
            string += ' '
            line_new += string
        elif coord[0] == 'Y':
            string += 'Y'
            string += str(round(float(coord[1:]) * scale, 2))
            string += ' '
            line_new += string
        else:
            line_new += coord + ' '
    lines_new.append(line_new[:-1] + '\n')

# Write lines in file
#print(lines_new)
with open(filename, 'w') as f:
    f.writelines(lines_new)


