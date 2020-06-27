#include "primitives.h"

namespace kdtree {

    PointSet::MyIterator::MyIterator() : m_tree(nullptr) {}

    PointSet::MyIterator::MyIterator(PointSet::node *new_tree) : m_tree(new_tree) {
        m_it = 0;
        node *iter = m_tree;
        node *last = m_tree;
        if (m_tree != nullptr) {
            while (iter->left != nullptr) {
                iter = iter->left;
            }

            while (last->right != nullptr) {
                last = last->right;
            }

            while (iter != last) {
                m_answer.push_back(iter->current);
                increment(iter);
            }
            m_answer.push_back(iter->current);
        }
    }

    PointSet::MyIterator::MyIterator(const PointSet::MyIterator &other) : m_answer(other.m_answer),
                                                                          m_it(other.m_it), m_tree(other.m_tree) {}


    void PointSet::MyIterator::increment(PointSet::node *&iter) {
        if (iter->right != nullptr) {
            iter = iter->right;
            while (iter->left != nullptr) {
                iter = iter->left;
            }
        } else if (iter == iter->parent->left) {
            iter = iter->parent;
        } else {
            while (iter == iter->parent->right) {
                iter = iter->parent;
            }
            iter = iter->parent;
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

    PointSet::MyIterator &PointSet::MyIterator::operator = (const PointSet::MyIterator &other) {
        this->m_answer = other.m_answer;
        this->m_it = other.m_it;
        this->m_tree = other.m_tree;
        return *this;
    }

    bool PointSet::MyIterator::operator == (const PointSet::MyIterator &other) const {
        if (m_it != other.m_it || m_answer.size() != other.m_answer.size()) {
            return false;
        }
        for (size_t i = 0; i < m_answer.size(); ++i) {
            if (m_answer[i] != other.m_answer[i]) {
                return false;
            }
        }
        return true;
    }

    bool PointSet::MyIterator::operator != (const PointSet::MyIterator &other) const {
        return !(PointSet::MyIterator::operator == (other));
    }

    void PointSet::MyIterator::end() {
        m_it = m_answer.size();
    }
}