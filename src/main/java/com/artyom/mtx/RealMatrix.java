package com.artyom.mtx;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

public class RealMatrix {
    private int rowCount;
    private int columnCount;

    private double[][] matrixElements;

    public RealMatrix(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;

        matrixElements = new double[rows][columns];
    }

    public RealMatrix() {
        this(1, 1);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public boolean isSquareMatrix() {
        return rowCount == columnCount;
    }

    public void setElement(int row, int column, double newValue) {
        if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) {
            matrixElements[row][column] = newValue;
        } else {
            throw new RuntimeException("Incorrect values of indices!");
        }
    }

    public double getElement(int row, int column) {
        if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) {
            return matrixElements[row][column];
        } else {
            throw new RuntimeException("Incorrect values of indices!");
        }
    }

    public RealMatrix add(RealMatrix anotherMatrix) {
        if (rowCount != anotherMatrix.rowCount || columnCount != anotherMatrix.columnCount)
            throw new RuntimeException("Sizes of two matrices must be same!");
        else {
            RealMatrix matrixSum = new RealMatrix(rowCount, columnCount);

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    matrixSum.matrixElements[i][j] = this.matrixElements[i][j] + anotherMatrix.matrixElements[i][j];
                }
            }

            return matrixSum;
        }
    }

    public RealMatrix multiply(RealMatrix anotherMatrix) {
        if (columnCount != anotherMatrix.rowCount) {
            throw new RuntimeException("Number of columns of the 1st matrix must be equal to number of rows of the 2nd matrix!");
        } else {
            RealMatrix prodMatrix = new RealMatrix(rowCount, anotherMatrix.columnCount);
            double combination;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < anotherMatrix.columnCount; j++) {
                    combination = 0.0;
                    for (int k = 0; k < columnCount; k++) {
                        combination += this.matrixElements[i][k] * anotherMatrix.matrixElements[k][j];
                    }
                    prodMatrix.matrixElements[i][j] = combination;
                }
            }

            return prodMatrix;
        }
    }

    public RealMatrix scalarMultiply(double scalar) {
        RealMatrix matrixScalMult = new RealMatrix(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
				if(Math.abs(this.matrixElements[i][j]) < 1e-6 || Math.abs(scalar) < 1e-6) {
					matrixScalMult.matrixElements[i][j] = 0.0;
				} else {
					matrixScalMult.matrixElements[i][j] = this.matrixElements[i][j] * scalar;
				}
			}
		} 

        return matrixScalMult;
    }

    public double getTrace() {
        if (!this.isSquareMatrix())
            throw new RuntimeException("Trace is applied only for square matrices!");
        else {
            double sumOfDiagonal = 0.0;
            for (int i = 0; i < rowCount; i++)
                sumOfDiagonal += this.matrixElements[i][i];

            return sumOfDiagonal;
        }
    }

    public double getDeterminant() {
        return getDeterminantOf(this.matrixElements);
    }

    public RealMatrix transpose() {
        int rowsTransp = this.columnCount;
        int columnsTransp = this.rowCount;


        RealMatrix transpMatrix = new RealMatrix(rowsTransp, columnsTransp);
        for (int i = 0; i < rowsTransp; i++)
            for (int j = 0; j < columnsTransp; j++)
                transpMatrix.matrixElements[i][j] = matrixElements[j][i];

        return transpMatrix;
    }

    public RealMatrix getCofactorMatrix() {
        if (!this.isSquareMatrix()) {
            throw new RuntimeException("Cofactor is applied only for the square matrices!");
        } else {
            RealMatrix cofactorMatrix = new RealMatrix(rowCount, columnCount);
            for (int i = 0; i < rowCount; i++)
                for (int j = 0; j < columnCount; j++)
                    cofactorMatrix.setElement(i, j, getCofactorOf(this.matrixElements, i, j));

            return cofactorMatrix;
        }
    }

    public RealMatrix adjugate() {
        return this.getCofactorMatrix().transpose();
    }

    public RealMatrix getInverseMatrix() throws UnsupportedOperationException {
        if (!this.isSquareMatrix()) {
            throw new RuntimeException("Cofactor is applied only for the square matrices!");
        } else {
            double det = this.getDeterminant();
            if (Math.abs(det) < 1e-6) {
                throw new UnsupportedOperationException("The inverse matrix exists only for square matrix with non-zero determinant");
            } else {
                return this.adjugate().scalarMultiply(1.0 / det);
            }
        }
    }

    public static RealMatrix getEqualityMatrix(int rows) {
        RealMatrix eqMatrix = new RealMatrix(rows, rows);
        for (int i = 0; i < rows; i++) {
            eqMatrix.setElement(i, i, 1.0);
        }

        return eqMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(2 * rowCount * columnCount);

        for (double[] row : matrixElements) {
            for (double element : row) {
                sb.append(element);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // rowMinor and columnMinor starts with 0
    private static double[][] getMatrixOfMinor(double[][] matrix, int rowMinor, int columnMinor) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        int i1 = 0;
        int j1 = 0;

        double[][] matrixMinor = new double[rows - 1][columns - 1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i != rowMinor && j != columnMinor) {
                    matrixMinor[i1][j1] = matrix[i][j];
                    j1++;

                    // then we should fill next row of the matrixMinor
                    if (j1 == columns - 1) {
                        j1 = 0;
                        i1++;
                    }
                }
            }
        }

        return matrixMinor;
    }

    private static double getDeterminantOf(double[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        if (rows != columns) {
            throw new UnsupportedOperationException("Determinant is applied only for square matrices!");
        } else {
            switch (rows) {
                case 1:
                    return matrix[0][0];
                case 2:
                    return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
                default:
                    final int firstRow = 0;
                    final double eps = 1e-5;
                    double det = 0.0;
                    double curElem;
                    double[][] matrixOfMinor;
                    // -1 in power of sum of i-th row and j-th column (if starting with 1)
                    int koeff = 1;

                    for (int i = 0; i < rows; i++) {
                        curElem = matrix[firstRow][i];
                        // if it isn't equal to zero, then next addend in det isn't zero
                        if (Math.abs(curElem) >= eps) {
                            matrixOfMinor = getMatrixOfMinor(matrix, firstRow, i);
                            det += koeff * curElem * getDeterminantOf(matrixOfMinor);
                        }
                        // change sign every time
                        koeff *= -1;
                    }

                    return det;
            }
        }
    }

    private static double getCofactorOf(double[][] matrix, int row, int column) {
        double minor = getDeterminantOf(getMatrixOfMinor(matrix, row, column));
		
        return Math.abs(minor) < 1e-6 ? 0.0 : Math.pow(-1.0, (row + column + 2)) * minor;
    }
}
