package expression;

public class Const<T extends Number> implements CommonExpression<T>, GenericTripleExpression<T> {
    public T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public T evaluate(T x) {
        return value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
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
        if (other.getClass() == Const.class) {
            return ((Const) other).value.equals(this.value);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
