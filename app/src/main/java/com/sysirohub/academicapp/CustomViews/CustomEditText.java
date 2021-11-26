package com.sysirohub.academicapp.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    public CustomEditText(@NonNull Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.ttf");
        this.setTypeface(face);
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.ttf");
        this.setTypeface(face);
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Montserrat-Light.ttf");
        this.setTypeface(face);
    }
}
