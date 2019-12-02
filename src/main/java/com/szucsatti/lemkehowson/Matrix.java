package com.szucsatti.lemkehowson;

import lombok.extern.java.Log;

import java.util.Arrays;

@Log
public class Matrix {

    private static final String START_LINE = "|  ";
    private static final String END_LINE = "  |";
    private static final String VALUE_DELIMITER = "  |  ";
    private double[][] matrix;
    private int rows;
    private int cols;

    public static final Matrix IDENTITY_2X2 = new Matrix(new double[][]{
            {1, 0},
            {0, 1}});

    public static final Matrix COLUMN_ONE = new Matrix(new double[][]{
            {1},
            {1}});

    public Matrix(final double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
    }

    public Matrix(final Matrix other){
        this.rows = other.getRows();
        this.cols = other.getCols();
        this.matrix = new double[this.getRows()][this.getCols()];

        for(int rowIndex = 0; rowIndex < rows; rowIndex++){
            double[] row = other.matrix[rowIndex];
            System.arraycopy(row, 0, this.matrix[rowIndex], 0, row.length);
        }
    }

    public Matrix join(final Matrix other){
        assert this.getRows() == other.getRows();
        double[][] joined = new double[this.getRows()][this.getCols() + other.getCols()];

        for(int row = 0; row < other.getRows(); row++){
            double[] otherRow = other.matrix[row];
            double[] thisRow = this.matrix[row];
            System.arraycopy(thisRow, 0, joined[row], 0, thisRow.length);
            System.arraycopy(otherRow, 0, joined[row], thisRow.length, otherRow.length);
        }

        return new Matrix(joined);
    }

    public Matrix[] split(){
        int halfCol = this.getCols() / 2;

        double [][] firstMatrix = new double[this.getRows()][halfCol];
        double [][] secondMatrix = new double[this.getRows()][halfCol];

        for(int row = 0; row < rows; row++){
            double[] thisRow = this.matrix[row];
            System.arraycopy(thisRow, 0, firstMatrix[row], 0, halfCol);
            System.arraycopy(thisRow, halfCol, secondMatrix[row], 0, halfCol) ;
        }

        return new Matrix[]{new Matrix(firstMatrix), new Matrix(secondMatrix)};
    }

    public double get(int row, int column) {
        return matrix[row][column];
    }

    public Matrix normalize() {
        double normalizationConstant = getNormalizationConstant();
        double[][] normalized = new double[this.getRows()][this.getCols()];
        if(normalizationConstant > 0D){
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){
                    normalized[row][col] = this.matrix[row][col] + normalizationConstant;
                }
            }
        }
        return new Matrix(normalized);
    }

    private double getNormalizationConstant(){
        double minimum = getMinimumValue();
        return minimum >= 0D ? 0D : Math.abs(minimum) + 1;
    }


    protected double getMinimumValue() {
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

    public void print() {
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

    public int getCols(){
        return cols;
    }

    public int getRows(){
        return rows;
    }

    public double ratio(int uRow, int uCol, int lRow, int lCol){
        return matrix[uRow][uCol] / matrix[lRow][lCol];
    }

    public Matrix multiplyRow(final int rowIndex, final double multiplyBy){
        double[][] multipliedMatrix = new double[][]{};
        multipliedMatrix = this.matrix;
        for(int col = 0; col < getCols(); col++){
            multipliedMatrix[rowIndex][col] *= multiplyBy;
        }

        return new Matrix(multipliedMatrix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix1 = (Matrix) o;

        if (rows != matrix1.rows) return false;
        if (cols != matrix1.cols) return false;
        return Arrays.deepEquals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(matrix);
        result = 31 * result + rows;
        result = 31 * result + cols;
        return result;
    }
}
