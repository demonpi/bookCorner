package pibr.bookcorner.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;

import java.util.HashMap;

import pibr.bookcorner.R;
import pibr.bookcorner.activity.DetailActivity;
import pibr.bookcorner.activity.qrcodeActivity;
import pibr.bookcorner.service.BookService;
import pibr.bookcorner.service.InitDataBase;

/**
 * 主页显示的list表
 * 作者：pishao
 * 创建日期：2016.07.02
 */
public class BookListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageButton qrcode_IB;
    private EditText search_ET;
    private Button search_B;
    private ListView main_LV;

    private BookService booklist_BS;
    private InitDataBase book_ID;

    public BookListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booklist, container, false);

        book_ID = new InitDataBase(this.getActivity());

        qrcode_IB = (ImageButton)view.findViewById(R.id.QRcode);
        search_ET = (EditText)view.findViewById(R.id.search_text);
        search_B = (Button)view.findViewById(R.id.button_search);

        search_B.setOnClickListener(this);
        qrcode_IB.setOnClickListener(this);

        booklist_BS = new BookService(this.getActivity());
        booklist_BS.select();

        main_LV =(ListView)view.findViewById(R.id.main_lv);
        main_LV.setAdapter(booklist_BS.getAdapter(this.getActivity()));
        main_LV.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.QRcode:{
                Intent intent = new Intent();
                intent.setClass(this.getActivity(), qrcodeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_search:{
                if (search_ET.getText().length()==0){
                    Toast.makeText(this.getActivity(),"请输入搜索内容",Toast.LENGTH_LONG).show();
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
        main_LV.setAdapter(booklist_BS.getAdapter(this.getActivity()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SimpleAdapter simpleAdapter = (SimpleAdapter) parent.getAdapter();
        HashMap<String, Object> hashMap = (HashMap)simpleAdapter.getItem(position);
        String name = (String)hashMap.get("book_name");
        //传递书名开启新activity，容易出问题，现阶段这么处理
        //采用setType的方式解决这个问题
        Intent intent = new Intent(this.getActivity(),DetailActivity.class);
        intent.setType("name");
        intent.putExtra("book_name",name);
        startActivity(intent);
    }
}
