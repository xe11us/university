package expression;

import expression.exceptions.IncorrectDataException;

public interface GenericTripleExpression<T extends Number> extends ToMiniString, GenericExpression<T> {
    T evaluate(T x, T y, T z) throws IncorrectDataException;
}