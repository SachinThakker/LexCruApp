package com.lexcru.lexcruapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.lexcru.lexcruapp.R;

public class MySpannable extends ClickableSpan {

    private boolean isUnderline = true;
    private Context context;

    /**
     * Constructor
     */
    public MySpannable(Context context,boolean isUnderline) {
        this.context = context;
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(ContextCompat.getColor(context, R.color.black));
        Typeface plain = ResourcesCompat.getFont(context,R.font.gotham_medium);
        ds.setTypeface(plain);
    }

    @Override
    public void onClick(View widget) {


    }
}
