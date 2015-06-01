package com.example.volleydemo.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleydemo.application.MyApplication;
import com.example.volleydemo.inter.ImageRequestCallBack;
import com.example.volleydemo.inter.JsonObjectRequestCallBack;
import com.example.volleydemo.inter.StringRequestCallBack;
import com.example.volleydemo.inter.XmlRequestCallBack;

public class VolleyUtil {

	protected static final String TAG = "VolleyUtil";
	public static RequestQueue mQueue = Volley.newRequestQueue(MyApplication
			.getContext());

	/**
	 * SringRequest ����url�����ص�string��stringRequestCallBack��getStringResponse�У�
	 * ʹ�ø÷�������дgetStringResponse������
	 * 
	 * @param url
	 *            the url you want to request
	 * @return
	 */
	public static void stringRequest(String url,
			final StringRequestCallBack stringRequestCallBack) {

		StringRequest stringRequest = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtil.i(TAG, response);
						stringRequestCallBack.getStringResponse(response);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						LogUtil.e(TAG, error.getMessage(), error);
					}

				});
		mQueue.add(stringRequest);
	}

	/**
	 * StringRequestû���ṩ����post�����ķ�������Ҫ����Request��getParams����������post����
	 * 
	 * @param url
	 */
	public static void postRequest(String url) {
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						LogUtil.i(TAG, response);

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						LogUtil.e(TAG, error.getMessage(), error);

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("params1", "value1");
				map.put("params2", "value2");
				return map;
			}
		};

		mQueue.add(stringRequest);
	}

	/**
	 * ����url����ȡjson���ݣ�����дJsonObjectRequestCallBack��jsonObjectCallBack�ص�����
	 * 
	 * @param url
	 * @param jsonObjectCallBack
	 */
	public static void jsonObjectRequest(String url,
			final JsonObjectRequestCallBack jsonObjectCallBack) {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						LogUtil.i(TAG, response.toString());
						jsonObjectCallBack.getJsonObject(response);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						LogUtil.e(TAG, error.getMessage(), error);

					}
				});

		mQueue.add(jsonObjectRequest);
	}
	
	/**
	 * ����url����ͼƬ������д�ص�����getImageResponse
	 * @param ur
	 * @param imageRequestCallBack
	 */
	public static void imageRequest(String url,final ImageRequestCallBack imageRequestCallBack){
		ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				imageRequestCallBack.getImageResponse(response);
				
			}
		}, 0, 0, Config.RGB_565, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.e(TAG, error.getMessage(), error);
			}
		});
		
		mQueue.add(imageRequest);
	}
	
	/**
	 * imageLoader
	 * @param url Ҫ���ʵ�ͼƬ��ַ
	 * @param imageView ��ʾͼƬ��ImageView�ؼ�
	 */
	public static void imageLoader(String url,ImageView imageView,int defaultImageResId, int errorImageResId){
		
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
		//��һ������ʱҪ��ʾͼƬ�Ŀؼ����ڶ��������Ǽ�������ʾ��ͼƬ�������������Ǽ���ʧ�ܵ�ͼƬ
		ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
		
		imageLoader.get(url, listener);
	}
	
	/**
	 * ����url����xml������д�ص�����xmlRequestCallBack()
	 * @param url
	 * @param xmlRequestCallBack
	 */
	public static void xmlPullRequest(String url,final XmlRequestCallBack xmlRequestCallBack){
		XMLRequest xmlRequest = new XMLRequest(url, new Response.Listener<XmlPullParser>() {

			@Override
			public void onResponse(XmlPullParser response) {
				xmlRequestCallBack.getXmlResponse(response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				LogUtil.e(TAG, error.getMessage(), error);
				
			}
		});
		mQueue.add(xmlRequest);
	}
	
	
	}
