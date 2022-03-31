package com.goldrush;

public class ImgDims {
    private float initHeight;
    private float initWidth;
    private float fullHeight;
    private float fullWidth;

    public ImgDims(float height, float width, float ration){
        initHeight = height;
        initWidth = width;
        fullHeight = ration * height;
        fullWidth = ration * width;
    }

    public float getIH(){
        return initHeight;
    }

    public float getIW(){
        return initWidth;
    }

    public float getFH(){
        return fullHeight;
    }

    public float getFW(){
        return fullWidth;
    }
}
