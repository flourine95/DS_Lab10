package Stack;

import java.util.Stack;

public class MyLIFO_App {
    // This method reserves the given array
    //[1,2,3]=> [3,2,1]
    public static <E> void reserve(E[] array) {
        Stack<E> stack = new Stack<>();
        for (E e : array) {
            stack.push(e);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = stack.pop();
        }
    }

    // This method checks the correctness of the given input
    // i.e. ()(())[]{(())} ==> true, ){[]}() ==> false

    public static boolean isCorrect(String input) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isOpenBracket(c)) {
                stack.push(addCloseBracket(c));
            } else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // This method evaluates the value of an expression
    // i.e. 51 + (54 *(3+2)) = 321
    public static int evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
                i--;
            }
            else if (tokens[i] == '(') ops.push(tokens[i]);
            else if (tokens[i] == ')') {
                while (ops.peek() != '(') values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.push(tokens[i]);
            }
        }
        while (!ops.empty()) values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        return values.pop();
    }
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }
    public static int applyOp(char op, int b, int a) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                yield a / b;
            }
            default -> 0;
        };
    }

    public static boolean isOpenBracket(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    public static char addCloseBracket(char c) {
        return switch (c) {
            case '(' -> ')';
            case '{' -> '}';
            case '[' -> ']';
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }
}

