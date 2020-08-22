package expression;

import expression.operations.NumberOperation;

public class Count<T extends Number> extends AbstractUnaryOperator<T> {
    public Count(CommonExpression<T> exp, NumberOperation<T> oper) {
        super(exp, oper);
    }

    @Override
    public T makeOperation(T value) {
        return operation.count(value);
    }

    @Override
    protected String getOperator() {
        return "count ";
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public String toString() {
        return "count ";
    }

    @Override
    public boolean hasMorePriority() {
        return true;
    }
}
