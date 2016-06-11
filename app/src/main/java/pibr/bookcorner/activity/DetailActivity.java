package pibr.bookcorner.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import pibr.bookcorner.R;
import pibr.bookcorner.service.BookService;

/**
 * Created by Administrator on 2015/12/26.
 */
public class DetailActivity extends Activity implements View.OnClickListener{
    private ImageButton back_IB;
    private Button borrow_B;
    private Button return_B;
    private Button confirm_B;

    private ImageView book_cover_IV;
    private TextView book_name_TV;
    private TextView book_status_TV;
    private TextView book_author_TV;
    private TextView book_press_TV;
    private TextView book_brief_TV;
    private TextView book_isbn_TV;

    private String book_name;
    private String book_isbn;

    private HashMap<String, Object> detail_HM;
    private BookService book_BS;

    private Dialog dialog;
    enum DIALOGTYPE{BORROW, RETURNED};
    DIALOGTYPE dialogType;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        back_IB = (ImageButton)findViewById(R.id.back);
        back_IB.setOnClickListener(this);

        borrow_B = (Button) findViewById(R.id.btn_borrowbook);
        borrow_B.setOnClickListener(this);
        return_B = (Button) findViewById(R.id.btn_returnbook);
        return_B.setOnClickListener(this);

        book_cover_IV = (ImageView)findViewById(R.id.book_cover);
        book_name_TV = (TextView) findViewById(R.id.book_name);
        book_status_TV = (TextView) findViewById(R.id.book_status);
        book_author_TV = (TextView) findViewById(R.id.book_author);
        book_press_TV = (TextView) findViewById(R.id.book_press);
        book_brief_TV = (TextView) findViewById(R.id.book_brief);
        book_isbn_TV = (TextView) findViewById(R.id.book_isbn);

        intent = getIntent();

        if(intent.getType().equals("name")){
            book_name = intent.getStringExtra("book_name");
            book_name_TV.setText(book_name);

            book_BS = new BookService(this);
            init_name_detail();
        }
        else if(intent.getType().equals("isbn")){
            book_isbn = intent.getStringExtra("book_isbn");
            book_isbn_TV.setText(book_isbn);

            book_BS = new BookService(this);
            init_isbn_detail();
        }

    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:{
                finish();
                break;
            }
            case R.id.btn_returnbook:{
                dialog_return();
                break;
            }
            case R.id.btn_borrowbook:{
                dialog_borrow();
                break;
            }
            case R.id.confirm:{
                switch (dialogType){
                    case BORROW:{
                        dialog.cancel();
                        Toast.makeText(this,"借书成功",Toast.LENGTH_LONG).show();
                        if(intent.getType().equals("name")){
                            book_name = intent.getStringExtra("book_name");
                            book_name_TV.setText(book_name);

                            book_BS = new BookService(this);
                            init_name_detail();
                        }
                        else if(intent.getType().equals("isbn")){
                            book_isbn = intent.getStringExtra("book_isbn");
                            book_isbn_TV.setText(book_isbn);

                            book_BS = new BookService(this);
                            init_isbn_detail();
                        }
                        break;
                    }
                    case RETURNED:{
                        dialog.cancel();
                        Toast.makeText(this,"还书成功",Toast.LENGTH_LONG).show();
                        if(intent.getType().equals("name")){
                            book_name = intent.getStringExtra("book_name");
                            book_name_TV.setText(book_name);
                            book_BS = new BookService(this);
                            init_name_detail();
                        }
                        else if(intent.getType().equals("isbn")){
                            book_isbn = intent.getStringExtra("book_isbn");
                            book_isbn_TV.setText(book_isbn);
                            book_BS = new BookService(this);
                            init_isbn_detail();
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    private void init_name_detail(){
        detail_HM =  book_BS.selectDetail(book_name);
        book_isbn = detail_HM.get("book_isbn").toString();
        book_isbn_TV.setText(detail_HM.get("book_isbn").toString());
        //调用前一定要记得赋值，不然就空指针了！！！
        if ((detail_HM.get("book_cover") != null) && (getResources().getIdentifier(detail_HM.get("book_cover").toString(), "drawable", getPackageName()) > 0)){
            book_cover_IV.setImageResource(getResources().getIdentifier(detail_HM.get("book_cover").toString(), "drawable", getPackageName()));
        }
        else{
            book_cover_IV.setImageResource(R.drawable.book_null);
        }

        if ((int)detail_HM.get("book_count") > 0){
            book_status_TV.setText("可借");
            borrow_B.setBackgroundResource(R.color.blue_lite);
        }
        else{
            book_status_TV.setText("借光");
            borrow_B.setBackgroundResource(R.color.gray);
        }

        if (detail_HM.get("book_author") != null){
            book_author_TV.setText(detail_HM.get("book_author").toString());
        }
        if (detail_HM.get("book_press") != null){
            book_press_TV.setText(detail_HM.get("book_press").toString());
        }
        if (detail_HM.get("book_brief") != null){
            book_brief_TV.setText(detail_HM.get("book_brief").toString());
        }

      }

    //画面初始化
    private void init_isbn_detail(){
        detail_HM =  book_BS.selectISBN(book_isbn);
        if(detail_HM.get("result").equals("ok")){
            book_name_TV.setText(detail_HM.get("book_name").toString());
            //调用前一定要记得赋值，不然就空指针了！！！
            if ((detail_HM.get("book_cover") != null) && (getResources().getIdentifier(detail_HM.get("book_cover").toString(), "drawable", getPackageName()) > 0)){
                book_cover_IV.setImageResource(getResources().getIdentifier(detail_HM.get("book_cover").toString(), "drawable", getPackageName()));
            }
            else{
                book_cover_IV.setImageResource(R.drawable.book_null);
            }

            if ((int)detail_HM.get("book_count") > 0){
                book_status_TV.setText("可借");
                borrow_B.setBackgroundResource(R.color.blue_lite);
            }
            else{
                book_status_TV.setText("借光");
                borrow_B.setBackgroundResource(R.color.gray);
            }

            if (detail_HM.get("book_author") != null){
                book_author_TV.setText(detail_HM.get("book_author").toString());
            }
            if (detail_HM.get("book_press") != null){
                book_press_TV.setText(detail_HM.get("book_press").toString());
            }
            if (detail_HM.get("book_brief") != null){
                book_brief_TV.setText(detail_HM.get("book_brief").toString());
            }
        }
        else {
            Toast.makeText(this,"书架上未找到图书",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("book_isbn",book_isbn);
            startActivity(intent);
        }



    }

    private void dialog_return(){
        dialog = new Dialog(this);
        dialog.setTitle("还书");
        dialog.setContentView(R.layout.pop_layout);
        dialog.show();
        dialogType = DIALOGTYPE.RETURNED;
        confirm_B = (Button)dialog.findViewById(R.id.confirm);
        confirm_B.setOnClickListener(this);
        book_BS.returnBook(book_isbn);
    }

    private void dialog_borrow(){
        if ((int)detail_HM.get("book_count") == 0){
            Toast.makeText(this,"已借光",Toast.LENGTH_LONG).show();
        }
        else {
            dialog = new Dialog(this);
            dialog.setTitle("借书");
            dialog.setContentView(R.layout.pop_layout);
            dialog.show();
            dialogType = DIALOGTYPE.BORROW;
            confirm_B = (Button)dialog.findViewById(R.id.confirm);
            confirm_B.setOnClickListener(this);
            book_BS.borrowBook(book_isbn);
        }

    }


}
