#pragma once

#include <iostream>
#include <vector>
#include <cmath>
#include <map>
#include <set>

class Point {
    double m_x = 0, m_y = 0;
public:

    Point() = default;

    Point(double x, double y);

    double x() const;

    double y() const;

    double distance(const Point &) const;

    double distance(const Point &from, const Point &to) const;

    bool operator < (const Point &) const;

    bool operator > (const Point &) const;

    bool operator <= (const Point &) const;

    bool operator >= (const Point &) const;

    bool operator == (const Point &) const;

    bool operator != (const Point &) const;

    Point operator - (const Point &) const;

    friend std::ostream &operator << (std::ostream &, const Point &);

    ~Point() = default;
};

class Vector {
    double m_x = 0, m_y = 0;

public:
    Vector(const Point &from, const Point &to);

    double x() const;

    double y() const;

    double length() const;

    double operator * (const Vector &other) const; ///vector multiply length

    double operator % (const Vector &other) const; ///scalar
};

class Rect {
    Point m_left_down, m_right_up;
public:

    Rect(const Point &left_bottom, const Point &right_top);

    double xmin() const;

    double ymin() const;

    double xmax() const;

    double ymax() const;

    void set_left_down(const Point &);

    void set_right_up(const Point &);

    double distance(const Point &) const;

    bool contains(const Point &) const;

    bool intersects(const Rect &) const;

    friend std::ostream &operator << (std::ostream &, const Rect &);
};

namespace rbtree {

    class PointSet {
        std::set <Point> m_tree;
        mutable std::set<Point> m_range_result;
        mutable std::set<Point> m_nearest_result;
    public:

        class MyIterator: public std::iterator <std::input_iterator_tag, Point> {
            std::vector <Point> m_answer;
            int m_it = 0;
        public:
            using pointer = Point *;
            using reference = Point &;

            MyIterator() = default;
            MyIterator(const MyIterator & other);

            MyIterator(std::set <Point> &st);

            MyIterator(const std::set <Point> &st);

            reference operator * ();

            pointer operator -> ();

            MyIterator & operator ++ ();

            MyIterator operator ++ (int);

            MyIterator & operator = (const MyIterator & other);

            bool operator == (const MyIterator & other) const;

            bool operator != (const MyIterator & other) const;

            void end();
        };

        using ForwardIt = MyIterator;

        PointSet();

        bool empty() const;

        std::size_t size() const;

        void put(const Point &);

        bool contains(const Point &) const;

        std::pair <ForwardIt , ForwardIt> range(const Rect &) const;

        ForwardIt begin() const;

        ForwardIt end() const;

        std::optional <Point> nearest(const Point &) const;

        std::pair <ForwardIt , ForwardIt> nearest(const Point &p, std::size_t k) const;

        friend std::ostream &operator << (std::ostream &, const PointSet &);

    };

}

namespace kdtree {

    class PointSet {
    public:
        struct node {
            Point current = Point();
            node *left;
            node *right;
            node *parent;
            int subtree_size;
            double xmin = 1e9, xmax = -1e9;
            double ymin = 1e9, ymax = -1e9;

            node(const Point &cur) {
                current = cur;
                xmin = xmax = cur.x();
                ymin = ymax = cur.y();
                subtree_size = 1;
                left = nullptr;
                right = nullptr;
                parent = nullptr;
            }

            ~node() {
                delete left;
                delete right;
            }
        };

    private:
        class MyIterator: public std::iterator <std::input_iterator_tag, Point> {
            using It = node *;
            std::vector <Point> m_answer;
            int m_it;
            It m_tree = nullptr;
        public:
            using pointer = Point *;
            using reference = Point &;

            MyIterator();
            MyIterator(It);
            MyIterator(const MyIterator &);

            void increment(PointSet::node *&);

            reference operator * () ;

            pointer operator -> () ;

            MyIterator & operator ++ ();

            MyIterator operator ++ (int);

            MyIterator & operator = (const MyIterator &);

            bool operator == (const MyIterator &) const;

            bool operator != (const MyIterator &) const;

            void end();
        };

        node *tree = nullptr;
        mutable MyIterator m_range_result_begin;
        mutable MyIterator m_range_result_end;
        mutable MyIterator m_nearest_result_begin;
        mutable MyIterator m_nearest_result_end;
    public:
        using ForwardIt = MyIterator;

        PointSet() = default;

        bool empty() const;

        std::size_t size() const;

        void put(const Point &);

        bool contains(const Point &) const;

        std::pair <ForwardIt , ForwardIt> range(const Rect &) const;

        ForwardIt begin() const;

        ForwardIt end() const;

        std::optional <Point> nearest(const Point &) const;

        std::pair <ForwardIt , ForwardIt> nearest(const Point &, std::size_t) const;

        friend std::ostream &operator << (std::ostream &, const PointSet &);

        ~PointSet() {
            delete tree;
        }
    };
}