package expression;

import expression.exceptions.*;

public interface GenericExpression<T extends Number> extends ToMiniString {
     T evaluate(T x) throws DivisionByZeroException, OverflowException, IncorrectDataException;
}
