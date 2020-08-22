package expression.parser;

import expression.*;
import expression.exceptions.IncorrectDataException;
import expression.exceptions.ParsingErrorException;
import expression.operations.NumberOperation;

public class ExpressionParser<T extends Number> {
    private int pos;
    private String expression;
    private NumberOperation<T> numberOperation;

    public ExpressionParser(NumberOperation<T> operation) {
        this.numberOperation = operation;
    }

    public CommonExpression<T> parse(String expression) throws ParsingErrorException {
        pos = 0;
        this.expression = expression;
        CommonExpression<T> answer = parse(0);
        if (pos < expression.length()) {
            throw new ParsingErrorException("Can't parse all string. Might be redundant closing brackets");
        }
        return answer;
    }

    private void checkOperation(String operation) throws ParsingErrorException {
        if (!(operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/") ||
                operation.equals("min") || operation.equals("max") || operation.equals("count"))) {
            throw new ParsingErrorException("Unknown symbol of operation in parser: " + operation + " in pos: " + pos);
        }
    }

    private String getSubstring(String string, int from, int len) {
        StringBuilder ans = new StringBuilder();
        for (int i = from; i < from + len; i++) {
            ans.append(string.charAt(i));
        }
        return ans.toString();
    }

    private CommonExpression<T> parse(int minExpectedPriority) throws ParsingErrorException {
        CommonExpression<T> left;

        skipBlanks();

        if (pos < expression.length() && expression.charAt(pos) == '(') {
            pos++;
            left = parse(0);
            if (pos == expression.length() || expression.charAt(pos) != ')') {
                throw new ParsingErrorException("Expected closing bracket at pos " + pos);
            }
            pos++;
        } else if (pos + 1 < expression.length() && expression.charAt(pos) == '-' &&
                !Character.isDigit(expression.charAt(pos + 1))) {
            pos++;
            left = new CheckedNegate<T>(parse(4), numberOperation);
        } else if (pos + 4 < expression.length() && getSubstring(expression, pos, 5).equals("count")) {
            pos += 5;
            left = new Count<T>(parse(5), numberOperation);
        }
        else {
            left = getToken();
        }

        skipBlanks();

        while (pos < expression.length()) {
            String operation = "";
            operation += expression.charAt(pos++);

            if (operation.equals(")")) {
                pos--;
                return left;
            }

            if (operation.equals("m") && pos + 1 < expression.length() &&
                    (getSubstring(expression, pos, 2).equals("in") || getSubstring(expression, pos, 2).equals("ax"))) {
                operation += getSubstring(expression, pos, 2);
                pos += 2;
            }

            if (operation.equals("c") && pos + 3 < expression.length() && getSubstring(expression, pos, 4).equals("ount")) {
                operation += getSubstring(expression, pos, 4);
                pos += 4;
            }
            checkOperation(operation);

            if (getPriority(operation) < minExpectedPriority) {
                pos -= operation.length();
                return left;
            }

            left = createOperation(left, parse(getPriority(operation) + 1), operation);
        }

        return left;
    }

    private CommonExpression<T> createOperation(CommonExpression<T> left, CommonExpression<T> right, String operation) throws ParsingErrorException {
        switch (operation) {
            case "-": return new CheckedSubtract<T>(left, right, numberOperation);
            case "+": return new CheckedAdd<T>(left, right, numberOperation);
            case "*": return new CheckedMultiply<T>(left, right, numberOperation);
            case "/": return new CheckedDivide<T>(left, right, numberOperation);
            case "min": return new Min<T>(left, right, numberOperation);
            case "max": return new Max<T>(left, right, numberOperation);

            default: throw new ParsingErrorException("Unknown symbol of operation in parser: " + operation + " in pos: " + pos);
        }
    }

    private void skipBlanks() {
        while (pos < expression.length() && Character.isWhitespace(expression.charAt(pos))) {
            pos++;
        }
    }

    private CommonExpression<T> getToken() throws ParsingErrorException {

        if (pos >= expression.length()) {
            throw new ParsingErrorException("Expected token in the end of text");
        }

        switch (expression.charAt(pos++)) {
            case 'x': return new Variable<T>("x");
            case 'y': return new Variable<T>("y");
            case 'z': return new Variable<T>("z");
            default: pos--; break;
        }

        if (Character.isDigit(expression.charAt(pos)) ||
                pos + 1 < expression.length() && expression.charAt(pos) == '-' && Character.isDigit(expression.charAt(pos + 1))) {
            return parseNumber();
        } else {
            throw new ParsingErrorException("Can't find token at " + pos + " symbol");
        }
    }

    private Const<T> parseNumber() throws ParsingErrorException {

        StringBuilder number = new StringBuilder();

        if (pos < expression.length() && expression.charAt(pos) == '-') {
            number.append(expression.charAt(pos++));
        }

        while(pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
            number.append(expression.charAt(pos++));
        }

        try {
            return new Const<T>(numberOperation.get(number.toString()));
        } catch (IncorrectDataException e) {
            throw new ParsingErrorException("Can't parse number at pos: " + pos);
        }
    }

    private int getPriority(String operation) {
        if (operation.equals("**") || operation.equals("//")) {
            return 3;
        }
        else if (operation.equals("*") || operation.equals("/")) {
            return 2;
        }
        else if (operation.equals("min") || operation.equals("max")){
            return 0;
        }
        else {
            return 1;
        }
    }
}