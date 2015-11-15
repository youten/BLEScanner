package youten.redo.unity.blescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BLEScanner mScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScanner = new BLEScanner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanner.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanner.stop();
    }
}
