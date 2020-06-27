#pragma once

#include <random>
#include <utility>
#include <functional>

double generate_random_number();

double GetPi(std::function<double()> generator,
             std::size_t number_of_iterations);
