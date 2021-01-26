 #!/bin/bash
    
number=0
for PPid in `grep -Eo "Parent_ProcessID=[0-9]{1,}" "fourth.out"| awk -F "=" '{ print $2 }' | uniq`
do  
    average=$(grep -E "Parent_ProcessID=$PPid " "fourth.out"| awk -F "=" 'BEGIN { sum = 0; count = 0} { sum += $4; count++} END { print sum/count }')
    amount=$(grep -E "Parent_ProcessID=$PPid " "fourth.out"| wc -l)
    number=$(($number+$amount+1))
    sed -i "$number iAverage_Sleeping_Children_of_ParentID=$PPid is $average" "fourth.out"
done
