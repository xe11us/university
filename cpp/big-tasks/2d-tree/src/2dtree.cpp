#include "primitives.h"
#include <optional>
#include <set>
#include <vector>

namespace kdtree {

    bool PointSet::empty() const {
        return (tree == nullptr);
    }

    std::size_t PointSet::size() const {
        return (tree == nullptr ? 0 : tree->subtree_size);
    }


    PointSet::MyIterator PointSet::begin() const {
        return MyIterator(tree);
    }

    PointSet::MyIterator PointSet::end() const {
        auto tmp = MyIterator(tree);
        tmp.end();
        return tmp;
    }

    double my_min(double a, double b) {
        return std::min(a, b);
    }

    double my_max(double a, double b) {
        return std::max(a, b);
    }

    void rect_change(Rect &rect, double (*func)(double, double), bool coord, double x) {
        if (!coord) { ///x
            rect.set_left_down(Point(func(rect.xmin(), x), rect.ymin()));
            rect.set_right_up(Point(func(rect.xmax(), x), rect.ymax()));
        } else {      ///y
            rect.set_left_down(Point(rect.xmin(), func(rect.ymin(), x)));
            rect.set_right_up(Point(rect.xmax(), func(rect.ymax(), x)));
        }
    }

    bool kdtree_recursive_contains(PointSet::node *tree, const Point &point, std::size_t depth) {
        if (tree == nullptr) {
            return false;
        } else if (tree->current == point) {
            return true;
        } else if ((depth % 2 == 0 && point.x() <= tree->current.x()) ||
                   (depth % 2 == 1 && point.y() <= tree->current.y())) {
            return kdtree_recursive_contains(tree->left, point, depth + 1);
        } else {
            return kdtree_recursive_contains(tree->right, point, depth + 1);
        }
    }

    bool PointSet::contains(const Point &point) const {
        return kdtree_recursive_contains(tree, point, 0);
    }

    int get_subtree_size(PointSet::node *nd) {
        return (nd == nullptr ? 0 : nd->subtree_size);
    }

    void tree_update(PointSet::node *tree, PointSet::node *child) {
        tree->xmin = std::min(tree->xmin, child->xmin);
        tree->xmax = std::max(tree->xmax, child->xmax);
        tree->ymin = std::min(tree->ymin, child->ymin);
        tree->ymax = std::max(tree->ymax, child->ymax);
    }

    void kdtree_recursive_put(PointSet::node *tree, const Point &point, std::size_t depth) {
        if ((depth % 2 == 0 && point.x() <= tree->current.x()) ||
            (depth % 2 == 1 && point.y() <= tree->current.y())) {
            if (tree->left == nullptr) {
                tree->left = new PointSet::node(point);
                tree->left->parent = tree;
            } else {
                kdtree_recursive_put(tree->left, point, depth + 1);
            }
            tree_update(tree, tree->left);
        } else {
            if (tree->right == nullptr) {
                tree->right = new PointSet::node(point);
                tree->right->parent = tree;
            } else {
                kdtree_recursive_put(tree->right, point, depth + 1);
            }
            tree_update(tree, tree->right);
        }

        tree->subtree_size = get_subtree_size(tree->left) + get_subtree_size(tree->right) + 1;
    }

    void PointSet::put(const Point &point) {
        if (contains(point)) {
            return;
        } else if (tree == nullptr) {
            tree = new node(point);
        } else {
            kdtree_recursive_put(tree, point, 0);
        }
    }

    void kdtree_range(PointSet::node *tree, const Rect &rect, Rect &current_rect,
                      std::vector <Point> &answer, std::size_t depth) {
        if (tree == nullptr || !current_rect.intersects(rect)) {
            return;
        } else {
            if (rect.contains(tree->current)) {
                answer.push_back(tree->current);
            }
            Rect old_rect = current_rect;
            double x = (depth % 2) ? tree->current.y() : tree->current.x();
            rect_change(current_rect, my_min, depth % 2, x);
            kdtree_range(tree->left, rect, current_rect, answer, depth + 1);
            current_rect = old_rect;
            rect_change(current_rect, my_max, depth % 2, x);
            kdtree_range(tree->right, rect, current_rect, answer, depth + 1);
            current_rect = old_rect;
        }
    }

