#!/bin/bash

cp "memorylog/auto_memory_log.txt" "auto_memory_log.txt.bak"
java memorylog.MemoryLog

diff "memorylog/auto_memory_log.txt" "auto_memory_log.txt.bak"
wc -l "memorylog/auto_memory_log.txt"
wc -l "auto_memory_log.txt.bak"

echo "Press enter to continue."
read -s
rm "auto_memory_log.txt.bak"
