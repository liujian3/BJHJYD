package liujian.BJHJYD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * create
 */
public class BJHJYDActivity extends Activity {
    /** Called when the activity is first created. */
	private Button btnCommit;
	private EditText etCode;
	private Spinner spDate;
	private ArrayList<String> alDate;
	private ArrayAdapter<String> aaDate;
    private HttpParams httpParams;
    private HttpClient httpClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.main);
        
    	btnCommit = (Button)findViewById(R.id.btncommit);
    	etCode = (EditText)findViewById(R.id.etcode);
    	spDate = (Spinner)findViewById(R.id.spdate);
    	
    	alDate = new ArrayList<String>();
    	
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String url = "http://apply.bjhjyd.gov.cn/apply/norm/personQuery.html";
        getHttpClient();
        String res = doPost(url, params);
    	Pattern p = Pattern.compile(">(\\d{6})</option>");
    	Matcher m = p.matcher(res);
    	while(m.find())
    	{
    		MatchResult mr = m.toMatchResult();
	    	alDate.add(mr.group(1));
    	}   	
    	
    	aaDate = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,alDate);
    	aaDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spDate.setAdapter(aaDate);
    	/*
    	Editor sharedata = getSharedPreferences("data", 0).edit();
    	sharedata.putString("n","2");
    	sharedata.putString("1","2055100624399");
    	sharedata.putString("2","3777100436386");
    	sharedata.commit();*/
    	SharedPreferences sharedata = getSharedPreferences("data", 0);
    	String data = sharedata.getString("1", null);
    	Log.v("cola","data="+data);
    	
    	etCode.setText(data);
    	
    	btnCommit.setOnClickListener(new Button.OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			String msg="����";
    			if(spDate.getCount()==0)
    			{
    				msg = "��ѡ���·�";
    			}
    			else if(etCode.getText().length()==0)
    			{
    				msg = "����д�����";
    			}
    			else
    			{
	    	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	    	        params.add(new BasicNameValuePair("issueNumber", spDate.getSelectedItem().toString()));
	    	        params.add(new BasicNameValuePair("applyCode", etCode.getText().toString()));
	    	        String url = "http://apply.bjhjyd.gov.cn/apply/norm/personQuery.html";
	    	        getHttpClient();
	    	        String res = doPost(url, params);
	    	        msg = (res.contains(">"+etCode.getText().toString()+"<")?"����":"δ��");
    			}
    			AlertDialog ad = new AlertDialog.Builder(BJHJYDActivity.this).setTitle("���")	.
    					setMessage(msg).
    					setIcon(R.drawable.ic_launcher).setNegativeButton("����", null).
    					create();
    			ad.show();
    		}
    	});
    }
    public String doGet(String url, Map params) {
        /* ����HTTPGet���� */
        String paramStr = "";
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            paramStr += paramStr = "&" + key + "=" + val;
        }
        if (!paramStr.equals("")) {
            paramStr = paramStr.replaceFirst("&", "?");
            url += paramStr;
        }
        HttpGet httpRequest = new HttpGet(url);
        String strResult = "doGetError";
        try {
            /* �������󲢵ȴ���Ӧ */
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            /* ��״̬��Ϊ200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ���������� */
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else {
                strResult = "Error Response: "
                        + httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        Log.v("strResult", strResult);
        return strResult;
    }
    public String doPost(String url, List<NameValuePair> params) {
        /* ����HTTPPost���� */
        HttpPost httpRequest = new HttpPost(url);
        String strResult = "doPostError";
        try {
            /* ������������������� */
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            /* �������󲢵ȴ���Ӧ */
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            /* ��״̬��Ϊ200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ���������� */
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else {
                strResult = "Error Response: "
                        + httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        Log.v("strResult", strResult);
        return strResult;
    }
    public HttpClient getHttpClient() {
        // ���� HttpParams ���������� HTTP ��������һ���ֲ��Ǳ���ģ�
        this.httpParams = new BasicHttpParams();
        // �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С
        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        // �����ض���ȱʡΪ true
        HttpClientParams.setRedirecting(httpParams, true);
        // ���� user agent
        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);
        // ����һ�� HttpClient ʵ��
        // ע�� HttpClient httpClient = new HttpClient(); ��Commons HttpClient
        // �е��÷����� Android 1.5 ��������Ҫʹ�� Apache ��ȱʡʵ�� DefaultHttpClient
        httpClient = new DefaultHttpClient(httpParams);
        return httpClient;
    }

}