package com.artyom.mtx;

import java.util.ArrayList;

abstract class AbstractMatrix<E extends MatrixElement> {
    private int rowCount;
    private int columnCount;

    private ArrayList<ArrayList<E>> matrixElements;

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public ArrayList<ArrayList<E>> getMatrixElements() {
        return matrixElements;
    }

    public AbstractMatrix<E> add(AbstractMatrix<E> anotherMatrix) {
        return null;
    }

    public AbstractMatrix<E> multiply(AbstractMatrix<E> anotherMatrix) {
        return null;
    }

    public AbstractMatrix<E> scalarMultiply(E scalar) {
        return null;
    }

    public E getTrace() {
        return null;
    }

    public E getDeterminant() {
        return null;
    }

    public AbstractMatrix<E> transpose() {
        return null;
    }

    public AbstractMatrix<E> getCofactorMatrix() {
        return null;
    }

    public AbstractMatrix<E> adjugate() {
        return this.getCofactorMatrix().transpose();
    }
}
