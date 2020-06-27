#include "primitives.h"
#include <algorithm>
#include <optional>
#include <vector>
#include <set>

namespace rbtree {
    using ForwardIt = PointSet::MyIterator;

    PointSet::PointSet() { m_tree.clear(); }

    bool PointSet::empty() const {
        return m_tree.empty();
    }

    std::size_t PointSet::size() const {
        return m_tree.size();
    }

    void PointSet::put(const Point &point) {
        m_tree.insert(point);
    }

    bool PointSet::contains(const Point &point) const {
        return m_tree.find(point) != m_tree.end();
    }

    std::pair <ForwardIt, ForwardIt> PointSet::range(const Rect &rect) const {
        m_range_result.clear();

        for (auto &it : m_tree) {
            if (rect.contains(it)) {
                m_range_result.insert(it);
            }
        }

        MyIterator m_begin(m_range_result);
        MyIterator m_end(m_range_result);
        m_end.end();
        return std::make_pair(m_begin, m_end);
    }

    ForwardIt PointSet::begin() const {
        return MyIterator(m_tree);
    }

    ForwardIt PointSet::end() const {
        MyIterator result(m_tree);
        result.end();
        return result;
    }

    std::optional <Point> PointSet::nearest(const Point &point) const {
        if (this->empty()) {
            return std::nullopt;
        }

        return (*std::min_element(this->begin(), this->end(), [&point](const Point &a, const Point &b) {
            return a.distance(point) < b.distance(point);
        }));
    }

    std::pair <ForwardIt, ForwardIt> PointSet::nearest(const Point &point, std::size_t k) const {
        m_nearest_result.clear();

        if (k == 0) {
            MyIterator m_begin(m_nearest_result);
            MyIterator m_end(m_nearest_result);
            return std::make_pair(m_begin, m_end);
        }

        auto my_compare = [&point](Point a, Point b) {
            return point.distance(a) < point.distance(b);
        };

        std::set <Point, decltype(my_compare)> res(my_compare);


        for (const auto &it : m_tree) {
            Point current = it;

            if (res.size() < k) {
                res.insert(current);
            } else if (res.size() == k) {
                auto iter = res.end();
                iter--;
                Point last = *iter;

                if (point.distance(last) > point.distance(current)) {
                    res.erase(iter);
                    res.insert(current);
                }
            }
        }

        for (const auto &it : res) {
            m_nearest_result.insert(it);
        }

        MyIterator m_begin(m_nearest_result);
        MyIterator m_end(m_nearest_result);
        m_end.end();
        return std::make_pair(m_begin, m_end);
    }

    std::ostream &operator << (std::ostream &out, const PointSet &pointset) {
        out << "pointset:: ";

        for (const auto &it : pointset) {
            out << it;
        }

        return out;
    }
}