#include "primitives.h"
#include <vector>

Rect::Rect(const Point & left_bottom, const Point & right_top)
    : m_left_down(left_bottom), m_right_up(right_top) {}

double Rect::xmin() const {
    return m_left_down.x();
}

double Rect::ymin() const {
    return m_left_down.y();
}

double Rect::xmax() const {
    return m_right_up.x();
}

double Rect::ymax() const {
    return m_right_up.y();
}

void Rect::set_left_down(const Point & point) {
    m_left_down = point;
}

void Rect::set_right_up(const Point & point) {
    m_right_up = point;
}

bool between(double a, double left, double right) {
    return left <= a && a <= right;
}

double Rect::distance(const Point &p) const {
    if (between(p.x(), this->m_left_down.x(), this->m_right_up.x()) &&
        between(p.y(), this->m_left_down.y(), this->m_right_up.y())) {
        return 0.0;
    } else if (this->m_left_down == this->m_right_up) {
        return p.distance(this->m_left_down);
    }

    std::vector <Point> points;
    points.emplace_back(this->xmin(), this->ymin());
    points.emplace_back(this->xmin(), this->ymax());
    points.emplace_back(this->xmax(), this->ymax());
    points.emplace_back(this->xmax(), this->ymin());

    double answer = this->m_left_down.distance(p);

    for (int i = 0; i < 4; i++) {
        answer = std::min(answer, p.distance(points[i], points[(i + 1) % 4]));
    }

    return answer;
}

bool Rect::contains(const Point &p) const {
    return (between(p.x(), m_left_down.x(), m_right_up.x()) &&
            between(p.y(), m_left_down.y(), m_right_up.y()));
}

bool Rect::intersects(const Rect & other) const {
    double x_other[2] = {other.xmin(), other.xmax()};
    double y_other[2] = {other.ymin(), other.ymax()};

    for (double x_id : x_other) {
        for (double y_id : y_other) {
            if (this->contains(Point(x_id, y_id))) {
                return true;
            }
        }
    }

    double x_this[2] = {this->xmin(), this->xmax()};
    double y_this[2] = {this->ymin(), this->ymax()};

    for (double x_id : x_this) {
        for (double y_id : y_this) {
            if (other.contains(Point(x_id, y_id))) {
                return true;
            }
        }
    }

    return false;
}

std::ostream &operator << (std::ostream &stream, const Rect &rect) {
    stream << "Rect: from " << rect.m_left_down << " to " << rect.m_right_up << "\n";
    return stream;
}