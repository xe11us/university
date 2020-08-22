package expression;

import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class CheckedMultiply<T extends Number> extends AbstractBinaryOperator<T> {

    public CheckedMultiply(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> operation) {
        super(left, right, operation);
    }

    @Override
    protected T makeOperation(T left, T right) throws OverflowException {
        return operation.multiply(left, right);
    }

    @Override
    protected String getOperator() {
        return "*";
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean hasMorePriority() {
        return false;
    }
}
