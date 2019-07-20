package com.artyom.mtx;

public class RealMatrix extends AbstractMatrix<Real> {
    public RealMatrix(int rows, int columns) {
        super(rows, columns);

        // set the default values
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                this.setElement(i, j, new Real());
            }
        }
    }

    public RealMatrix() {
        this(0, 0);
    }
}
