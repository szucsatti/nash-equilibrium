package com.szucsatti.lemkehowson;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.szucsatti.lemkehowson.Matrix.COLUMN_ONE;
import static com.szucsatti.lemkehowson.Matrix.IDENTITY_2X2;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class Tableux {

    private List<Integer> labels = new ArrayList<>();

    private Matrix matrix;

    public static TableuxBuilder builder() {
        return new TableuxBuilder();
    }

    public Matrix getMatrix(){
        return this.matrix;
    }

    public List<Integer> getLabels() {
        return labels;
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
