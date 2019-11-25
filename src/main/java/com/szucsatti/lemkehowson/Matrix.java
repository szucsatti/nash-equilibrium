package com.szucsatti.lemkehowson;

public class Matrix {

    private static final String START_LINE = "| ";
    private static final String END_LINE = " |";
    private static final String VALUE_DELIMITER = " | ";
    private double[][] matrix;
    private int rows;
    private int cols;

    public Matrix(final double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
    }

    public double get(int row, int column) {
        return matrix[row][column];
    }

    public void normalize() {

    }

    protected double minimumValue() {
        double minimum = matrix[0][0];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] < minimum) {
                    minimum = matrix[row][col];
                }
            }
        }
        return minimum;
    }

    public void display() {
        for (int row = 0; row < rows; row++) {
            System.out.print(START_LINE);
            for (int col = 0; col < cols; col++) {
                System.out.print(matrix[row][col]);
                if (col != cols - 1) {
                    System.out.print(VALUE_DELIMITER);
                }
            }
            System.out.print(END_LINE);
            if (row != rows - 1) {
                System.out.println();
            }
        }
        System.out.println();
    }

}
