package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.operations.NumberOperation;

public class CheckedDivide<T extends Number> extends AbstractBinaryOperator<T> {

    public CheckedDivide(CommonExpression<T> left, CommonExpression<T> right, NumberOperation<T> operation) {
        super(left, right, operation);
    }

    @Override
    protected T makeOperation(T left, T right) throws DivisionByZeroException, OverflowException {
        return operation.divide(left, right);
    }

    @Override
    protected String getOperator() {
        return "/";
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
