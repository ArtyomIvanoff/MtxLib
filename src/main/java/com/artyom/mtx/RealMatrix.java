package com.artyom.mtx;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class AbstractMatrix<E extends MatrixElement> {
    private int rowCount;
    private int columnCount;

    private ArrayList<ArrayList<E>> matrixElements;

    public AbstractMatrix(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;

        matrixElements = new ArrayList<>(rows);
        for (int i = 0; i < this.rowCount; i++) {
            // need to set rows and columns by hand
            matrixElements.add(new ArrayList<>(columns));
            for (int j = 0; j < this.columnCount; j++)
                matrixElements.get(i).add(null);
        }
    }

    public AbstractMatrix() {
        this(0, 0);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    /*public ArrayList<ArrayList<E>> getMatrixElements() {
        return matrixElements;
    }*/

    public void setElement(int row, int column, E newValue) {
        if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) {
            matrixElements.get(row).set(column, newValue);
        } else {
            throw new RuntimeException("Incorrect values of indices!");
        }
    }

    public E getElement(int row, int column) {
        if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) {
            return matrixElements.get(row).get(column);
        } else {
            throw new RuntimeException("Incorrect values of indices!");
        }
    }

    public AbstractMatrix<E> add(AbstractMatrix<E> anotherMatrix) {
        if (rowCount != anotherMatrix.rowCount || columnCount != anotherMatrix.columnCount)
            throw new RuntimeException("Sizes of two matrices must be same!");
        else {
            AbstractMatrix<E> matrixSum = new AbstractMatrix<>(rowCount, columnCount);

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    matrixSum.setElement(i, j, (E) this.getElement(i, j).add(anotherMatrix.getElement(i, j)));
                }
            }

            return matrixSum;
        }
    }

    public AbstractMatrix<E> multiply(AbstractMatrix<E> anotherMatrix) {
        if (columnCount != anotherMatrix.rowCount) {
            throw new RuntimeException("Number of columns of the 1st matrix must be equal to number of rows of the 2nd matrix!");
        } else {
            AbstractMatrix<E> prodMatrix = new AbstractMatrix<>(rowCount, anotherMatrix.columnCount);
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < anotherMatrix.columnCount; j++) {
                    E prodOfElements = (E) this.getElement(i, 0).multiply(anotherMatrix.getElement(0, j));
                    prodMatrix.setElement(i, j, prodOfElements);
                    for (int k = 1; k < columnCount; k++) {
                        prodOfElements = (E) this.getElement(i, k).multiply(anotherMatrix.getElement(k, j));
                        prodMatrix.setElement(i, j, (E)prodOfElements.add(prodOfElements));
                    }
                }
            }

            return prodMatrix;
        }
    }

    public AbstractMatrix<E> scalarMultiply(E scalar) {
        AbstractMatrix<E> matrixScalMult = new AbstractMatrix<>(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++)
                matrixScalMult.setElement(i, j, (E) this.getElement(i, j).multiply(scalar));

        return matrixScalMult;
    }

    public E getTrace() {
        if (rowCount != columnCount)
            throw new RuntimeException("Trace is applied only for square matrices!");
        else {
            MatrixElement sumOfDiagonal = this.getElement(0, 0);
            for (int i = 1; i < rowCount; i++)
                sumOfDiagonal = sumOfDiagonal.add(this.getElement(i, i));

            return (E) sumOfDiagonal;
        }
    }

    public E getDeterminant() {
        return null;
    }

    public AbstractMatrix<E> transpose() {
        int rowsTransp = this.columnCount;
        int columnsTransp = this.rowCount;


        AbstractMatrix<E> transpMatrix = new AbstractMatrix<>(rowsTransp, columnsTransp);
        for (int i = 0; i < rowsTransp; i++)
            for (int j = 0; j < columnsTransp; j++)
                transpMatrix.setElement(i, j, this.getElement(j, i));

        return transpMatrix;
    }

    public AbstractMatrix<E> getCofactorMatrix() {
        return null;
    }

    public AbstractMatrix<E> adjugate() {
        return this.getCofactorMatrix().transpose();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(2 * rowCount * columnCount);

        for (ArrayList<E> row : matrixElements) {
            for (E element : row) {
                sb.append(element);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
