package expressions;

public class Divide extends AbstractOperator {

    public Divide(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    protected int makeOperation(int left, int right) {
        return left / right;
    }

    @Override
    protected String getOperator() {
        return "/";
    }

    @Override
    protected int getPriority() {
        return 2;
    }
}
