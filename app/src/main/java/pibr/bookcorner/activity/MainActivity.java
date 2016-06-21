package pibr.bookcorner.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.HashMap;

import pibr.bookcorner.R;
import pibr.bookcorner.service.BookService;
import pibr.bookcorner.service.InitDataBase;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ImageButton qrcode_IB;
    private EditText search_ET;
    private Button search_B;
    private ListView main_LV;

    private BottomBar mBottomBar;

    private BookService booklist_BS;
    private InitDataBase book_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        book_ID = new InitDataBase(this);

        qrcode_IB = (ImageButton)findViewById(R.id.QRcode);
        search_ET = (EditText)findViewById(R.id.search_text);
        search_B = (Button)findViewById(R.id.button_search);
        main_LV =(ListView)findViewById(R.id.main_lv);

        booklist_BS = new BookService(this);

        booklist_BS.select();
        main_LV.setAdapter(booklist_BS.getAdapter(this));

        search_B.setOnClickListener(this);
        qrcode_IB.setOnClickListener(this);

        main_LV.setOnItemClickListener(this);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setActiveTabColor("#67d0e2");

        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.blue_dark));
//        mBottomBar.mapColorForTab(1, "#7B1FA2");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }



    /*
    * 点击响应函数*/
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.QRcode:{
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), qrcodeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_search:{
                if (search_ET.getText().length()==0){
                    Toast.makeText(this,"请输入搜索内容",Toast.LENGTH_LONG).show();
                }
                else{
                    search();
                }
                break;
            }
        }
    }
    //搜索响应
    protected void search(){
        String tmp = search_ET.getText().toString();
        booklist_BS.select(tmp);
        main_LV.setAdapter(booklist_BS.getAdapter(this));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position,
                            long ids){
        SimpleAdapter simpleAdapter = (SimpleAdapter) adapterView.getAdapter();
        HashMap<String, Object> hashMap = (HashMap)simpleAdapter.getItem(position);
        String name = (String)hashMap.get("book_name");
        //传递书名开启新activity，容易出问题，现阶段这么处理
        //采用setType的方式解决这个问题
        Intent intent = new Intent(this,DetailActivity.class);
        intent.setType("name");
        intent.putExtra("book_name",name);
        startActivity(intent);

    }

}
