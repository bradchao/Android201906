package tw.org.iii.android201906;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    private LinkedList<HashMap<String,Float>> line = new LinkedList<>();


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.BLUE);
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("brad", "click");
//            }
//        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10);
        canvas.drawLine(0,0, 200,200, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX(), ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x", ex); point.put("y",ey);
        line.add(point);
        return true; //super.onTouchEvent(event);
    }
}
