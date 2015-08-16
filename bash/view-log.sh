#!/bin/sh
## open logs in text editor
if [ -e '../../../logs/dealing.log' ]; then
 gedit ../../../logs/dealing.log
else echo "No any logs to view."
fi