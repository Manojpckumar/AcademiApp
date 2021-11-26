package com.sysirohub.academicapp.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomSubHeading extends androidx.appcompat.widget.AppCompatTextView {


    public CustomSubHeading(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.ttf");
        this.setTypeface(face);
    }

    public CustomSubHeading(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.ttf");
        this.setTypeface(face);
    }

    public CustomSubHeading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}
