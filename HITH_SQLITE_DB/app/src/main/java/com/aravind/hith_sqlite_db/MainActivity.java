package com.aravind.hith_sqlite_db;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private EditText e_id,e_name,e_mail,e_coursecount;
    private Button insert_btn,delete_btn,update_btn,view_btn,viewAll_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        e_id = findViewById(R.id.editText_id);
        e_name = findViewById(R.id.editText_name);
        e_mail = findViewById(R.id.editText_email);
        e_coursecount = findViewById(R.id.editText_CC);

        insert_btn = findViewById(R.id.button_add);
        delete_btn = findViewById(R.id.button_delete);
        update_btn = findViewById(R.id.button_update);
        view_btn = findViewById(R.id.button_view);
        viewAll_btn = findViewById(R.id.button_viewAll);
       // showMessage("Alert Dialog","I am Working!");
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_add:
                boolean isInserted = databaseHelper.insertData(e_name.getText().toString(),e_mail.getText().toString(),e_coursecount.getText().toString());

                if (isInserted == true){
                    Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    clearDataFields();
                }else {
                    Toast.makeText(this, "Sorry,Something went wrong.", Toast.LENGTH_SHORT).show();
                    clearDataFields();
                }
                break;
            case R.id.button_view:
                String id = e_id.getText().toString();

                if (id.equals(String.valueOf(""))){
                    e_id.setError("Please Enter Id");
                    return;
                }

                Cursor cursor = databaseHelper.getData(id);
                String Data = "";
                if (cursor.moveToNext()){
                    Data = "Id: "+cursor.getString(0)+"\n"+
                            "Name: "+cursor.getString(1)+"\n"+
                            "E-mail: "+cursor.getString(2)+"\n"+
                            "Course Count: "+cursor.getString(3);
                }
                showMessage("Users Data",Data);
                clearDataFields();
                break;
            case R.id.button_viewAll:
                 cursor =databaseHelper.getALLData();
                 if (cursor.getCount()==0){
                     showMessage("Error","Sorry,There is no Data");
                     return;
                 }
                 StringBuffer buffer = new StringBuffer();
                 while (cursor.moveToNext()){
                     buffer.append("Id: "+cursor.getString(0)+"\n");
                     buffer.append("Name: "+cursor.getString(1)+"\n");
                     buffer.append("E-mail: "+cursor.getString(2)+"\n");
                     buffer.append("Course Count: "+cursor.getString(3)+"\n\n");
                 }
                 showMessage("All Users Data",buffer.toString());
                break;

            case R.id.button_update:
                boolean isUpdate = databaseHelper.updateData(e_id.getText().toString(),
                        e_name.getText().toString(),
                        e_mail.getText().toString(),
                        e_coursecount.getText().toString());

                if (isUpdate == true){
                    Toast.makeText(this, "Data Is Updated successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Oops!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_delete:
                 id = e_id.getText().toString();
                if (id.equals(String.valueOf(""))){
                    e_id.setError("Please Provide Me Id");
                }else {
                    Integer deleteRow = databaseHelper.deleteData(id);
                    if (deleteRow>0){
                        Toast.makeText(this, "The Record Is successfully Deleted.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Oops!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void clearDataFields(){
        e_id.setText("");
        e_name.setText("");
        e_mail.setText("");
        e_coursecount.setText("");
    }
}
