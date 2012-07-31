package liujian.BJHJYD;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * create
 */
public class BJHJYDActivity extends Activity {
    /** Called when the activity is first created. */
	Button btnCommit;
	EditText etCode;
	Spinner spDate;
	ArrayList<String> alDate;
	ArrayAdapter<String> aaDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	btnCommit = (Button)findViewById(R.id.btncommit);
    	etCode = (EditText)findViewById(R.id.etcode);
    	spDate = (Spinner)findViewById(R.id.spdate);
    	
    	alDate = new ArrayList<String>();
    	
    	alDate.add("201207");
    	alDate.add("201206");
    	alDate.add("201205");
    	alDate.add("201204");
    	alDate.add("201203");
    	alDate.add("201202");
    	alDate.add("201201");
    	alDate.add("201112");
    	alDate.add("201111");
    	alDate.add("201110");
    	alDate.add("201109");
    	alDate.add("201108");
    	alDate.add("201107");
    	alDate.add("201106");
    	alDate.add("201105");
    	alDate.add("201104");
    	alDate.add("201103");
    	alDate.add("201102");    	
    	alDate.add("201101");    	
    	
    	aaDate = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,alDate);
    	aaDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spDate.setAdapter(aaDate);
    	
    	btnCommit.setOnClickListener(new Button.OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			AlertDialog ad = new AlertDialog.Builder(BJHJYDActivity.this).setTitle("结果")	.
    					setMessage("恭喜您！没中……").
    					setIcon(R.drawable.ic_launcher).setNegativeButton("返回", null).
    					create();
    			ad.show();
    		}
    	});
    }
}