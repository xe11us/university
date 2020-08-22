package expression.operations;

import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;

public class LongNumberOperation implements NumberOperation<Long> {

    @Override
    public Long get(String argument) throws IncorrectDataException {
        try {
            return Long.parseLong(argument);
        } catch (Exception e) {
            throw new IncorrectDataException("Can't parse " + argument);
        }
    }

    @Override
    public Long add(Long left, Long right) {
        return left + right;
    }

    @Override
    public Long subtract(Long left, Long right) throws OverflowException {
        return left - right;
    }

    @Override
    public Long multiply(Long left, Long right) throws OverflowException {
        return left * right;
    }


    @Override
    public Long divide(Long left, Long right) {
        return left / right;
    }

    @Override
    public Long negate(Long value) throws OverflowException {
        return -value;
    }

    @Override
    public Long count(Long value) {
        return (long)Long.bitCount(value);
    }

    @Override
    public Long max(Long left, Long right) {
        return Long.max(left, right);
    }

    @Override
    public Long min(Long left, Long right) {
        return Long.min(left, right);
    }
}
