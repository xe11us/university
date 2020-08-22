package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class Min<T extends Number> extends AbstractBinaryOperator<T> {

    public Min(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> operation) {
        super(left, right, operation);
    }

    @Override
    protected T makeOperation(T left, T right) throws DivisionByZeroException, OverflowException, IncorrectDataException {
        return operation.min(left, right);
    }

    @Override
    protected String getOperator() {
        return "min";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean hasMorePriority() {
        return false;
    }
}
