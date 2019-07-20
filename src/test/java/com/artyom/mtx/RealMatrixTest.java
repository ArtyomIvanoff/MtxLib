package com.artyom.mtx;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;


public class RealMatrixTest extends Assert {
    private static RealMatrix firstMatrix;
    private static RealMatrix secondMatrix;

    @BeforeClass
    public static void initTestMatrices() {
        final int rows = 3;
        final int columns = 3;

        firstMatrix = new RealMatrix(rows, columns);
        secondMatrix = new RealMatrix(rows, columns);
    }

    @Test
    public void testSetElement() {
        int rows = firstMatrix.getRowCount();

        for (int i = 0; i < rows; i++) {
            firstMatrix.setElement(i, i, new Real(1.0));
        }
    }
	
	@Test
    public void testAdd() {
        AbstractMatrix sumMatrix =  firstMatrix.add(secondMatrix);

        int rows = sumMatrix.getRowCount();
        int columns = sumMatrix.getColumnCount();

        assertEquals(rows, firstMatrix.getRowCount());
        assertEquals(columns, firstMatrix.getColumnCount());
        assertEquals(rows, secondMatrix.getRowCount());
        assertEquals(columns, secondMatrix.getColumnCount());

        for (int i = 0; i < rows; i++) {
            for(int j = 0; j <columns; j++) {
                Real sum = (Real) firstMatrix.getElement(i, j).add(secondMatrix.getElement(i, j));
                assertEquals(sumMatrix.getElement(i, j), sum); // ovveride equals for MatrixElement
            }
        }
    }

    @Test
    public void testScalarMultiply() {
        Real multiplier = new Real(3.0);

        for (int i = 0; i < firstMatrix.getRowCount(); i++) {
            firstMatrix.setElement(i, i, new Real(1.0));
        }

        AbstractMatrix scalarMultiplyMatrix =  firstMatrix.scalarMultiply(multiplier);

        int rows = scalarMultiplyMatrix.getRowCount();
        int columns = scalarMultiplyMatrix.getColumnCount();

        for (int i = 0; i < rows; i++) {
            for(int j = 0; j <columns; j++) {
                assertEquals(scalarMultiplyMatrix.getElement(i, j), firstMatrix.getElement(i,j).multiply(multiplier)); // ovveride equals for MatrixElement
            }
        }
    }
}
