package fr.melanoxy.mareu;

import android.graphics.Color;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class PageTransformer implements ViewPager2.PageTransformer{
    private static final float MIN_SCALE = 0.92f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position <= -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0.9f);
            view.setTranslationZ(-1f);

        } else if (position < 0) { // [-1,0]
            view.setX(-1f);

            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        }else if (position == 0) { // [0]
            view.setAlpha(1f);
            view.setTranslationZ(0f);

        }else if (position > 0) { // [0,1]
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1f);
            view.setTranslationZ(0f);
        }
    }
}