package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public abstract class AbstractUnaryOperator<T extends Number> implements CommonExpression<T>, GenericTripleExpression<T> {
    private CommonExpression<T> expression;
    protected NumberOperation<T> operation;

    public AbstractUnaryOperator(CommonExpression<T> expression, NumberOperation<T> op) {
        this.expression = expression;
        this.operation = op;
    }

    protected abstract T makeOperation(T value) throws DivisionByZeroException, OverflowException, IncorrectDataException;
    protected abstract String getOperator();
    public abstract int getPriority();

    public String toString() {
        return "(" + this.expression.toString() + ")";
    }

    public String toMiniString() {
        return "(" + expression.toMiniString() + ")";
    }

    public T evaluate(T x) throws DivisionByZeroException, OverflowException, IncorrectDataException {
        return makeOperation(this.expression.evaluate(x));
    }

    public T evaluate(T x, T y, T z) throws DivisionByZeroException, OverflowException, IncorrectDataException {
        return makeOperation(this.expression.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() == this.getClass()) {
            return ((AbstractUnaryOperator) other).expression.equals(this.expression)
                    && ((AbstractUnaryOperator) other).getOperator().equals(this.getOperator());
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (this.expression.hashCode() * 31 + this.getClass().hashCode()) % 1000000007;
    }
}
