package expressions;

public abstract class AbstractOperator implements CommonExpression {
    private CommonExpression left;
    private CommonExpression right;

    public int evaluate(int x, int y, int z) {
        return makeOperation(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
    }

    protected AbstractOperator(CommonExpression left, CommonExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int makeOperation(int left, int right);
    protected abstract String getOperator();
    protected abstract int getPriority();

    public String toString() {
        return "(" + left.toString() + " " + getOperator() + " " + right.toString() + ")";
    }

    public String toMiniString() {
        return getExpressionView(this.left, checkPriority(this.left)) + " " + getOperator() + " "
                + getExpressionView(this.right, checkPriority(this.right) || ifNeedBracket(this.right));
    }

    public int evaluate(int x) {
        return makeOperation(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() == this.getClass()) {
            AbstractOperator other1 = (AbstractOperator) other;
            return other1.left.equals(this.left) && other1.right.equals(this.right)
                    && other1.getOperator().equals(this.getOperator());
        }
        else {
            return false;
        }
    }

    private boolean checkPriority(CommonExpression other) {
        return (other instanceof AbstractOperator && ((AbstractOperator) other).getPriority() < this.getPriority());
    }

    public boolean ifNeedBracket(CommonExpression other) {
        return  (other instanceof AbstractOperator && ((this instanceof Subtract && ((AbstractOperator) other).getPriority() == 1)
                || this instanceof Divide)) ||
                (other instanceof Divide && this.getPriority() == 2);
    }

    private String getExpressionView(CommonExpression expression, boolean ifBrackets) {
        if (ifBrackets) {
            return "(" + expression.toMiniString() + ")";
        }
        else {
            return expression.toMiniString();
        }
    }

    @Override
    public int hashCode() {
        int p = 10007;
        return (this.left.hashCode() * p * p + this.right.hashCode() * p + this.getOperator().hashCode());
    }
}
