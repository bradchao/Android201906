package tw.org.iii.android201906;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.BLUE);
    }
}
