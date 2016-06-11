package pibr.bookcorner.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import pibr.bookcorner.R;


public class BookService {
	private DatabaseHelper booklist_DH;
    private SQLiteDatabase booklist_SQLD;
    private ArrayList<HashMap<String, Object>> booklist_AL;
    private SimpleAdapter booklist_SA;

    private Cursor cursor;
    private int count = 0;

    public BookService(Context context){
		booklist_DH=new DatabaseHelper(context);
        booklist_SQLD = booklist_DH.getReadableDatabase();
	}


    public int getCount(){
        return count;
    }


    public void select(){
        String sql = "select * from bc_bookdetail";
        cursor = booklist_SQLD.rawQuery(sql,null);
        count = cursor.getCount();
    }

    public void select(String _sql){
        String sql = "select * from bc_bookdetail where name like ?";
        cursor = booklist_SQLD.rawQuery(sql,new String[]{"%"+_sql+"%"});
        count = cursor.getCount();
    }

    //通过书名搜索信息
    public HashMap<String, Object> selectDetail(String _sql){
        HashMap<String, Object> result = new HashMap<>();
        //SQL语句记得写from
        String sql = "select isbn, cover, count, author, press, brief from bc_bookdetail where name = ?";
        cursor = booklist_SQLD.rawQuery(sql,new String[]{_sql});
        cursor.moveToFirst();
        result.put("book_isbn", cursor.getLong(cursor.getColumnIndex("isbn")));
        result.put("book_cover", cursor.getString(cursor.getColumnIndex("cover")));
        result.put("book_count",cursor.getInt(cursor.getColumnIndex("count")));
        result.put("book_author",cursor.getString(cursor.getColumnIndex("author")));
        result.put("book_press",cursor.getString(cursor.getColumnIndex("press")));
        result.put("book_brief",cursor.getString(cursor.getColumnIndex("brief")));

        return result;
    }

    //通过isbn搜索书籍信息
    public HashMap<String, Object> selectISBN(String _sql){
        HashMap<String, Object> result = new HashMap<>();
        //SQL语句记得写from
        String sql = "select name, cover, count, author, press, brief from bc_bookdetail where isbn = ?";
        cursor = booklist_SQLD.rawQuery(sql,new String[]{_sql});
        if(!cursor.moveToFirst()){
            result.put("result","未找到");
        }
        else{
            result.put("result","ok");
            result.put("book_name", cursor.getString(cursor.getColumnIndex("name")));
            result.put("book_cover", cursor.getString(cursor.getColumnIndex("cover")));
            result.put("book_count",cursor.getInt(cursor.getColumnIndex("count")));
            result.put("book_author",cursor.getString(cursor.getColumnIndex("author")));
            result.put("book_press",cursor.getString(cursor.getColumnIndex("press")));
            result.put("book_brief",cursor.getString(cursor.getColumnIndex("brief")));
        }


        return result;
    }

    public ArrayList getArrayList(Context context){
        booklist_AL = new ArrayList<HashMap<String, Object>>();
        if(cursor.moveToFirst()){
            for(int i = 0; i < count; i++){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("book_cover", context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex("cover")),"drawable",context.getPackageName()));
                map.put("book_name",cursor.getString(cursor.getColumnIndex("name")));
                map.put("book_author",cursor.getString(cursor.getColumnIndex("author")));
                booklist_AL.add(map);
                cursor.moveToNext();
            }
        }
        else{
            Toast.makeText(context,"未找到图书",Toast.LENGTH_SHORT).show();
        }

        cursor.close();

        return booklist_AL;
    }

    public SimpleAdapter getAdapter(Context context){
        booklist_SA =  new SimpleAdapter(context,getArrayList(context),R.layout.mainlist_layout,new String[]{"book_cover","book_name","book_author"},new int[]{R.id.iv_book,R.id.tv_bookname,R.id.tv_bookauthor});
        return booklist_SA;
    }

    public void close(){
        booklist_DH.close();
    }

    public void borrowBook(String _sql){
        String sql = "select count from bc_bookdetail where isbn = ?";
        cursor = booklist_SQLD.rawQuery(sql,new String[]{_sql});
        if(cursor.moveToFirst()){
            int num = cursor.getInt(cursor.getColumnIndex("count"));
            num--;
            sql = "update bc_bookdetail set count = ? where isbn = ?";
            booklist_SQLD.execSQL(sql,new Object[]{num,_sql});
        }
    }

    public void returnBook(String _sql){
        String sql = "select count from bc_bookdetail where isbn = ?";
        cursor = booklist_SQLD.rawQuery(sql,new String[]{_sql});
        if(cursor.moveToFirst()){
            int num = cursor.getInt(cursor.getColumnIndex("count"));
            num++;
            sql = "update bc_bookdetail set count = ? where isbn = ?";
            booklist_SQLD.execSQL(sql,new Object[]{num,_sql});
        }
    }

}
