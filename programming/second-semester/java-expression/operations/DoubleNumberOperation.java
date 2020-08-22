package expression.operations;

import expression.exceptions.IncorrectDataException;

public class DoubleNumberOperation implements NumberOperation<Double> {
    @Override
    public Double get(String argument) throws IncorrectDataException {
        try {
            return Double.parseDouble(argument);
        } catch (Exception e) {
            throw new IncorrectDataException("Can't parse " + argument);
        }
    }
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double multiply(Double left, Double right) {
        return left * right;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double negate(Double value) {
        return -value;
    }

    @Override
    public Double count(Double value) {
        return (double) Long.bitCount(Double.doubleToLongBits(value));
    }

    @Override
    public Double max(Double left, Double right) {
        return Double.max(left, right);
    }

    @Override
    public Double min(Double left, Double right) {
        return Double.min(left, right);
    }
}
