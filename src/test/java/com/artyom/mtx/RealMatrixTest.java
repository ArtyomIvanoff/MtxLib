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

    public void testAdd() {
        AbstractMatrix sumMatrix =  firstMatrix.add(secondMatrix);

        int rows = sumMatrix.getRowCount();
        int columns = sumMatrix.getColumnCount();

        assertEquals(rows, firstMatrix.getRowCount());
        assertEquals(columns, firstMatrix.getColumnCount());
        assertEquals(rows, secondMatrix.getRowCount());
        assertEquals(columns, secondMatrix.getColumnCount());

        for (int i = 0; i < rows; i++) {
            Real sum = (Real) firstMatrix.getElement(i, i).add(secondMatrix.getElement(i, i));
            assertEquals(sumMatrix.getElement(i, i), sum);
        }
    }
}
