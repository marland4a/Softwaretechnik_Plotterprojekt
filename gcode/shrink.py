#!/usr/bin/env python3

import sys

result =[]
with open(sys.argv[1], 'r') as f:
    lines = f.readlines()
    l = 0
    for i in range(len(lines)):
        if 'Z' in lines[i]:
            l = 0
        if l == 0:
            result.append(lines[i])
        l += 1
        if l == 30:
            l = 0

with open(sys.argv[2], 'w') as f:
    for line in result:
        f.write(line)

