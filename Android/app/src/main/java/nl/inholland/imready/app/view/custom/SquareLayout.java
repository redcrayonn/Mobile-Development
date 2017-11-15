package nl.inholland.imready.app.view.custom;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareLayout extends RelativeLayout {
    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // force the cell to be square
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int size = getMeasuredWidth();
            setMeasuredDimension(size, size);
        } else {
            int size = getMeasuredHeight();
            setMeasuredDimension(size, size);
        }
    }
}
