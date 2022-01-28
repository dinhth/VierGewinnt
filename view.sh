#!/bin/bash
#Font and Color
RED='\033[31;1m'
YELLOW='\033[33;1m'
BOLD='\033[1m'
RESET='\033[0m'

#String message
welcome_message="${BOLD}---------------------------------------------------------${RESET}
Welcome to
${RED}
 _   _ _           _____               _             _
| | | (_)         |  __ \             (_)           | |
| | | |_  ___ _ __| |  \/ _____      ___ _ __  _ __ | |_
| | | | |/ _ \ '__| | __ / _ ${YELLOW}\ \ /\ / / | '_ \| '_ \| __|
\ \_/ / |  __/ |  | |_\ \  __/\ V  V /| | | | | | | | |_
 \___/|_|\___|_|   \____/\___| \_/\_/ |_|_| |_|_| |_|\__|
${RESET} (Vier Gewinnt / Connect Four)


Which view do you want?
Choose between ( ${BOLD}gui${RESET} | ${BOLD}tui${RESET} | ${BOLD}both${RESET} ):
"

#Code
printf "$welcome_message"
read RVIEW
if [[ $RVIEW == "gui" ]]; then
  sbt "run gui"
elif [[ $RVIEW == "tui" ]]; then
  sbt "run tui"
else
  sbt "run"
fi
