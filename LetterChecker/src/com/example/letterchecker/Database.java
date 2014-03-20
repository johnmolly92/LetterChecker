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
	
	//Teacher Table
	private static final String DATABASE_TABLE = "teacher_table";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "teachers_name";
	public static final String KEY_EMAIL = "teachers_email";
	public static final String KEY_PASSWORD = "teachers_pswrd";
	
	//Student Table
	private static final String DATABASE_S_TABLE = "student_table";
	public static final String KEY_S_ROWID = "_id";
	public static final String KEY_S_NAME = "students_name";
	public static final String KEY_S_TEACHER_EMAIL = "foreign_key";
	public static final String KEY_S_NEXT_LESSON = "next_lesson";
	
	//Reports Table
	private static final String DATABASE_R_TABLE = "report_table";
	public static final String KEY_R_ROWID = "_id";
	public static final String KEY_R_STUDENTID = "student_fk";   
	public static final String KEY_R_MARK = "mark";
	public static final String KEY_R_LESSON = "lesson";
	public static final String KEY_R_COMPLETE = "complete";
	
	//Database name
	private static final String DATABASE_NAME = "letter_checker_db";
	private static final int DATABASE_VERSION = 3;
	
	
	private DbHelper myHelper;
	private final Context myContext;
	private SQLiteDatabase myDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		//SQl instructions to create the database
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
							KEY_R_LESSON + " INTEGER, " +
							KEY_R_COMPLETE + " INTEGER);"
							);
		}

		//when upgrading the database drop all old tables
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
	
	//deletes a student based on the student's name and the teacher's email passed in as parameters
	public void deleteStudent(String studentName, String teacherEmail) throws SQLException{
		String[] array = getStudentInfo();
		String row, name, email, lesson;
		int rowToBeDeleted=-1;
		for(int i=0; i<array.length; i=i+4){
			row = array[i];
			name= array[i+1];
			email = array[i+2];
			lesson = array[i+3];
			if(studentName.equals(name) && teacherEmail.equals(email)){
				rowToBeDeleted=Integer.parseInt(row);
				i = array.length + 1;
			}
		}
		myDatabase.delete(DATABASE_S_TABLE, KEY_S_ROWID + "=" + rowToBeDeleted, null);
	}
	
	//changes a student's next lesson 
	public void changeNextLesson(String studentName, String teacherEmail,int intLesson) throws SQLException {
		//get student row
		String[] array = getStudentInfo();
		String row, name, email, lesson;
		int rowToBeUpdated=-1;
		for(int i=0; i<array.length; i=i+4){
			row = array[i];
			name= array[i+1];
			email = array[i+2];
			lesson = array[i+3];
			if(studentName.equals(name) && teacherEmail.equals(email)){
				rowToBeUpdated=Integer.parseInt(row);
				i = array.length + 1;
			}
		}
		ContentValues cv = new ContentValues();
		cv.put(KEY_S_NEXT_LESSON, intLesson);
		myDatabase.update(DATABASE_S_TABLE, cv, KEY_S_ROWID + "=" + rowToBeUpdated, null);
	}

	//creates an entry in the teacher table
	public long createEntry(String n, String e, String p) throws SQLException{
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, n);
		cv.put(KEY_EMAIL, e);
		cv.put(KEY_PASSWORD, p);
		 return myDatabase.insert(DATABASE_TABLE, null, cv);
	}
	
	//creates an entry in the student table
	public long createStudentEntry(String n, String e, int l) throws SQLException{
		ContentValues cv = new ContentValues();
		cv.put(KEY_S_NAME, n);
		cv.put(KEY_S_TEACHER_EMAIL, e);
		cv.put(KEY_S_NEXT_LESSON, l);
		return myDatabase.insert(DATABASE_S_TABLE, null, cv);
		
	}

	//creates an entry in the report table
	public long createReportEntry(String studentName, String teacherEmail, int mark, int lesson, int complete) throws SQLException{
		ContentValues cv = new ContentValues();
		String studentRow = getStudentRow(studentName, teacherEmail);
		int row = Integer.parseInt(studentRow);
		cv.put(KEY_R_STUDENTID, row);
		cv.put(KEY_R_MARK, mark);
		cv.put(KEY_R_LESSON, lesson);
		cv.put(KEY_R_COMPLETE, complete);
		return myDatabase.insert(DATABASE_R_TABLE, null, cv);
	}
	
	//get all information on all teachers in the teachers table
	public String[] getTeacherInfo() throws SQLException {
		String columns[] = new String[]{ KEY_ROWID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD};
		Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			list.add(c.getString(iRow));
			list.add(c.getString(iName));
			list.add(c.getString(iEmail));
			list.add(c.getString(iPassword));
		}
		c.close();
		String[] results = list.toArray(new String[list.size()]);
		return results;
	}
	
	
	//get all teacher names in the teacher table
	public String[] getTeacherNames() throws SQLException{
		String[] tmp = getTeacherInfo();
		ArrayList<String> list = new ArrayList<String>();
		String row, name, email, password;
		for(int i=0; i<tmp.length; i=i+4){
			row = tmp[i];
			name = tmp[i+1];
			email = tmp[i+2];
			password = tmp[1+3];
			list.add(name);
		}
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
	
	//method to take array of all students and their info and returns an array of the names of students who have a teacher
	//whos email is passed as a parameter
	public String[] getTeachersStudentsByEmail(String[] array, String email){
		String id, name, teacherEmail, nextLesson;
		ArrayList<String> tmp = new ArrayList<String>();
		for(int i=0; i<array.length; i=i+4){
			id = array[i];
			name = array[i+1];
			teacherEmail = array[i+2];
			nextLesson = array[i+3];
			if(teacherEmail.equals(email)){
				tmp.add(name);
			}
		}
		String[] result = tmp.toArray(new String[tmp.size()]);
		return result;
	}
	
	//method to take array of all students and their info and returns an array of the names of students who have a teacher
	//whos name is passed as a parameter
	public String[] getTeachersStudentsByName(String[] array, String teacherName){
		String email = getTeacherEmail(teacherName);
		String id, name, teacherEmail, nextLesson;
		ArrayList<String> tmp = new ArrayList<String>();
		for(int i=0; i<array.length; i=i+4){
			id = array[i];
			name = array[i+1];
			teacherEmail = array[i+2];
			nextLesson = array[i+3];
			if(teacherEmail.equals(email)){
				tmp.add(name);
			}
		}
		String[] result = tmp.toArray(new String[tmp.size()]);
		return result;
	}
	
	//gets a teacher's email based on their name
	public String getTeacherEmail(String teacher_name){
		String[] tmpData = getTeacherInfo();
		String result="";
		String id, name, email, password;
		for(int i=0; i<tmpData.length; i=i+4){
			id = tmpData[i];
			name = tmpData[i+1];
			email = tmpData[i+2];
			password=tmpData[1+3];
			if(name.equals(teacher_name)){
				result=email;
			}
		}
		return result;
	}
	
	//get all information on all students in the students table
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
	

	//get all student names from the student table
	public String[] getStudentNames()throws SQLException{
		String[] tmp = getStudentInfo();
		ArrayList<String> list = new ArrayList<String>();
		String row, name, email, lesson;
		for(int i=0; i<tmp.length; i=i+4){
			row = tmp[i];
			name = tmp[i+1];
			email = tmp[i+2];
			lesson = tmp[1+3];
			list.add(name);
		}
		String[] results = list.toArray(new String[list.size()]);
		return results;
	}

	//get student's next lesson based on the student's name and teacher's email
	public String getStudentNextLesson(String studentName, String teacherEmail){
		String[] tmp = getStudentInfo();
		String row, name, email, lesson;
		String result="";
		for(int i=0; i<tmp.length; i=i+4){
			row = tmp[i];
			name = tmp[i+1];
			email = tmp[i+2];
			lesson = tmp[i+3];
			if(name.equals(studentName) && email.equals(teacherEmail)){
				result = lesson;
				i = tmp.length + 1;
			}
		}
		return result;
	}
	
	//get student's row in student table
	public String getStudentRow(String studentName, String teacherEmail){
		String[] tmp = getStudentInfo();
		String row, name, email, lesson;
		String result="";
		for(int i=0; i<tmp.length; i=i+4){
			row = tmp[i];
			name = tmp[i+1];
			email = tmp[i+2];
			lesson = tmp[i+3];
			if(name.equals(studentName) && email.equals(teacherEmail)){
				result = row;
				i = tmp.length + 1;
			}
		}
		return result;
	}

	//get all reports from the reports table
	public String[] getReports() throws SQLException {
		String columns[] = new String[]{ KEY_R_ROWID, KEY_R_STUDENTID, KEY_R_MARK, KEY_R_LESSON, KEY_R_COMPLETE};
		Cursor c = myDatabase.query(DATABASE_R_TABLE, columns, null, null, null, null, null);
		ArrayList<String> list = new ArrayList<String>();
		int iRow = c.getColumnIndex(KEY_R_ROWID);
		int iStudentID = c.getColumnIndex(KEY_R_STUDENTID);
		int iMark = c.getColumnIndex(KEY_R_MARK);
		int iLesson = c.getColumnIndex(KEY_R_LESSON);
		int iComplete = c.getColumnIndex(KEY_R_COMPLETE);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			list.add(c.getString(iRow));
			list.add(c.getString(iStudentID));
			list.add(c.getString(iMark));
			list.add(c.getString(iLesson));
			list.add(c.getString(iComplete));
		}
		c.close();
		String [] results = list.toArray(new String[list.size()]);
		return results;
	}
	
	//get a report based on the the student's name and teacher's email
	public String[] getReportsForTeacher(String student, String teacherEmail) throws SQLException{
		String studentRow = getStudentRow(student, teacherEmail);
		String[] tmp = getReports();
		ArrayList<String> list = new ArrayList<String>();
		String row, studentID, mark, lesson, complete;
		for(int i=0; i<tmp.length; i=i+5){
			row = tmp[i];
			studentID = tmp[i+1];
			mark = tmp[i+2];
			lesson = tmp[i+3];
			complete = tmp[i+4];
			if(studentRow.equals(studentID)){
				list.add("Report ID: " + row);
				list.add("Student Name: " + student);
				if(lesson.equals("0")){
					list.add("Lesson: Line");
				}
				if(lesson.equals("1")){
					list.add("Lesson: Square");
				}				
				if(lesson.equals("2")){
					list.add("Lesson: Triangle");
				}
				if(lesson.equals("3")){
					list.add("Lesson: Letter 'a'");
				}
				if(lesson.equals("22")){
					list.add("Lesson: Letter 't'");
				}
				list.add("Mark: " + mark + "%");
			}
		}
		String[] results = list.toArray(new String[list.size()]);
		return results;
	}
	
	
}

