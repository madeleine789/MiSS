#!/bin/bash
#
# This script uses ImageMagick (http://www.imagemagick.org) for movie generation
#
convert -delay 50 -quality 100 *.png outputfile.mpeg
rm -f *.png