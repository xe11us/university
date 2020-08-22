package expression.generic;

import expression.*;
import expression.operations.*;
import expression.parser.*;
import expression.exceptions.*;
import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, NumberOperation<?>> MODES = Map.of(
            "d",    new DoubleNumberOperation(),
            "i",    new IntegerNumberOperation(),
            "bi",   new BigIntegerOperation(),
            "u",    new UIntegerNumberOperation(),
            "l",    new LongNumberOperation(),
            "s",    new ShortNumberOperation()
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws IllegalModeException, ParsingErrorException {
        return getTable(expression, getOperation(mode), x1, x2, y1, y2, z1, z2);
    }

    private NumberOperation<?> getOperation(final String mode) throws IllegalModeException {
        if (MODES.containsKey(mode)) {
            return MODES.get(mode);
        }
        throw new IllegalModeException(mode);
    }

    private <T extends Number> Object[][][] getTable(String expression, NumberOperation<T> operation, int x1, int x2, int y1, int y2, int z1, int z2) throws ParsingErrorException {
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<T> parser = new ExpressionParser<T>(operation);
        GenericTripleExpression<T> expr = parser.parse(expression);

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        table[x - x1][y - y1][z - z1] = expr.evaluate(
                                operation.get(Integer.toString(x)),
                                operation.get(Integer.toString(y)),
                                operation.get(Integer.toString(z))
                        );
                    } catch (Exception e) {
                        table[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return table;
    }
}