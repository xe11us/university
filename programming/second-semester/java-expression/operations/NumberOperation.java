package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.OverflowException;

public interface NumberOperation<T extends Number> {
    T get(String argument) throws IncorrectDataException;
    T add(T left, T right) throws OverflowException;
    T subtract(T left, T right) throws OverflowException;
    T multiply(T left, T right) throws OverflowException;
    T divide(T left, T right) throws OverflowException, DivisionByZeroException;
    T negate(T value) throws OverflowException;

    T count(T value);
    T max(T left, T right);
    T min(T left, T right);
}
