package liujian.BJHJYD;

import android.app.Activity;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * create
 */
public class BJHJYDActivity extends Activity {
	TextView textview1;
	Button b;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textview1 = (TextView)findViewById(R.id.textView1);  
        b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		SmsManager sm = SmsManager.getDefault();
        		sm.sendTextMessage("13810249488", null, "asdf", null, null);
        	}
        });
    }
}