package com.example.calculator;

public class ModelBMI {

    private int result;
    private int weight;
    private int height;

    //constructors

    public ModelBMI(int result, int weight, int height) {
        this.result = result;
        this.weight = weight;
        this.height = height;
    }

    //toString

    @Override
    public String toString() {
        return "ModelBMI{" +
                "result=" + result +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }


    //getters and setters

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
