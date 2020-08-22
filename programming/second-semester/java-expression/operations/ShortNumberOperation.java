package expression.operations;

import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;

public class ShortNumberOperation implements NumberOperation<Short> {

    @Override
    public Short get(String argument) throws IncorrectDataException {
        try {
            return (short) Integer.parseInt(argument);
        } catch (Exception e) {
            throw new IncorrectDataException("Can't parse " + argument);
        }
    }

    @Override
    public Short add(Short left, Short right) {
        return (short)(left + right);
    }

    @Override
    public Short subtract(Short left, Short right) throws OverflowException {
        return (short)(left - right);
    }

    @Override
    public Short multiply(Short left, Short right) throws OverflowException {
        return (short)(left * right);
    }


    @Override
    public Short divide(Short left, Short right) {
        return (short)(left / right);
    }

    @Override
    public Short negate(Short value) throws OverflowException {
        return (short)(-value);
    }

    @Override
    public Short count(Short value) {
        return (short) Integer.bitCount(0xffff & value);
    }

    @Override
    public Short max(Short left, Short right) {
        return left > right ? left : right;
    }

    @Override
    public Short min(Short left, Short right) {
        return left < right ? left : right;
    }
}
