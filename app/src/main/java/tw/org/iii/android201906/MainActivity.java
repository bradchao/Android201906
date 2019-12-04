package tw.org.iii.android201906;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.myView);
        myView.setDrawingCacheEnabled(true);
    }

    public void clear(View view) {
        myView.clear();
    }
    public void undo(View view) {
        myView.undo();
    }
    public void redo(View view) {
        myView.redo();
    }

    public void save(View view) {
        Bitmap bitmap = myView.getDrawingCache();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput("sign.png", MODE_PRIVATE));
            Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }
}
