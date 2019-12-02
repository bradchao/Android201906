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
    private LinkedList<LinkedList<HashMap<String,Float>>> lines = new LinkedList<>();


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
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);

        for (LinkedList<HashMap<String,Float>> line: lines){
            for (int i=1; i<line.size(); i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"),p0.get("y"),
                        p1.get("x"),p1.get("y"), paint);
            }
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX(), ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x", ex); point.put("y",ey);

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            LinkedList<HashMap<String,Float>> line = new LinkedList<>();
            line.add(point);
            lines.add(line);
        }else {
            lines.getLast().add(point);
        }
        invalidate();   // repaint
        return true; //super.onTouchEvent(event);
    }

    public void clear(){
        lines.clear();
        invalidate();
    }


}
