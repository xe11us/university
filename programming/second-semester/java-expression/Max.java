package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class Max<T extends Number> extends AbstractBinaryOperator<T> {

    public Max(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> operation) {
        super(left, right, operation);
    }
    @Override
    protected T makeOperation(T left, T right) throws DivisionByZeroException, OverflowException, IncorrectDataException {
        return operation.max(left, right);
    }

    @Override
    protected String getOperator() {
        return "max";
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
