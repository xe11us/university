package expression;

import expression.exceptions.IncorrectDataException;

public class Variable<T extends Number> implements CommonExpression<T>, GenericTripleExpression<T> {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    public T evaluate(T x) {
        return x;
    }

    @Override
    public T evaluate(T x, T y, T z) throws IncorrectDataException {
        switch (this.name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new IncorrectDataException("Wrong variable name: " + this.name);
        }
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean hasMorePriority() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() == Variable.class) {
            return ((Variable)other).name.equals(this.name);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (name.charAt(0) * 41) % 61;
    }
}
