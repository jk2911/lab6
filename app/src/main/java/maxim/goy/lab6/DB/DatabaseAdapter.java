package maxim.goy.lab6.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import maxim.goy.lab6.Model.Event;

public class DatabaseAdapter implements IRepository<Event> {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public DatabaseAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        db.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[]{dbHelper.COLUMN_ID, dbHelper.COLUMN_NAME,
                dbHelper.COLUMN_DESCRIPTION, dbHelper.COLUMN_DESCRIPTION,
                dbHelper.COLUMN_CALENDAR, dbHelper.COLUMN_PATH_IMAGE};
        return db.query(dbHelper.TABLE, columns, null, null, null, null, null);
    }

    @SuppressLint("Range")
    @Override
    public List<Event> getAll() {
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR))));
            events.add(new Event(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)),
                    calendar,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH_IMAGE))
            ));
        }
        return events;
    }

    @Override
    public long getCount() {
        return DatabaseUtils.queryNumEntries(db, DatabaseHelper.TABLE);
    }

    @SuppressLint("Range")
    @Override
    public Event get(long id) {
        Event event = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR))));
            event = new Event(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)),
                    calendar,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH_IMAGE))
            );
        }
        cursor.close();
        return event;
    }

    @Override
    public long insert(Event event) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, event.name);
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, event.description);
        cv.put(DatabaseHelper.COLUMN_CALENDAR, event.calendar.getTimeInMillis() + "");
        cv.put(DatabaseHelper.COLUMN_PATH_IMAGE, event.pathImages);

        return db.insert(DatabaseHelper.TABLE,null, cv);
    }

    @Override
    public long update(Event event) {
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + event.id;
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, event.name);
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, event.description);
        cv.put(DatabaseHelper.COLUMN_CALENDAR, event.calendar.getTimeInMillis() + "");
        cv.put(DatabaseHelper.COLUMN_PATH_IMAGE, event.pathImages);
        return db.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

    @Override
    public long remove(long id) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return db.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }
}
