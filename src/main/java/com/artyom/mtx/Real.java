package com.artyom.mtx;

class Real extends MatrixElement<Double> {

    public Real(Double value) {
        super(value);
    }

    public Real() {
        this(0.0);
    }

    @Override
    public MatrixElement add(MatrixElement<Double> anotherElement) {
       return new Real(this.getValue() + anotherElement.getValue());
    }

    @Override
    public MatrixElement subtract(MatrixElement<Double> anotherElement) {
        return new Real(this.getValue() - anotherElement.getValue());
    }

    @Override
    public MatrixElement multiply(MatrixElement<Double> anotherElement) {
        return new Real(this.getValue() * anotherElement.getValue());
    }

    @Override
    public MatrixElement divide(MatrixElement<Double> anotherElement) {
        return new Real(this.getValue() / anotherElement.getValue());
    }

    @Override
    public String toString(){
        return String.valueOf(this.getValue());
    }
}
