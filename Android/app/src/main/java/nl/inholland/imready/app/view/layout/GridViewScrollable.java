package nl.inholland.imready.app.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewScrollable extends GridView {
    public GridViewScrollable(Context context) {
        super(context);
    }

    public GridViewScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewScrollable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GridViewScrollable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Makes the gridview as high as the combined size of it's children (including padding).
        // This to ensure it can be packaged in a scrollview
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
