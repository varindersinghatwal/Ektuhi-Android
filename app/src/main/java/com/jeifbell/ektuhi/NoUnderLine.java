package com.jeifbell.ektuhi;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by varinder on 2/10/2015.
 */
public class NoUnderLine extends ClickableSpan {



    @Override
    public void onClick(View widget) {

    }

    public void updateDrawState(TextPaint ds){
        ds.setColor(Color.BLUE);
        ds.bgColor= Color.WHITE;
        ds.setUnderlineText(false);
    }
}
