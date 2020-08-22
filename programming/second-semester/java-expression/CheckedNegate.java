package expression;

import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class CheckedNegate<T extends Number> extends AbstractUnaryOperator<T> {

    public CheckedNegate(CommonExpression<T> expression, NumberOperation<T> operation) {
        super(expression, operation);
    }

    @Override
    protected T makeOperation(T value) throws OverflowException {
        return operation.negate(value);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean hasMorePriority() {
        return true;
    }
}
