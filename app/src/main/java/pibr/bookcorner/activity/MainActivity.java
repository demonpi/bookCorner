package pibr.bookcorner.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.HashMap;

import pibr.bookcorner.R;
import pibr.bookcorner.fragment.BookListFragment;
import pibr.bookcorner.fragment.UserFragment;
import pibr.bookcorner.service.BookService;
import pibr.bookcorner.service.InitDataBase;

public class MainActivity extends Activity implements View.OnClickListener{

    private BottomBar mBottomBar;

    private BookListFragment bookListFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setActiveTabColor("#67d0e2");
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                switch (menuItemId) {
                    case R.id.bottomBarItemOne:
                        if (bookListFragment == null)
                        {
                            bookListFragment = new BookListFragment();
                        }
                        // 使用当前Fragment的布局替代main_content的控件
                        transaction.replace(R.id.main_content, bookListFragment);
                        transaction.commit();
                        break;
                    case R.id.bottomBarItemTwo:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), ARActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.bottomBarItemThree:
                        if (userFragment == null)
                        {
                            userFragment = new UserFragment();
                        }
                        // 使用当前Fragment的布局替代main_content的控件
                        transaction.replace(R.id.main_content, userFragment);
                        transaction.commit();
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

        setDefaultFragment();

    }


    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        bookListFragment = new BookListFragment();
        transaction.replace(R.id.main_content, bookListFragment);
        transaction.commit();
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
        }
    }


}
