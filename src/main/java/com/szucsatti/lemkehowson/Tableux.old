package com.szucsatti.lemkehowson;

import java.util.ArrayList;
import java.util.List;

import static com.szucsatti.lemkehowson.Matrix.COLUMN_ONE;
import static com.szucsatti.lemkehowson.Matrix.IDENTITY_2X2;

public class Tableux {

    private List<Integer> labels = new ArrayList<>();

    private Matrix matrix;

    private int pivotRow = 0;

    private int nonPivotRow = 0;

    private static final int FIRST_ROW = 0;

    private static final int SECOND_ROW = 1;

    private Tableux(final List<Integer> labels, final Matrix matrix){
        this.matrix = matrix;
        this.labels = labels;
    }

    public static TableuxBuilder builder() {
        return new TableuxBuilder();
    }

    public Matrix getMatrix(){
        return this.matrix;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public boolean hasLabel(int label){
        return labels.contains(label);
    }

    public void findPivotAndNonPivotRows(final int label){
        assert labels.contains(label);

        Rational ratioRowOne = getRatioForRow(FIRST_ROW, label);
        Rational rationRowTwo = getRatioForRow(SECOND_ROW, label);

        if(ratioRowOne.isLessThan(rationRowTwo)){
            this.pivotRow = FIRST_ROW;
            this.nonPivotRow = SECOND_ROW;
        } else {
            this.pivotRow = SECOND_ROW;
            this.nonPivotRow = FIRST_ROW;
        }
    }

    /**
     * We do only row operations here until the <code>label</code> column will corresponds to a basic variable.
     *
     * @param label index of the column that has to become a basic variable (has a single non-zero variable)
     * @return the new {@link Tableux} after pivoting column <code>label</code>
     */
    public Tableux pivot(int label){
        findPivotAndNonPivotRows(label);

        Matrix pivotedMatrix = matrix.multiplyRow(nonPivotRow, matrix.ratio(pivotRow, label, nonPivotRow, label))
                .subtract(pivotRow, nonPivotRow);

        return new Tableux(pivotedMatrix.getNoZeroCols(), pivotedMatrix);
    }

    private Rational getRatioForRow(int rowIndex, int label){
        return matrix.ratio(rowIndex, matrix.getCols() - 1, rowIndex, label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tableux tableux = (Tableux) o;

        if (labels != null ? !labels.equals(tableux.labels) : tableux.labels != null) return false;
        return matrix != null ? matrix.equals(tableux.matrix) : tableux.matrix == null;
    }

    @Override
    public int hashCode() {
        int result = labels != null ? labels.hashCode() : 0;
        result = 31 * result + (matrix != null ? matrix.hashCode() : 0);
        return result;
    }

    static class TableuxBuilder{

        private List<Integer> labels = new ArrayList<>();

        private Matrix matrix;

        public TableuxBuilder matrix(Matrix matrix){
            if(this.matrix == null){
                this.matrix = matrix;
                for(int index = 0; index < matrix.getCols(); index++) {
                    labels.add(index);
                }
            } else {
                for(int index = this.matrix.getCols(); index < this.matrix.getCols() + matrix.getCols(); index++){
                    labels.add(index);
                }
                this.matrix = this.matrix.join(matrix);
            }
            return this;
        }

        public TableuxBuilder identity(){
            if(this.matrix == null){
                this.matrix = IDENTITY_2X2;
            } else {
                this.matrix = this.matrix.join(IDENTITY_2X2);
            }
            return this;
        }

        public Tableux build(){
            this.matrix = this.matrix.join(COLUMN_ONE);
            return new Tableux(labels, matrix);
        }
    }
}
