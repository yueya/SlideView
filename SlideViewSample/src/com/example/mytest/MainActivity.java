package com.example.mytest;

import java.util.ArrayList;

import adapter.Listadapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listview;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		webView=(WebView)findViewById(R.id.webview);
//		initWebview();
//		webView.loadUrl("http://m.sui.taobao.org/demos/");
		listview=(ListView)findViewById(R.id.listview);
		ArrayList<String> list=new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		Listadapter adapter=new Listadapter();
		adapter.setData(list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(MainActivity.this, "点击", 0).show();
			}
		});
	}

	
	
	 private void initWebview(){
	        //启用支持javascript
	        WebSettings settings = webView.getSettings();
	        settings.setJavaScriptEnabled(true);
	        webView.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url)
	            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
	                Log.i("url",url);
	                view.loadUrl(url);
	                return true;
	            }
	        });
	        webView.setWebChromeClient(new WebChromeClient(){
	            @Override
	            public void onProgressChanged(WebView view, int newProgress) {
	                super.onProgressChanged(view, newProgress);
	            }
	        });
	        webView.setHorizontalScrollbarOverlay(false);//禁止横向滚动
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
