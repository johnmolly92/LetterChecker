package com.example.letterchecker;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class Database{
	
	private static final String DATABASE_TABLE = "teacher_table";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "teachers_name";
	public static final String KEY_EMAIL = "teachers_email";
	public static final String KEY_PASSWORD = "teachers_pswrd";
	
	private static final String DATABASE_S_TABLE = "student_table";
	public static final String KEY_S_ROWID = "_id";
	public static final String KEY_S_NAME = "students_name";
	public static final String KEY_S_TEACHER_EMAIL = "foreign_key";
	public static final String KEY_S_NEXT_LESSON = "next_lesson";
	
	private static final String DATABASE_R_TABLE = "report_table";
	public static final String KEY_R_ROWID = "_id";
	public static final String KEY_R_STUDENTID = "student_fk";   
	public static final String KEY_R_MARK = "mark";
	public static final String KEY_R_COMPLETE = "complete";
	
	
	private static final String DATABASE_NAME = "letter_checker_db";
	private static final int DATABASE_VERSION = 1;
	
	
	private DbHelper myHelper;
	private final Context myContext;
	private SQLiteDatabase myDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) throws SQLiteException {
			// TODO Auto-generated method stub
				db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
							KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
							KEY_NAME + " TEXT NOT NULL, " +
							KEY_EMAIL + " TEXT NOT NULL, " +
							KEY_PASSWORD + " TEXT NOT NULL);"	
							);
				db.execSQL("CREATE TABLE " + DATABASE_S_TABLE + " (" +
							KEY_S_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
							KEY_S_NAME + " TEXT NOT NULL, " +
							KEY_S_TEACHER_EMAIL + " TEXT NOT NULL, " +
							KEY_S_NEXT_LESSON + " INTEGER);"
							);
				db.execSQL("CREATE TABLE " + DATABASE_R_TABLE + " (" +
							KEY_R_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
							KEY_R_STUDENTID + " INTEGER, " +
							KEY_R_MARK + " INTEGER, " + 
							KEY_R_COMPLETE + " INTEGER);"
							);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLE);
			db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_S_TABLE);
			db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_R_TABLE);
			onCreate(db);
		}
	}
	
	public Database(Context c){
		myContext = c;
	}
	
	public Database open() throws SQLException{
		myHelper = new DbHelper(myContext);
		myDatabase = myHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		myHelper.close();
	}
	
	public void deleteStudent(String studentName, String teacherEmail) throws SQLException{
		myDatabase.delete(DATABASE_S_TABLE, KEY_S_NAME + "=" + studentName, null);
	}

	public long createEntry(String n, String e, String p) throws SQLException{
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, n);
		cv.put(KEY_EMAIL, e);
		cv.put(KEY_PASSWORD, p);
		 return myDatabase.insert(DATABASE_TABLE, null, cv);
	}
	
	public long createStudentEntry(String n, String e, int l){
		ContentValues cv = new ContentValues();
		cv.put(KEY_S_NAME, n);
		cv.put(KEY_S_TEACHER_EMAIL, e);
		cv.put(KEY_S_NEXT_LESSON, l);
		return myDatabase.insert(DATABASE_S_TABLE, null, cv);
		
	}

	public long createReportEntry(int studentId, int mark, int complete){
		ContentValues cv = new ContentValues();
		cv.put(KEY_R_STUDENTID, studentId);
		cv.put(KEY_R_MARK, mark);
		cv.put(KEY_R_COMPLETE, complete);
		return myDatabase.insert(DATABASE_R_TABLE, null, cv);
	}
	
	public String[] getTeacherInfo() throws SQLException {
		String columns[] = new String[]{ KEY_ROWID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD};
		Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			//result = result + c.getString(iRow) + " " + c.getString(iName) + " " + c.getString(iEmail) + " " + c.getString(iPassword) + "\n";
			list.add(c.getString(iRow));
			list.add(c.getString(iName));
			list.add(c.getString(iEmail));
			list.add(c.getString(iPassword));
		}
		c.close();
		String[] results = list.toArray(new String[list.size()]);
		return results;
	}
	
	//returns an array of teacher names
	public String[] getTeacherNames() throws SQLException {
		String columns[] = new String[]{ KEY_ROWID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD};
		Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			//result = result + c.getString(iRow) + " " + c.getString(iName) + " " + c.getString(iEmail) + " " + c.getString(iPassword) + "\n";
			c.getString(iRow);
			list.add(c.getString(iName));
			c.getString(iEmail);
			c.getString(iPassword);
		}
		c.close();
		String[] results = list.toArray(new String[list.size()]);
		return results;
	}
	
	//returns an array of login info eg {email,password,email,password....}
	public String[] getTeacherLoginInfo() throws SQLException{
		String columns[] = new String[]{ KEY_ROWID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD};
		Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			c.getString(iRow);
			c.getString(iName);
			list.add(c.getString(iEmail));
			list.add(c.getString(iPassword));
		}
		c.close();
		String [] results = list.toArray(new String[list.size()]);
		return results;
	}
	
	public String[] getStudentInfo() throws SQLException{
		String columns[] = new String[]{ KEY_S_ROWID, KEY_S_NAME, KEY_S_TEACHER_EMAIL, KEY_S_NEXT_LESSON};
		Cursor c = myDatabase.query(DATABASE_S_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_S_ROWID);
		int iName = c.getColumnIndex(KEY_S_NAME);
		int iEmail = c.getColumnIndex(KEY_S_TEACHER_EMAIL);
		int iLesson = c.getColumnIndex(KEY_S_NEXT_LESSON);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			list.add(c.getString(iRow));
			list.add(c.getString(iName));
			list.add(c.getString(iEmail));
			list.add(c.getString(iLesson));
		}
		c.close();
		String [] results = list.toArray(new String[list.size()]);
		return results;
		
	}
	
	public String[] getStudentNames() throws SQLException{
		String columns[] = new String[]{ KEY_S_ROWID, KEY_S_NAME, KEY_S_TEACHER_EMAIL, KEY_S_NEXT_LESSON};
		Cursor c = myDatabase.query(DATABASE_S_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_S_ROWID);
		int iName = c.getColumnIndex(KEY_S_NAME);
		int iEmail = c.getColumnIndex(KEY_S_TEACHER_EMAIL);
		int iLesson = c.getColumnIndex(KEY_S_NEXT_LESSON);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			c.getString(iRow);
			list.add(c.getString(iName));
			c.getString(iEmail);
			c.getString(iLesson);
		}
		c.close();
		String [] results = list.toArray(new String[list.size()]);
		return results;
		
	}

	public void changeNextLesson(String tmpStudent, String tmpEmail,
			int intLesson) {
		//myDatabase.execSQL("UPDATE " + DATABASE_S_TABLE + )
		
	}
}

