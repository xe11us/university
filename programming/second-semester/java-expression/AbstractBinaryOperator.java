package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public abstract class AbstractBinaryOperator<T extends Number> implements  CommonExpression<T>, GenericTripleExpression<T> {
    private CommonExpression<T> left;
    private CommonExpression<T> right;
    protected NumberOperation<T> operation;

    public AbstractBinaryOperator(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> op) {
        this.left = left;
        this.right = right;
        this.operation = op;
    }

    protected abstract T makeOperation(T left, T right) throws DivisionByZeroException, OverflowException, IncorrectDataException;
    protected abstract String getOperator();
    public abstract int getPriority();

    public String toString() {
        return "(" + left.toString() + " " + getOperator() + " " + right.toString() + ")";
    }

    public String toMiniString() {
        return getExpressionView(this.left, priority(this.left)) + " " + getOperator() + " "
                + getExpressionView(this.right, ifNeedBrackets(this.right));
    }

    public T evaluate(T x) throws DivisionByZeroException, OverflowException, IncorrectDataException {
        return makeOperation(left.evaluate(x), right.evaluate(x));
    }

    public T evaluate(T x, T y, T z) throws DivisionByZeroException, OverflowException, IncorrectDataException {
        return makeOperation(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() == this.getClass()) {
            return ((AbstractBinaryOperator) other).left.equals(this.left) && ((AbstractBinaryOperator) other).right.equals(this.right)
                    && ((AbstractBinaryOperator) other).getOperator().equals(this.getOperator());
        }
        else {
            return false;
        }
    }

    private boolean priority(CommonExpression<T> other) {
        return other.getPriority() < this.getPriority();
    }

    private boolean ifNeedBrackets(CommonExpression<T> other) {
        return priority(other) || (other.getPriority() == this.getPriority() && other.hasMorePriority());
    }

    private String getExpressionView(CommonExpression<T> expression, boolean ifBrackets) {
        if (ifBrackets) {
            return "(" + expression.toMiniString() + ")";
        }
        else {
            return expression.toMiniString();
        }
    }

    @Override
    public int hashCode() {
        return (this.left.hashCode() * 31 + this.right.hashCode() * 7333 + this.getClass().hashCode()) % 1000000007;
    }
}
