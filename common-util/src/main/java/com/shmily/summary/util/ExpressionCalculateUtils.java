package com.shmily.summary.util;

import com.google.common.base.Preconditions;
import com.shmily.summary.enumz.OperatorEnum;
import com.shmily.summary.exception.BaseBizException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 表达式求值工具类
 * 目前支持的运输符：+ - * / ( )
 * 目前支持的操作数：整数
 * create by kevin.ma on 2019/8/11 上午11:42
 **/
public class ExpressionCalculateUtils {

    private static final List<Character> NUMBER_CHARS
            = Arrays.asList(new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});

    /**
     * 中缀表达式求值 1+2*(3-1)+4 输出 9
     * @param expression
     * @return
     */
    public static final String middleExpressionCalculate(String expression) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(expression),
                "参数[expression]不可为空");

        // 操作数栈
        final Stack<String> operandStack = new Stack<>();
        // 操作符栈
        final Stack<String> operatorStack = new Stack<>();

        char[] chars = buildCharArray(expression);
        int size = chars.length;
        int i = 0;
        while (i < size) {
            char c = chars[i];
            if (!NUMBER_CHARS.contains(c)) {
                // 操作符
                operatorInStack(operandStack, operatorStack, String.valueOf(c));
                i++;
                continue;
            }
            // 操作数
            StringBuilder sb = new StringBuilder(8);
            sb.append(c);
            i += exchageOperand(chars, i+1, sb);
            // 操作数入栈
            operandStack.push(sb.toString());
        }

        String operandA = operandStack.pop();
        String operandB = operandStack.pop();
        return calculate(operandB, operandA, operatorStack.pop());
    }

    private static char[] buildCharArray(String expression){

        char[] chars = expression.toCharArray();
        StringBuilder sb = new StringBuilder(expression.length());
        for (int i = 0, size = chars.length; i < size; i++) {
            sb.append(chars[i]);
            if (NUMBER_CHARS.contains(chars[i]) && i < (size - 1)
                    && (OperatorEnum.LEFT_BRACKETS.getValue().equals(String.valueOf(chars[i+1]))
                    || OperatorEnum.RIGHT_BRACKETS.getValue().equals(String.valueOf(chars[i+1])))) {
                // 当前是数字 下一个是左括号/右括号
                sb.append(OperatorEnum.MULTI.getValue());
            }
        }
        return sb.toString().toCharArray();
    }

    private static int exchageOperand(final char[] chars, int beginIndex, final StringBuilder sb){

        int j = 1;
        if (beginIndex >= chars.length) {
            return j;
        }
        // 操作数
        while (NUMBER_CHARS.contains(chars[beginIndex])) {
            sb.append(chars[beginIndex]);
            beginIndex++;
            j++;
        }
        return j;
    }

    private static void operatorInStack(final Stack<String> operandStack,
                                        final Stack<String> operatorStack,
                                        final String operator){
        if (operatorStack.empty()){
            // 操作符栈空直接入栈
            operatorStack.push(operator);
            return;
        }
        // 和栈顶操作符比较优先级
        String topOperator = operatorStack.peek();

        if (isStackTopOpMorePriority(topOperator, operator)) {
            if (OperatorEnum.LEFT_BRACKETS.getValue().equals(topOperator)) {
                // 栈顶是 ( 直接出栈
                operatorStack.pop();
                return;
            }
            // 栈顶操作符优先级高 取出两个操作数计算后入栈
            String operandA = operandStack.pop();
            String operandB = operandStack.pop();
            topOperator = operatorStack.pop();
            String result = calculate(operandB, operandA, topOperator);
            if (!StringUtils.isEmpty(result)) {
                operandStack.push(result);
            }
            operatorInStack(operandStack, operatorStack, operator);
            return;
        }
        // 栈顶操作符优先级低 直接入栈
        operatorStack.push(operator);
    }

    /**
     *
     * @param operand 操作数
     * @param beOperand 被操作
     * @param operator 操作符
     * @return
     */
    private static String calculate(String operand, String beOperand, String operator){

        // 栈顶操作符 +
        if (OperatorEnum.PLUS.getValue().equals(operator)) {
             return String.valueOf(Double.valueOf(operand).doubleValue() + Double.valueOf(beOperand).byteValue());
        }
        // 栈顶操作符 -
        if (OperatorEnum.MINUS.getValue().equals(operator)) {
            return String.valueOf(Double.valueOf(operand).doubleValue() - Double.valueOf(beOperand).byteValue());

        }
        // 栈顶操作符 *
        if (OperatorEnum.MULTI.getValue().equals(operator)) {

            return String.valueOf(Double.valueOf(operand).doubleValue() * Double.valueOf(beOperand).byteValue());
        }
        // 栈顶操作符 /
        if (OperatorEnum.DIVISION.getValue().equals(operator)) {

            return String.valueOf(Double.valueOf(operand).doubleValue() / Double.valueOf(beOperand).byteValue());

        }
        // 栈顶操作符 (
        if (OperatorEnum.LEFT_BRACKETS.getValue().equals(operator)) {
            return null;
        }
        // 栈顶操作符 ) 不会存在
        if (OperatorEnum.RIGHT_BRACKETS.getValue().equals(operator)) {
            return null;
        }

        return null;
    }


    /**
     * 操作符优先级比较
     * @param stackTopOperator 栈丁操作符
     * @param nextOperator 待入栈操作符
     * @return true 当 stackTopOperator 优先级高于 nextOperator
     */
    private static boolean isStackTopOpMorePriority(String stackTopOperator, String nextOperator) {

        // 栈顶操作符 +
        if (OperatorEnum.PLUS.getValue().equals(stackTopOperator)) {
            return OperatorEnum.MINUS.getValue().equals(nextOperator)
                    || OperatorEnum.PLUS.getValue().equals(nextOperator)
                    || OperatorEnum.RIGHT_BRACKETS.getValue().equals(nextOperator);
        }
        // 栈顶操作符 -
        if (OperatorEnum.MINUS.getValue().equals(stackTopOperator)) {
            return OperatorEnum.MINUS.getValue().equals(nextOperator)
                    || OperatorEnum.PLUS.getValue().equals(nextOperator)
                    || OperatorEnum.RIGHT_BRACKETS.getValue().equals(nextOperator);
        }
        // 栈顶操作符 *
        if (OperatorEnum.MULTI.getValue().equals(stackTopOperator)) {

            return OperatorEnum.MINUS.getValue().equals(nextOperator)
                    || OperatorEnum.PLUS.getValue().equals(nextOperator)
                    || OperatorEnum.MULTI.getValue().equals(nextOperator)
                    || OperatorEnum.DIVISION.getValue().equals(nextOperator)
                    || OperatorEnum.RIGHT_BRACKETS.getValue().equals(nextOperator);
        }
        // 栈顶操作符 /
        if (OperatorEnum.DIVISION.getValue().equals(stackTopOperator)) {

            return OperatorEnum.MINUS.getValue().equals(nextOperator)
                    || OperatorEnum.PLUS.getValue().equals(nextOperator)
                    || OperatorEnum.MULTI.getValue().equals(nextOperator)
                    || OperatorEnum.DIVISION.getValue().equals(nextOperator)
                    || OperatorEnum.RIGHT_BRACKETS.getValue().equals(nextOperator);
        }
        // 栈顶操作符 (
        if (OperatorEnum.LEFT_BRACKETS.getValue().equals(stackTopOperator)) {
            return OperatorEnum.RIGHT_BRACKETS.getValue().equals(nextOperator);
        }
        // 栈顶操作符 ) 不会存在
        if (OperatorEnum.RIGHT_BRACKETS.getValue().equals(stackTopOperator)) {
            throw new BaseBizException("expression invalid");
        }

        throw new BaseBizException(String.join("", "stackTopOperator[ ", stackTopOperator, "] invalid"));
    }


    private ExpressionCalculateUtils(){
        throw new BaseBizException(String.join("", "not allow instance ", this.getClass().getName()));
    }

    public static void main(String[] args) {
//        System.out.println(ExpressionCalculateUtils.middleExpressionCalculate("100+2*(3-1)+4"));
//        System.out.println(ExpressionCalculateUtils.middleExpressionCalculate("1+2*(3-(1-1))+4"));
//        System.out.println(ExpressionCalculateUtils.middleExpressionCalculate("100+2*(3-(1-1))+4"));
        System.out.println(ExpressionCalculateUtils.middleExpressionCalculate("100+2(3-(1-1))+4"));
    }
}
