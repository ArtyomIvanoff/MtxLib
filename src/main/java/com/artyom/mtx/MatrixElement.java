package com.artyom.mtx;

public interface MatrixElement {
    MatrixElement add(MatrixElement anotherElement);

    MatrixElement subtract(MatrixElement anotherElement);

    MatrixElement multiply(MatrixElement anotherElement);

    MatrixElement divide(MatrixElement anotherElement);
}
