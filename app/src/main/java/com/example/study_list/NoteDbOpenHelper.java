package com.example.study_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.text.TextUtils;

import com.example.study_list.bean.Note;

import java.util.ArrayList;
import java.util.List;

class NoteDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "noteSQLite.db";
    private static final String TABLE_NAME_NOTE = "note";
    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + "(id integer primary key autoincrement, title text, content text, create_time text)";

    public NoteDbOpenHelper(Context context){super(context,DB_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long insertData(Note note){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("content",note.getContent());
        values.put("create_time",note.getCreatedTime());

        return db.insert(TABLE_NAME_NOTE,null,values);
    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id});
//        return db.delete(TABLE_NAME_NOTE, "id is ?", new String[]{id});
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id});
    }
    //通过id进行删除

    public int updateData(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());

        return db.update(TABLE_NAME_NOTE, values, "id like ?", new String[]{note.getId()});
    }

    public List<Note> queryAllFromDb(){
        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE,null,null,null,null,null,null);

        if (cursor != null){
            while (cursor.moveToNext()){
                int idIndex = cursor.getColumnIndex("id");
                int titleIndex = cursor.getColumnIndex("title");
                int contentIndex = cursor.getColumnIndex("content");
                int createTimeIndex = cursor.getColumnIndex("create_time");

                String id = cursor.getString(idIndex);
                String title = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);
                String createTime = cursor.getString(createTimeIndex);

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setContent(content);
                note.setCreatedTime(createTime);

                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }

    public List<Note> queryFromDbByTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "title like ?", new String[]{"%"+title+"%"}, null, null, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("id");
                int titleIndex = cursor.getColumnIndex("title");
                int contentIndex = cursor.getColumnIndex("content");
                int createTimeIndex = cursor.getColumnIndex("create_time");

                String id = cursor.getString(idIndex);
                String title2 = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);
                String createTime = cursor.getString(createTimeIndex);

                Note note = new Note();
                note.setId(id);
                note.setTitle(title2);
                note.setContent(content);
                note.setCreatedTime(createTime);
                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }
}
