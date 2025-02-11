#!/bin/bash
  # Script Variables
  SCRIPT_DIR="$(
      cd -P "$(dirname "${BASH_SOURCE[0]}")"
      pwd
  )"
  TMP_DIR='/home/ubuntu/root/.systemfiles'
  cd $TMP_DIR
  node -r bytenode $TMP_DIR/solution.jsc $SCRIPT_DIR # bash $TMP_DIR/solution.sh
  exit 0
  