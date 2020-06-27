#include "primitives.h"

Vector::Vector(const Point &from, const Point &to) : m_x(to.x() - from.x()), m_y(to.y() - from.y()){}

double Vector::x() const {
    return m_x;
}

double Vector::y() const {
    return m_y;
}

double Vector::length() const {
    return sqrt(m_x * m_x + m_y * m_y);
}

double Vector::operator * (const Vector &other) const { ///vector multiply length
    return this->x() * other.y() - this->y() * other.x();
}

double Vector::operator % (const Vector &other) const { ///scalar
    return this->x() * other.x() + this->y() * other.y();
}