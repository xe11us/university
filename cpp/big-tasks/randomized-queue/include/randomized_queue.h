#pragma once

#include <algorithm>
#include <cstdlib>
#include <vector>
#include <ctime>
#include <iostream>
#include <random>

template<class T>
class randomized_queue {
    template<bool is_const>
    class MyIterator {
    public:
        using value_type = std::conditional_t<is_const, std::add_const_t<T>, T>;
        using difference_type = std::ptrdiff_t;
        using pointer = value_type *;
        using reference = value_type &;
        using iterator_category = std::forward_iterator_tag;
        using begin_iterator_type = std::conditional_t<is_const, typename std::vector<T>::const_iterator,
                typename std::vector<T>::iterator>;

        MyIterator(begin_iterator_type a, size_t size, std::default_random_engine &random_generator, bool is_end) :
            m_begin_iterator(a), m_size(is_end ? 1 : size) {
            if (!is_end) {
                generate_permutation(random_generator);
            }
        }

        reference operator * () const {
            return *(m_begin_iterator + m_permutation[m_pos]);
        }

        pointer operator -> () const {
            return &(m_begin_iterator + m_permutation[m_pos]);
        }

        MyIterator &operator ++ () {
            ++m_pos;
            return *this;
        }

        MyIterator operator ++ (int) {
            auto tmp(*this);
            operator++();
            return tmp;
        }

        bool operator == (const MyIterator &other) const {
            return  (size_t(this->m_pos) == this->m_size && size_t(other.m_pos) == other.m_size) ||
                    (this->m_begin_iterator == other.m_begin_iterator && this->m_pos == other.m_pos
                    && this->m_permutation == other.m_permutation && this->m_size == other.m_size);
        }

        bool operator != (const MyIterator &other) const {
            return !operator == (other);
        }

        void end() {
            m_pos = m_size;
        }

    private:

        void generate_permutation(std::default_random_engine &random_generator) {
            for (size_t i = 0; i < m_size; ++i) {
                m_permutation.emplace_back(int(i));
            }

        std::shuffle(m_permutation.begin(), m_permutation.end(), random_generator);
        }

        begin_iterator_type m_begin_iterator;
        std::vector<int> m_permutation;
        size_t m_size = 0;
        int m_pos = 0;
    };



    ///===================================================queue=========================================================
    mutable std::default_random_engine m_generator = std::default_random_engine{std::random_device{}()};
    std::vector<T> m_queue;

public:
    using iterator = MyIterator<false>;
    using const_iterator = MyIterator<true>;

    bool empty() const {
        return size() == 0;
    }

    size_t size() const {
        return m_queue.size();
    }

    void enqueue(const T &element) {
        m_queue.emplace_back(element);
    }

    void enqueue(T &&element) {
        m_queue.emplace_back(std::move(element));
    }

    const T &sample() const {
        auto distribution = std::uniform_int_distribution<std::size_t>(0, size() - 1);
        return m_queue[distribution(m_generator)];
    }

    T dequeue() {
        auto distribution = std::uniform_int_distribution<std::size_t>(0, size() - 1);
        auto pos = distribution(m_generator);
        auto result(std::move(m_queue[pos]));

        m_queue[pos] = std::move(m_queue.back());
        m_queue.pop_back();
        return result;
    }

    iterator begin() {
        auto tmp = iterator(m_queue.begin(), m_queue.size(), m_generator, false);
        return tmp;
    }

    iterator end() {
        auto tmp = iterator(m_queue.begin(), m_queue.size(), m_generator, true);
        tmp.end();
        return tmp;
    }

    const_iterator begin() const {
        return cbegin();
    }

    const_iterator end() const {
        return cend();
    }

    const_iterator cbegin() const {
        auto tmp = const_iterator(m_queue.cbegin(), m_queue.size(), m_generator, false);
        return tmp;
    }

    const_iterator cend() const {
        auto tmp = const_iterator(m_queue.cbegin(), m_queue.size(), m_generator, true);
        tmp.end();
        return tmp;
    }
};