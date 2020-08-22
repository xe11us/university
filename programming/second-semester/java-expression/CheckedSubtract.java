package expression;

import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class CheckedSubtract<T extends Number> extends AbstractBinaryOperator<T> {

    public CheckedSubtract(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> op) {
        super(left, right, op);
    }

    @Override
    protected T makeOperation(T left, T right) throws OverflowException {
        return operation.subtract(left, right);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean hasMorePriority() {
        return true;
    }
}
