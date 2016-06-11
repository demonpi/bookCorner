package pibr.bookcorner.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	static String name="bookcorner.db";
	static int dbVersion=1;
	public DatabaseHelper(Context context) {
		super(context, name, null, dbVersion);
	}

	public void onCreate(SQLiteDatabase db) {
		//String sql="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";

		//语句不能有回车，不能有转义字符

//        String sql="CREATE TABLE bc_bookdetail(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,isbn INTEGER NOT NULL,	cover TEXT,	name  TEXT NOT NULL,status INTEGER NOT NULL,	count INTEGER NOT NULL,	author TEXT,press TEXT,	brief TEXT)";
//		db.execSQL(sql);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
