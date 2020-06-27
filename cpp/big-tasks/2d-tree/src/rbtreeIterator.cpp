#include "primitives.h"

namespace rbtree {
    PointSet::MyIterator::MyIterator(const PointSet::MyIterator &other) {
        this->m_it = other.m_it;
        this->m_answer = other.m_answer;
    }

    PointSet::MyIterator::MyIterator(std::set <Point> &st) {
        for (auto &it : st) {
            m_answer.emplace_back(it);
        }
    }

    PointSet::MyIterator::MyIterator(const std::set <Point> &st) {
        for (auto &it: st) {
            m_answer.emplace_back(it);
        }
    }

    PointSet::MyIterator::reference PointSet::MyIterator::operator * () {
        return m_answer[m_it];
    }

    PointSet::MyIterator::pointer PointSet::MyIterator::operator -> () {
        return (&m_answer[m_it]);
    }

    PointSet::MyIterator &PointSet::MyIterator::operator ++ () {
        m_it++;
        return *this;
    }

    PointSet::MyIterator PointSet::MyIterator::operator ++ (int) {
        auto tmp(*this);
        operator++();
        return tmp;
    }

    PointSet::MyIterator &PointSet::MyIterator::operator = (const MyIterator &other) {
        this->m_answer = other.m_answer;
        this->m_it = other.m_it;
        return *this;
    }

    bool PointSet::MyIterator::operator == (const MyIterator &other) const {
        return this->m_it == other.m_it && this->m_answer == other.m_answer;
    }

    bool PointSet::MyIterator::operator != (const MyIterator &other) const {
        return !(*this == other);
    }

    void PointSet::MyIterator::end() {
        m_it = m_answer.size();
    }
}