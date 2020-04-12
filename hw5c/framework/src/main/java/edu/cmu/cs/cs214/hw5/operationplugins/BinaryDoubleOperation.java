package edu.cmu.cs.cs214.hw5.operationplugins;

public interface BinaryDoubleOperation {
    /**
     * Perform a binary operation on two double
     * @param a the first operand
     * @param b the second operand
     * @return the computed result
     */
    double compute(double a, double b);
}