    std::pair <PointSet::MyIterator, PointSet::MyIterator>
    PointSet::range(const Rect &rect) const {
        std::vector<Point> res;
        Rect current_rect = Rect(Point(tree->xmin, tree->ymin), Point(tree->xmax, tree->ymax));
        kdtree_range(tree, rect, current_rect, res, 0);
        PointSet answer;

        for (auto &i : res) {
            answer.put(i);
        }

        m_range_result_begin = answer.begin();
        m_range_result_end = answer.end();
        return std::make_pair(m_range_result_begin, m_range_result_end);
    }

    void update(const Point &point, std::set <Point> &near_k, std::optional <Point> &res) {
        if (!res) {
            return;
        }
        Point result = res.value();
        Point current = *near_k.begin();

        for (auto &it: near_k) {
            if (it.distance(point) > current.distance(point)) {
                current = it;
            }
        }

        if ((near_k.find(result) == near_k.end() || *near_k.find(result) != result) &&
            (point.distance(result) < point.distance(current))) {
            near_k.erase(current);
            near_k.insert(result);
        }
    }

    void kdtree_nearest(const PointSet::node *tree, const Point &point,
                        std::set <Point> &near_k, Rect &rect, std::size_t depth);

    void process_subtree(const PointSet::node *tree, const Point &point,
                         std::set <Point> &near_k, Rect &rect, std::size_t depth, bool left_is_first, double x) {
        std::optional <Point> res = tree->current;
        update(point, near_k, res);

        Rect old_rect = rect;

        if (left_is_first) {
            rect_change(rect, my_min, depth % 2, x);
            kdtree_nearest(tree->left, point, near_k, rect, depth + 1);
            rect = old_rect;
            rect_change(rect, my_max, depth % 2, x);
            kdtree_nearest(tree->right, point, near_k, rect, depth + 1);
        } else {
            rect_change(rect, my_max, depth % 2, x);
            kdtree_nearest(tree->right, point, near_k, rect, depth + 1);
            rect = old_rect;
            rect_change(rect, my_min, depth % 2, x);
            kdtree_nearest(tree->left, point, near_k, rect, depth + 1);
        }
        rect = old_rect;
    }

    void kdtree_nearest(const PointSet::node *tree, const Point &point,
                        std::set <Point> &near_k, Rect &rect, std::size_t depth) {
        Point current = *near_k.begin();

        for (auto &it: near_k) {
            if (it.distance(point) > current.distance(point)) {
                current = it;
            }
        }

        if (tree == nullptr || rect.distance(point) >= current.distance(point)) {
            return;
        } else {
            if ((near_k.find(tree->current) == near_k.end() || *near_k.find(tree->current) != tree->current) &&
                (point.distance(tree->current) < point.distance(current))) {
                near_k.erase(current);
                near_k.insert(tree->current);
            }

            bool flag = false;
            if ((depth % 2 == 0 && point.x() <= tree->current.x()) ||
                (depth % 2 == 1 && point.y() <= tree->current.y())) {
                flag = true;
            }

            if (depth % 2 == 0) {
                process_subtree(tree, point, near_k, rect, depth, flag, tree->current.x());
            } else {
                process_subtree(tree, point, near_k, rect, depth, flag, tree->current.y());
            }
        }
    }

    std::pair <PointSet::MyIterator, PointSet::MyIterator>
    PointSet::nearest(const Point &point, std::size_t k) const {
        if (k == 0) {
            PointSet answer;
            m_nearest_result_begin = answer.begin();
            m_nearest_result_end = answer.end();
            return std::make_pair(m_nearest_result_begin, m_nearest_result_end);
        }

        auto it = begin();
        std::set <Point> result;
        for (std::size_t i = 0; i < k && it != end(); i++) {
            result.insert(*it);
            it++;
        }

        Rect rect = Rect(Point(tree->xmin, tree->ymin), Point(tree->xmax, tree->ymax));
        kdtree_nearest(tree, point, result, rect, 0);

        PointSet answer;

        for (auto iterator : result) {
            answer.put(iterator);
        }

        m_nearest_result_begin = answer.begin();
        m_nearest_result_end = answer.end();
        return std::make_pair(m_nearest_result_begin, m_nearest_result_end);
    }

    std::optional<Point> PointSet::nearest(const Point &point) const {
        if (this->empty()) {
            return std::nullopt;
        }
        auto ans = nearest(point, 1);
        return (*ans.first);
    }

    std::ostream &operator << (std::ostream &stream, const PointSet &point) {
        stream << "pointset has " << point.size() << " points:\n";
        auto it = point.begin();
        while (it != point.end()) {
            stream << (*it);
            ++it;
        }
        return stream;
    }
}