package pibr.bookcorner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import pibr.bookcorner.R;

/**
 * Created by Administrator on 2015/12/28.
 */
public class WebviewActivity extends Activity implements View.OnClickListener{

    private WebView webView;
    String doubanapi = "http://douban.com/isbn/";
    private ImageButton back_IB;
    private Button add_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(doubanapi + getIntent().getStringExtra("book_isbn"));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        back_IB = (ImageButton) findViewById(R.id.back);
        back_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        add_B = (Button)findViewById(R.id.add);
        add_B.setOnClickListener(this);
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                addToShelf();
                Toast.makeText(this, "加入书架成功！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addToShelf(){

    }
}
