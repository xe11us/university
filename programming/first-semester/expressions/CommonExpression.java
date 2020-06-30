package expressions;

public interface CommonExpression extends Expression, TripleExpression {
    int evaluate(int x);
    int evaluate(int x, int y, int z);
    String toString();
    String toMiniString();
}
