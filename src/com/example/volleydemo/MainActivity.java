package com.example.volleydemo;

import java.io.IOException;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleydemo.inter.ImageRequestCallBack;
import com.example.volleydemo.inter.JsonObjectRequestCallBack;
import com.example.volleydemo.inter.StringRequestCallBack;
import com.example.volleydemo.inter.XmlRequestCallBack;
import com.example.volleydemo.utils.BitmapCache;
import com.example.volleydemo.utils.LogUtil;
import com.example.volleydemo.utils.VolleyUtil;

public class MainActivity extends Activity implements OnClickListener {

	protected static final String TAG = "MainActivity";
	private Button btn_stringRequest;
	private TextView tv_result;
	private Button btn_jsonRequest;
	private Button btn_postRequest;
	private Button btn_imageRequest;
	private ImageView iv_result;
	private Button btn_imageLoader;
	private Button btn_network_iv;
	private NetworkImageView network_image_view;
	private Button btn_xml_request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	public void initView() {
		btn_stringRequest = (Button) findViewById(R.id.btn_stringRequest);
		tv_result = (TextView) findViewById(R.id.tv_result);
		btn_jsonRequest = (Button) findViewById(R.id.btn_jsonRequest);
		btn_postRequest = (Button) findViewById(R.id.btn_postRequest);
		btn_imageRequest = (Button) findViewById(R.id.btn_imageRequest);
		iv_result = (ImageView) findViewById(R.id.iv_result);
		btn_imageLoader = (Button) findViewById(R.id.btn_imageLoader);
		btn_network_iv = (Button) findViewById(R.id.btn_network_iv);
		network_image_view = (NetworkImageView) findViewById(R.id.network_image_view);
		btn_xml_request = (Button) findViewById(R.id.btn_xml_request);
		
		btn_stringRequest.setOnClickListener(this);
		btn_postRequest.setOnClickListener(this);
		btn_jsonRequest.setOnClickListener(this);
		btn_imageRequest.setOnClickListener(this);
		btn_imageLoader.setOnClickListener(this);
		btn_network_iv.setOnClickListener(this);
		btn_xml_request.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		/*
		 * stringRequest
		 */
		case R.id.btn_stringRequest:
			VolleyUtil.stringRequest("http://www.baidu.com",
					new StringRequestCallBack() {

						@Override
						public void getStringResponse(String response) {
							tv_result.setText(response);
						}

					});

			break;
			/**
			 * method post
			 */
		case R.id.btn_postRequest:
			VolleyUtil.postRequest("http://www.baidu.com");
			break;
			
			/*
			 * JsonRequest
			 */
		case R.id.btn_jsonRequest:
			VolleyUtil.jsonObjectRequest("http://www.weather.com.cn/adat/sk/101010100.html", new JsonObjectRequestCallBack() {
				
				@Override
				public void getJsonObject(JSONObject response) {
					tv_result.setText(response.toString());
					
				}
			});
			break;
			
		case R.id.btn_imageRequest:
			VolleyUtil.imageRequest("http://img.sc115.com/uploads1/sc/vector/150528/15052805127.jpg", new ImageRequestCallBack() {
				
				@Override
				public void getImageResponse(Bitmap response) {
					iv_result.setImageBitmap(response);
				}
			});
			
			break;
			
		case R.id.btn_imageLoader:
			VolleyUtil.imageLoader("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", iv_result,R.drawable.ic_empty,R.drawable.ic_error);
			break;
			
		case R.id.btn_network_iv:
			network_image_view.setDefaultImageResId(R.drawable.ic_empty);
			network_image_view.setErrorImageResId(R.drawable.ic_error);
			
			RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
			ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());
			
			network_image_view.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageLoader);
			break;
			
		case R.id.btn_xml_request:
			VolleyUtil.xmlPullRequest("http://flash.weather.com.cn/wmaps/xml/china.xml", new XmlRequestCallBack() {
				
				@Override
				public void getXmlResponse(XmlPullParser response) {
					
					try {
						int eventType = response.getEventType();
						while(eventType != XmlPullParser.END_DOCUMENT){
							switch (eventType) {
							case XmlPullParser.START_TAG:
								String nodeName = response.getName();
								if("city".equals(nodeName)){
									String pName = response.getAttributeValue(0);
									tv_result.setText(tv_result.getText()+", "+pName);
								}
								break;

							default:
								break;
							}
							
							eventType = response.next();
						}
						
					} catch (XmlPullParserException e) {
						LogUtil.e(TAG, e.getMessage(), e);
					} catch (IOException e) {
						LogUtil.e(TAG, e.getMessage(), e);
					}
					
				}
			});
			break;

		default:
			break;
		}

	}
}
