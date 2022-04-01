package com.goldrush;

public class ImgDims {
    private double initHeight;
    private double initWidth;
    private double fullHeight;
    private double fullWidth;

    public ImgDims(double height, double width, double ration){
        initHeight = height;
        initWidth = width;
        fullHeight = ration * height;
        fullWidth = ration * width;
    }

    public double getIH() {return initHeight;}
    public double getIW() {return initWidth;}
    public double getFH() {return fullHeight;}
    public double getFW() {return fullWidth;}
}
