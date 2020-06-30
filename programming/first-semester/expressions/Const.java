package expressions;

public class Const implements CommonExpression, TripleExpression {
    private int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Const) {
            return ((Const) other).value == this.value;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value;
    }
}
