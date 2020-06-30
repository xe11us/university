package expressions;

public class Variable implements CommonExpression, TripleExpression {
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

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (name == "x") {
            return x;
        }
        else if (name == "y") {
            return y;
        }
        else {
            return z;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return ((Variable)other).name.equals(this.name);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
