package com.artyom.mtx;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class RealMatrixTest extends Assert {
    private static RealMatrix firstMatrix;
    private static RealMatrix secondMatrix;
    private static RealMatrix eqMatrix;
    private static final double eps = 1e-5;

    @BeforeClass
    public static void initTestMatrices() {
        final int rows = 3;
        final int columns = 3;

        firstMatrix = new RealMatrix(rows, columns);
        secondMatrix = new RealMatrix(rows, columns);

        // set to the equality matrix
        eqMatrix = RealMatrix.getEqualityMatrix(rows);
    }

    @Test
    public void testSetElement() {
        int rows = firstMatrix.getRowCount();

        for (int i = 0; i < rows; i++) {
            firstMatrix.setElement(i, i, 5.0);
        }
    }

    @Test
    public void testAdd() {
        RealMatrix sumMatrix = firstMatrix.add(secondMatrix);

        int rows = sumMatrix.getRowCount();
        int columns = sumMatrix.getColumnCount();

        assertEquals(rows, firstMatrix.getRowCount());
        assertEquals(columns, firstMatrix.getColumnCount());
        assertEquals(rows, secondMatrix.getRowCount());
        assertEquals(columns, secondMatrix.getColumnCount());

        double eps = 1e-5;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double sum = firstMatrix.getElement(i, j) + (secondMatrix.getElement(i, j));
                assertEquals(sum, sumMatrix.getElement(i, j), eps); // ovveride equals for MatrixElement
            }
        }
    }

    @Test
    public void testScalarMultiply() {
        double multiplier = 3.0;

        RealMatrix scalarMultiplyMatrix = firstMatrix.scalarMultiply(multiplier);

        int rows = scalarMultiplyMatrix.getRowCount();
        int columns = scalarMultiplyMatrix.getColumnCount();

        double eps = 1e-5;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                assertEquals(firstMatrix.getElement(i, j) * multiplier, scalarMultiplyMatrix.getElement(i, j), eps); // ovveride equals for MatrixElement
            }
        }
    }

    @Test
    public void testProduct() {
        RealMatrix prodMatrix1 = eqMatrix.multiply(secondMatrix);

        int rows = prodMatrix1.getRowCount();
        int columns = prodMatrix1.getColumnCount();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                assertEquals(secondMatrix.getElement(i, j), prodMatrix1.getElement(i, j), eps); // ovveride equals for MatrixElement
            }
        }
    }

    @Test
    public void testDet() {
        double detEqMatrix = eqMatrix.getDeterminant();
        assertEquals(1.0, detEqMatrix, eps);

        int rows = firstMatrix.getRowCount();
        int columns = firstMatrix.getColumnCount();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                firstMatrix.setElement(i, j, (i + 1) * j + 1);
            }
        }

        double detZero = firstMatrix.getDeterminant();
        assertEquals(0.0, detZero, eps);
    }

    @Test
    public void testInverseMatrix() {
        int rows = secondMatrix.getRowCount();
        int columns = secondMatrix.getColumnCount();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == j)
                    secondMatrix.setElement(i, j, i * j);
                else
                    secondMatrix.setElement(i, j, 1);
            }
        }

        try {
            RealMatrix inverseMatrix = secondMatrix.getInverseMatrix();
            // the product of matrix and its inverse is equality matrix
            RealMatrix prod = secondMatrix.multiply(inverseMatrix);
            RealMatrix prod2 = inverseMatrix.multiply(secondMatrix);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    assertEquals(eqMatrix.getElement(i, j), prod.getElement(i, j), eps);
                    assertEquals(eqMatrix.getElement(i, j), prod2.getElement(i, j), eps);
                }
            }
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }

    }
}
