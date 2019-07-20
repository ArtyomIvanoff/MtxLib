package com.artyom.mtx;

abstract class MatrixElement<E> {
    private E value;

    public E getValue() {
        return value;
    }

    public void setValue(E value) { this.value = value; }

    public MatrixElement(E value) {
        this.value = value;
    }

    public MatrixElement() {}

    abstract MatrixElement<E> add(MatrixElement<E> anotherElement);

    abstract MatrixElement<E> subtract(MatrixElement<E> anotherElement);

    abstract MatrixElement<E> multiply(MatrixElement<E> anotherElement);

    abstract MatrixElement<E> divide(MatrixElement<E> anotherElement);
}
