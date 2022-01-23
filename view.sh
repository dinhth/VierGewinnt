#!/bin/bash
read -rep $'What view do you want?\nChoose between: ( gui | tui | both ):\n' RVIEW
if [[ $RVIEW == "gui" ]]; then
  sbt "run gui"
elif [[ $RVIEW == "tui" ]]; then
  sbt "run tui"
else
  sbt "run"
fi
