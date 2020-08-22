"use strict";

function Const(value) {
    this.value = value;
    this.prototype = Const.prototype;
}

Const.prototype.evaluate = function() {
    return this.value;
};

Const.prototype.toString = Const.prototype.prefix = Const.prototype.postfix = function() {
    return this.value.toString();
};

Const.prototype.diff = function() {
    return Const.ZERO;
};
Const.E = new Const(Math.E);
Const.ZERO = new Const(0);
Const.ONE = new Const(1);


const variableIndexes = {
    'x': 0,
    'y': 1,
    'z': 2
};

function Variable(name) {
    this.name = name;
    this.index = variableIndexes[name];
}

Variable.prototype.evaluate = function(...args) {
    return args[this.index];
};

Variable.prototype.toString = Variable.prototype.prefix = Variable.prototype.postfix = function() {
    return this.name.toString();
};

Variable.prototype.diff = function(name) {
    return this.name === name ? Const.ONE : Const.ZERO;
};

const Operation = function() {};

Operation.prototype.evaluate = function(...args) {
    return this.func(...this.args.map((expression) => expression.evaluate(...args)));
};

Operation.prototype.toString = function() {
    return [...this.args, this.symbol].join(' ');
};

Operation.prototype.postfix = function() {
    return this.args.length === 0
        ? '( ' + this.symbol + ')'
        : '(' + [...this.args.map((expression) => expression.postfix()), this.symbol].join(' ') + ')';
};

Operation.prototype.prefix = function() {
    return '(' + [this.symbol, ...this.args.map((expression) => expression.prefix())].join(' ') + ')';
};

Operation.prototype.diff = function(name) {
    return this.diffFunction(name, ...this.args);
};

function createOperation(func, symbol, diffFunction) {
    let NewOperation = function(...args) {
        this.args = args;
    };
    NewOperation.prototype = Object.create(Operation.prototype);
    NewOperation.prototype.constructor = NewOperation;
    NewOperation.prototype.func = func;
    NewOperation.prototype.symbol = symbol;
    NewOperation.prototype.diffFunction = diffFunction;
    NewOperation.prototype.argsAmount = func.length;
    return NewOperation;
}

const Add = createOperation((x, y) => x + y, '+', (name, x, y) => new Add(x.diff(name), y.diff(name)));

const Subtract = createOperation((x, y) => x - y, '-', (name, x, y) => new Subtract(x.diff(name), y.diff(name)));

const Multiply = createOperation((x, y) => x * y, '*', (name, x, y) => new Add(new Multiply(x.diff(name), y),
                                                                       new Multiply(x, y.diff(name))));

const Divide = createOperation((x, y) => x / y, '/',
    (name, x, y) => new Divide(new Subtract(new Multiply(x.diff(name), y),
        new Multiply(x, y.diff(name))),
        new Multiply(y, y)));

const Negate = createOperation((x) => -x, 'negate',
        (name, x) => new Negate(x.diff(name)));

let Power = createOperation((x, y) => x ** y, 'pow',
        function(name, x, y) {
            return new Multiply(this, new Multiply(y, new Log(Const.E, x)).diff(name));
        });

const Log = createOperation((x, y) => Math.log(Math.abs(y)) / Math.log(Math.abs(x)), 'log',
                                                (name, x, y) => logDif(name, x, y));

const Sumexp = createOperation((...args) => args.reduce((last, cur) => (last + Math.exp(cur)), 0),
                                'sumexp', (name, ...args) => sumexpDif(name, args));

const Softmax = createOperation((...args) => Math.exp(args[0]) / args.reduce((last, cur) => (last + Math.exp(cur)), 0),
                                'softmax', (name, ...args) => softmaxDif(name, args));

const sumexpDif = function(name, args) {
    return args.reduce((last, cur) => new Add(last, new Multiply(new Sumexp(cur), cur.diff(name))), Const.ZERO);
};

const softmaxDif = function(name, args) {
    const f = new Sumexp(args[0]);
    const g = new Sumexp(...args);
    return new Divide(f, g).diff(name);
};

const logDif = function(name, x, y) {
    if (areEqualConsts(x, Const.E)) {
        return new Divide(y.diff(name), y);
    }
    return new Divide(new Log(Const.E, y), new Log(Const.E, x)).diff(name);
};

const areEqualConsts = function(x, y) {
    return (x.prototype === Const.prototype && y.prototype === Const.prototype && x.value === y.value);
};

const operations = {
    '+': Add,
    '-': Subtract,
    '*': Multiply,
    '/': Divide,
    'negate': Negate,
    'pow': Power,
    'log': Log,
    'sumexp': Sumexp,
    'softmax': Softmax
};

