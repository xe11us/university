sc queryex type=service state=all > all_info.txt
sc pause LanmanWorkstation
C:\Windows\System32\timeout.exe 10
sc queryex type=service state=all > new_info.txt
cmd /c compare.cmd
sc continue LanmanWorkstation