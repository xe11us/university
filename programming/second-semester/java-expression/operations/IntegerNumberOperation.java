package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;

public class IntegerNumberOperation implements NumberOperation<Integer> {

    @Override
    public Integer get(String argument) throws IncorrectDataException {
        try {
            return Integer.parseInt(argument);
        } catch (Exception e) {
            throw new IncorrectDataException("Can't parse " + argument);
        }
    }

    private void checkAdd(Integer left, Integer right) throws OverflowException {
        if ((left > 0 && right > Integer.MAX_VALUE - left) || (left < 0 && right < Integer.MIN_VALUE - left)) {
            throw new OverflowException("Overflow in add " + left + " to " + right);
        }
    }

    @Override
    public Integer add(Integer left, Integer right) {
        checkAdd(left, right);
        return left + right;
    }

    private void checkSubtract(Integer left, Integer right) throws OverflowException {
            if ((right < 0 && left > right + Integer.MAX_VALUE) || (right > 0 && left < right + Integer.MIN_VALUE)) {
                throw new OverflowException("Overflow in subtract " + right + " from " + left);
            }
    }

    @Override
    public Integer subtract(Integer left, Integer right) throws OverflowException {
        checkSubtract(left, right);
        return left - right;
    }

    private void checkMultiply(Integer left, Integer right) {
        if (left == 0 || right == 0 || Math.min(left, right) == Integer.MIN_VALUE && Math.max(left, right) == 1) {
            return;
        }
        if (!(left != Integer.MIN_VALUE && right != Integer.MIN_VALUE &&
                (((left >= 0 || right >= 0) && (left <= 0 || right <= 0)) || Math.abs(left) <= Integer.MAX_VALUE / Math.abs(right)) &&
                (((left >= 0 || right <= 0) && (left <= 0 || right >= 0)) || Math.abs(right) == 1 || Math.abs(left) <= Math.abs(Integer.MIN_VALUE / right)))) {
            throw new OverflowException("Overflow in multiply " + left + " to " + right);
        }
    }

    @Override
    public Integer multiply(Integer left, Integer right) throws OverflowException {
        checkMultiply(left, right);
        return left * right;
    }

    private void checkDivide(Integer left, Integer right) throws OverflowException, DivisionByZeroException {
        if (right == 0) {
            throw new DivisionByZeroException("Division " + left + " by zero");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException("Overflow in multiply " + left + " to " + right);
        }
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        checkDivide(left, right);
        return left / right;
    }

    private void checkNegate(Integer value) throws OverflowException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow in negate of " + value);
        }
    }

    @Override
    public Integer negate(Integer value) throws OverflowException {
        checkNegate(value);
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
