package tw.org.iii.android201906;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines = new LinkedList<>();
    private LinkedList<LinkedList<HashMap<String,Float>>> recycler = new LinkedList<>();
    private Bitmap bg, ball;
    private MainActivity activity;
    private Resources res;
    private int viewW, viewH;
    private boolean isInit;
    private Paint paint;
    private float ballW,ballH;
    private LinkedList<BallTask> balls;

    private Timer timer;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activity = (MainActivity)context;
        res = activity.getResources();
        setBackgroundResource(R.drawable.bg);
        timer = new Timer();
        balls = new LinkedList<>();
    }

    private void init(){
        isInit = !isInit;
        viewW = getWidth(); viewH = getHeight();
        ball = BitmapFactory.decodeResource(res, R.drawable.ball);

        ballW = viewW / 12f; ballH = ballW;
        Matrix matrix = new Matrix();
        matrix.postScale(ballW/ball.getWidth(), ballH/ball.getHeight());
        ball = Bitmap.createBitmap(ball, 0, 0, ball.getWidth(), ball.getHeight(), matrix, false);

        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        timer.schedule(new ResfreshTask(), 0, 32);
    }

    private class ResfreshTask extends TimerTask {
        @Override
        public void run() {
            postInvalidate();
        }
    }

    private class BallTask extends TimerTask {
        private float ballX, ballY, dx, dy;
        BallTask(float ballX, float ballY){
            this.ballX = ballX; this.ballY = ballY;
            dx = ballW / 12f; dy = ballH / 12f;
        }
        float getBallX(){return ballX;}
        float getBallY(){return ballY;}
        @Override
        public void run() {
            if (ballX < 0 || ballX + ballW > viewW){
                dx *= -1;
            }
            if (ballY < 0 || ballY + ballH > viewH){
                dy *= -1;
            }

            ballX += dx; ballY += dy;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) init();

        for (BallTask myball : balls) {
            canvas.drawBitmap(ball, myball.getBallX(), myball.getBallY(), null);
        }

//        for (LinkedList<HashMap<String,Float>> line: lines){
//            for (int i=1; i<line.size(); i++){
//                HashMap<String,Float> p0 = line.get(i-1);
//                HashMap<String,Float> p1 = line.get(i);
//                canvas.drawLine(p0.get("x"),p0.get("y"),
//                        p1.get("x"),p1.get("y"), paint);
//            }
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        float ex = event.getX(), ey = event.getY();
//        HashMap<String,Float> point = new HashMap<>();
//        point.put("x", ex); point.put("y",ey);
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN){
//            recycler.clear();
//            LinkedList<HashMap<String,Float>> line = new LinkedList<>();
//            line.add(point);
//            lines.add(line);
//        }else {
//            lines.getLast().add(point);
//        }
//        invalidate();   // repaint
//        return true; //super.onTouchEvent(event);

        float ex = event.getX() - ballW/2f, ey = event.getY() - ballH/2f;
        BallTask ball = new BallTask(ex,ey);
        balls.add(ball);
        timer.schedule(ball, 0, 20);

        return false;
    }

    public void clear(){
        lines.clear();
        invalidate();
    }

    public void undo(){
        if (lines.size()>0) {
            recycler.add(lines.removeLast());
            invalidate();
        }
    }

    public void redo(){
        if (recycler.size()>0){
            lines.add(recycler.removeLast());
            invalidate();
        }
    }


}
