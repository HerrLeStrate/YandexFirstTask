package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquareView extends AppCompatImageView {

    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
