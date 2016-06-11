package pibr.bookcorner.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import pibr.bookcorner.R;

/**
 * Created by Administrator on 2015/12/26.
 * 用于从raw文件夹中读入数据库文件，同时可用于数据库文件的更新
 */
public class InitDataBase {

        private static final String DATABASE_PATH = "/data/data/pibr.bookcorner/databases";
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "bookcorner.db";

        private static String outFileName = DATABASE_PATH + "/" + DATABASE_NAME;

        private Context context;
        private SQLiteDatabase database;

        public InitDataBase(Context context) {
            this.context = context;

            File file = new File(outFileName);
            if (file.exists()) {
                database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
                if (database.getVersion() < DATABASE_VERSION) {
                    database.close();
                    file.delete();
                }
            }
            try {
                buildDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private void buildDatabase() throws Exception {
            InputStream myInput = context.getResources().openRawResource(R.raw.bookcorner);
            File file = new File(outFileName);

            File dir = new File(DATABASE_PATH);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    throw new Exception("创建失败");
                }
            }

            if (!file.exists()) {
                try {
                    OutputStream myOutput = new FileOutputStream(outFileName);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = myInput.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                    }
                    myOutput.close();
                    myInput.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
}
