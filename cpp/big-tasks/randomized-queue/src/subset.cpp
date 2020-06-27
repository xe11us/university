#include "randomized_queue.h"
#include <iostream>
#include "subset.h"

void subset(unsigned long k, std::istream & in, std::ostream & out)
{
    out.clear();
    randomized_queue<std::string> q;
    std::string current;

    while (std::getline(in, current)) {
        q.enqueue(current);
    }

    auto it = q.begin();
    auto it_end = q.end();

    for (size_t i = 0; i < k && it != it_end; i++, it++) {
        out << *it << '\n';
    }
}