const argumentsAmount = {
    '-': 2,
    '+': 2,
    '*': 2,
    '/': 2,
    'negate': 1,
    'pow': 2,
    'log': 2
};

const variables = ['x', 'y', 'z'];

function ParsingError(message) {
    this.message = message;
}

ParsingError.prototype = Object.create(Error.prototype);
ParsingError.prototype.constructor = ParsingError;
ParsingError.prototype.name = ParsingError.name;


//                          HW 7

let parse = function(str) {
    let array = [];
    for (const token of str.split(' ').filter(string => (string.length > 0))) {
        if (variables.includes(token)) {
            array.push(new Variable(token));
        } else if (token in operations) {
            array.push(new operations[token](...array.splice(-argumentsAmount[token])));
        } else {
            array.push(new Const(parseFloat(token)));
        }
    }
    return array.pop();
};




//                          HW 8

const expressionParser = function(isPrefixMode) {
    let pos;
    let string;

    const getExpression = function() {
        skipWhitespace();
        let isClosingBracketExpected = false;
        if (currentChar() !== '(') {
            crash("Expected ( at pos " + (pos + 1) + " but found " + currentChar());
            isClosingBracketExpected = true;
            pos++;
        }
        pos++;
        let operation;
        let args;

        if (isPrefixMode) {
            operation = readOperation();
            args = readArgs();
        } else {
            args = readArgs();
            operation = readOperation();
        }

        checkArgumentsAmount(operation, isClosingBracketExpected, args);

        if (currentChar() !== ')') {
            crash("Expected ) at pos " + pos + " but found " + currentChar());
        }
        pos++;

        skipWhitespace();
        return makeOperation(operation, args);
    };

    const makeOperation = function(operation, args) {
        if (operation === undefined) {
            crash("No operation at pos " + (pos + 1));
        } else {
            return new operation(...args);
        }
    };

    const readOperation = function() {
        skipWhitespace();
        const token = getToken();
        const result = operations[token];
        if (result === undefined) {
            pos -= token.length;
        }
        return result;
    };

    const makeUnit = function(value) {
        if (isNumber(value)) {
            return new Const(parseFloat(value));
        } else if (isVariable(value)) {
            return new Variable(String(value));
        } else {
            crash("Incorrect argument value " + value + " at pos " + (pos + 1));
        }
    };

    const readArgs = function() {
        let result = [];
        while (true) {
            skipWhitespace();
            if (pos >= string.length) {
                break;
            } else if (currentChar() === '(') {
                result.push(getExpression());
            } else if (currentChar() === ')') {
                break;
            } else {
                const token = getToken();
                if (isNumber(token) || isVariable(token)) {
                    result.push(makeUnit(token));
                } else if (operations[token] !== undefined && !isPrefixMode) {
                    pos -= token.length;
                    break;
                } else {
                    crash("Incorrect token " + token + " at pos " + (pos + 1));
                }
            }
        }
        return result;
    };

    function checkArgumentsAmount(operation, isStrict, ...args) {
        skipWhitespace();

        if (operation === undefined) {
            crash("Operation is not found at pos " + (pos + 1));
        } else if (operation.prototype.argsAmount !== 0 && args[0].length !== operation.prototype.argsAmount) {
            crash("Illegal arguments amount of " + operation.prototype.symbol + ": " + args[0].length
                                + "(" + operation.prototype.argsAmount + " expected) at pos " + (pos + 1));
        }
    };




    const parse = function(str) {
        string = str;
        pos = 0;

        if (str.length === 0) {
            crash("Empty string");
        }

        let answer;
        skipWhitespace();
        if (currentChar() === '(') {
            answer = getExpression();
        } else {
            answer = makeUnit(getToken());
        }

        skipWhitespace();
        if (pos < string.length) {
            crash("Expected end of input but found symbol at pos: " + pos);
        }
        return answer;
    };

    const isNumber = function(val) {
        return !isNaN(val);
    };

    const isVariable = function(name) {
        return variables.includes(String(name));
    };

    const currentChar = () => string.charAt(pos);

    const getToken = function() {
        skipWhitespace();
        let result = "";
        while (pos < string.length && !isWhitespace(currentChar()) && currentChar() !== '(' && currentChar() !== ')') {
            result += currentChar();
            pos++;
        }

        return result;
    };

    const crash = function(message) {
        throw new ParsingError(message);
    };

    const isWhitespace = function(symbol) {
        return /\s/.test(symbol);
    };

    const skipWhitespace = function() {
        while (pos < string.length && isWhitespace(currentChar())) {
            pos++;
        }
    };

    return parse;
};

const parsePrefix = expressionParser(true);
const parsePostfix = expressionParser(false);