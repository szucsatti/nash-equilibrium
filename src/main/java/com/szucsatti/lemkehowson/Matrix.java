package com.szucsatti.lemkehowson;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.szucsatti.lemkehowson.Rational.*;


@Log
public class Matrix {

    private static final String START_LINE = "|  ";
    private static final String END_LINE = "  |";
    private static final String VALUE_DELIMITER = "  |  ";
    private Rational[][] matrix;
    private int rows;
    private int cols;

    public static final Matrix IDENTITY_2X2 = new Matrix(new Rational[][]{
            {ONE, ZERO},
            {ZERO, ONE}});

    public static final Matrix COLUMN_ONE = new Matrix(new Rational[][]{
            {ONE},
            {ONE}});

    public Matrix(final Rational[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
    }

    public Matrix(final long[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = new Rational[this.rows][this.cols];
        for(int row = 0; row < this.getRows(); row++){
            for(int col = 0; col < this.getCols(); col++){
                this.matrix[row][col] = valueOf(matrix[row][col]);
            }
        }
    }

    public Matrix(final Matrix other){
        this.rows = other.getRows();
        this.cols = other.getCols();
        this.matrix = new Rational[this.getRows()][this.getCols()];

        for(int rowIndex = 0; rowIndex < rows; rowIndex++){
            Rational[] row = other.matrix[rowIndex];
            System.arraycopy(row, 0, this.matrix[rowIndex], 0, row.length);
        }
    }

    public Matrix join(final Matrix other){
        assert this.getRows() == other.getRows();
        Rational[][] joined = new Rational[this.getRows()][this.getCols() + other.getCols()];

        for(int row = 0; row < other.getRows(); row++){
            Rational[] otherRow = other.matrix[row];
            Rational[] thisRow = this.matrix[row];
            System.arraycopy(thisRow, 0, joined[row], 0, thisRow.length);
            System.arraycopy(otherRow, 0, joined[row], thisRow.length, otherRow.length);
        }

        return new Matrix(joined);
    }

    public Matrix[] split(){
        int halfCol = this.getCols() / 2;

        Rational [][] firstMatrix = new Rational[this.getRows()][halfCol];
        Rational [][] secondMatrix = new Rational[this.getRows()][halfCol];

        for(int row = 0; row < rows; row++){
            Rational[] thisRow = this.matrix[row];
            System.arraycopy(thisRow, 0, firstMatrix[row], 0, halfCol);
            System.arraycopy(thisRow, halfCol, secondMatrix[row], 0, halfCol) ;
        }

        return new Matrix[]{new Matrix(firstMatrix), new Matrix(secondMatrix)};
    }

    public Rational get(int row, int column) {
        return matrix[row][column];
    }

    public Matrix normalize() {
        Rational normalizationConstant = getNormalizationConstant();
        Rational[][] normalized = new Rational[this.getRows()][this.getCols()];
        if(normalizationConstant.isGreaterOrEqualThan(ZERO)){
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){
                    normalized[row][col] = this.matrix[row][col].plus(normalizationConstant);
                }
            }
        }
        return new Matrix(normalized);
    }

    private Rational getNormalizationConstant(){
        Rational minimum = getMinimumValue();
        return minimum.isGreaterOrEqualThan(ZERO) ? ZERO : minimum.absValue().plus(ONE);
    }

    protected Rational getMinimumValue() {
        Rational minimum = matrix[0][0];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col].isLessThan(minimum)) {
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

    public Rational ratio(int uRow, int uCol, int lRow, int lCol){
        return matrix[uRow][uCol].divide(matrix[lRow][lCol]);
    }

    public Matrix multiplyRow(final int rowIndex, final Rational multiplyBy){
        Rational[][] multipliedMatrix = this.matrix;

        for(int col = 0; col < getCols(); col++){
            multipliedMatrix[rowIndex][col] = multipliedMatrix[rowIndex][col].times(multiplyBy);
        }

        return new Matrix(multipliedMatrix);
    }

    public Matrix subtract(final int rowIndex, final int fromIndex){
        Rational[][] subtractedMatrix = this.matrix;

        for(int col = 0; col < this.getCols(); col++){
            subtractedMatrix[fromIndex][col] = subtractedMatrix[fromIndex][col].minus(subtractedMatrix[rowIndex][col]);
        }
        return new Matrix(subtractedMatrix);
    }

    public List<Integer> getNoZeroCols(){
        List<Integer> nonZeroCols = new ArrayList<>();
        for(int col = 0; col < getCols(); col++){
            boolean nonZero = true;
            for(int row = 0; row < getRows(); row++){
                if(Rational.ZERO.equals(matrix[row][col])){
                    nonZero = false;
                    break;
                }
            }
            if(nonZero){
                nonZeroCols.add(col);
            }
        }
        return nonZeroCols;
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
