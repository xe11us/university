package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;

import java.math.BigInteger;

public class BigIntegerOperation implements NumberOperation<BigInteger> {
    @Override
    public BigInteger get(String argument) throws IncorrectDataException {
        try {
            return new BigInteger(argument);
        } catch (Exception e) {
            throw new IncorrectDataException("Can't parse " + argument);
        }
    }

    @Override
    public BigInteger add(BigInteger left, BigInteger right)  {
        return left.add(right);
    }

    @Override
    public BigInteger subtract(BigInteger left, BigInteger right)  {
        return left.subtract(right);
    }

    @Override
    public BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    private void checkDivide(BigInteger left, BigInteger right) throws DivisionByZeroException {
        if (right.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("Division " + left + " by zero");
        }
    }

    @Override
    public BigInteger divide(BigInteger left, BigInteger right) throws DivisionByZeroException {
        checkDivide(left, right);
        return left.divide(right);
    }

    @Override
    public BigInteger negate(BigInteger value) throws OverflowException {
        return value.negate();
    }

    @Override
    public BigInteger count(BigInteger value) {
        return BigInteger.valueOf(value.bitCount());
    }

    @Override
    public BigInteger max(BigInteger left, BigInteger right) {
        return left.max(right);
    }

    @Override
    public BigInteger min(BigInteger left, BigInteger right) {
        return left.min(right);
    }
}
