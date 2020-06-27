#include <algorithm>
#include <iostream>
#include <fstream>
#include <sstream>
#include <utility>
#include <vector>

struct my_line {
    std::string line;
    std::vector<std::string> splitted;

    my_line(std::string new_line, std::vector<std::string> new_splitted) : line(std::move(new_line)),
                                                                           splitted(std::move(new_splitted)) {}
};

std::vector<std::string> parse(const std::string &s, char separator) {
    std::vector <std::string> ans;
    std::istringstream is(s);
    std::string str;

    while (std::getline(is, str, separator)) {
        if (!str.empty()) {
            ans.push_back(str);
        }
    }

    return ans;
}

bool is_prefix(const std::string &line, const std::string &prefix) {
    auto res = std::mismatch(line.begin(), line.end(), prefix.begin());
    return res.first == line.end();
}

int read_int(const std::string &line, std::size_t from) {
    int res = 0;
    for (size_t pos = from; pos < line.length() && std::isdigit(line[pos]); pos++) {
        res = res * 10 + (line[pos] - '0');
    }
    return res;
}

int main(int argc, char **argv) {
    char separator = '\t';
    std::pair <std::size_t, std::size_t> sort_bounds(0, 0);

    for (int i = 2; i < argc; i++) {
        const std::string &word = argv[i];
        if (word == "-k") {
            int column = atoi(argv[++i]) - 1;
            sort_bounds = std::make_pair(column, column);
        } else if (word == "-t") {
            separator = argv[++i][0];
        } else {
            if (is_prefix(word, "--field-separator=")) {
                separator = word[word.length() - 1];
            } else if (is_prefix(word, "--key=")) {
                int column = read_int(word, 6) - 1;
                sort_bounds = std::make_pair(column, column);
                int comma_pos = word.find(',');

                if (comma_pos != -1) {
                    int column2 = read_int(word, comma_pos + 1) - 1;
                    sort_bounds = std::make_pair(sort_bounds.first, column2);
                }
            } else {
                std::cout << "wrong token: " << word << "\n";
                exit(0);
            }
        }
    }

    std::ifstream in(argv[1]);
    std::vector <my_line> table;
    std::string line;

    while (std::getline(in, line)) {
        std::vector <std::string> parsed_line = parse(line, separator);
        my_line p(std::move(line), std::move(parsed_line));
        table.push_back(p);
    }

    std::sort(table.begin(), table.end(),
              [&sort_bounds](const my_line &a, const my_line &b) {
                  if (!sort_bounds.second) {
                      return a.line < b.line;
                  } else {
                      for (std::size_t column_id = sort_bounds.first; column_id <= sort_bounds.second; ++column_id) {
                          if (column_id >= a.splitted.size() || column_id >= b.splitted.size()) {
                              return column_id >= a.splitted.size();
                          } else if (a.splitted[column_id] == b.splitted[column_id]) {
                              continue;
                          } else {
                              return a.splitted[column_id] < b.splitted[column_id];
                          }
                      }
                  }
                  return false;
              }
    );

    for (auto &i : table) {
        std::cout << i.line << "\n";
    }
    return 0;
}