package expression;

public interface CommonExpression<T extends Number> extends GenericExpression<T>, GenericTripleExpression<T> {
    int getPriority();
    boolean hasMorePriority();
}
