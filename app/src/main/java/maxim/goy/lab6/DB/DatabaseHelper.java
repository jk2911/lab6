package maxim.goy.lab6.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "events.db";
    public final static int SCHEMA = 1;
    final static String TABLE = "events";

    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_DESCRIPTION = "description";
    public final  static String COLUMN_CALENDAR = "date";
    public final static String COLUMN_PATH_IMAGE= "path_image";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                + " TEXT, " + COLUMN_DESCRIPTION + " TEXT"+ COLUMN_PATH_IMAGE + " TEXT);");
        // добавление начальных данных
        //db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                //+ ", " + COLUMN_YEAR  + ") VALUES ('Том Смит', 1981);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
