#include "primitives.h"

Point::Point(double _x, double _y) : m_x(_x), m_y(_y){}

double Point::x() const {
    return m_x;
}

double Point::y() const {
    return m_y;
}

double Point::distance(const Point & other) const {
    return Vector(*this, other).length();
}

double Point::distance(const Point & from, const Point & to) const {
    Vector vector1 = Vector(from, *this);
    Vector vector2 = Vector(from, to);

    double scalar1 = vector1 % vector2;

    vector1 = Vector(to, *this);
    vector2 = Vector(to, from);

    double scalar2 = vector1 % vector2;

    if (scalar1 >= 0 && scalar2 >= 0 && !(scalar1 == 0 && scalar2 == 0)) {
        return std::abs(vector1 * vector2) / vector2.length();
    }
    else {
        return std::min(this->distance(from), this->distance(to));
    }
}

bool Point::operator < (const Point & other) const {
    return this->x() < other.x() || (this->x() == other.x() && this->y() < other.y());
}

bool Point::operator > (const Point & other) const {
    return this->x() > other.x() || (this->x() == other.x() && this->y() > other.y());
}

bool Point::operator <= (const Point & other) const {
    return this->x() < other.x() || (this->x() == other.x() && this->y() <= other.y());
}

bool Point::operator >= (const Point & other) const {
    return this->x() > other.x() || (this->x() == other.x() && this->y() >= other.y());
}

bool Point::operator == (const Point & other) const {
    return (this->x() == other.x() && this->y() == other.y());
}

bool Point::operator != (const Point & other) const {
    return (this->x() != other.x() || this->y() != other.y());
}

Point Point::operator - (const Point & other) const {
    return Point(this->x() - other.x(), this->y() - other.y());
}

std::ostream & operator << (std::ostream & out, const Point & point) {
    out << '(' << point.x() << ", " << point.y() << ')' << "\n";
    return out;
}
