package expression.operations;

import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;

public class UIntegerNumberOperation implements NumberOperation<Integer> {

    @Override
    public Integer get(String argument) throws IncorrectDataException {
        try {
            return Integer.parseInt(argument);
        } catch (Exception e) {
            throw new IncorrectDataException("Can't parse " + argument);
        }
    }

    @Override
    public Integer add(Integer left, Integer right) {
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) throws OverflowException {
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) throws OverflowException {
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        return left / right;
    }

    @Override
    public Integer negate(Integer value) throws OverflowException {
        return -value;
    }

    @Override
    public Integer count(Integer value) {
        return Integer.bitCount(value);
    }

    @Override
    public Integer max(Integer left, Integer right) {
        return Integer.max(left, right);
    }

    @Override
    public Integer min(Integer left, Integer right) {
        return Integer.min(left, right);
    }
}
