package expression;

import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class CheckedAdd<T extends Number> extends AbstractBinaryOperator<T> {

    public CheckedAdd(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> operation) {
        super(left, right, operation);
    }

    @Override
    protected T makeOperation(T left, T right) throws OverflowException {
        return operation.add(left, right);
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean hasMorePriority() {
        return false;
    }
}
