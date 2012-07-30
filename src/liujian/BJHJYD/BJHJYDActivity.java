package liujian.BJHJYD;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * create
 */
public class BJHJYDActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
    }
}