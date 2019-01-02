package com.medamoniaravind.sqlitelocaldb;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import static com.basgeekball.awesomevalidation.utility.RegexTemplate.*;

public class  InsertdataActivity extends AppCompatActivity {
    Dbhandler dbhandler;
    EditText edt_insdname,edt_insdmail,edt_insdmbnum,edt_insdpswd,edt_insdid;
    Button btn_insdsub,btn_insdgetdt,btn_insdupdate,btn_insddelete;
    String usrname,usremail,usrmobilenum,usrpassword,usrid;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdata);
        dbhandler=new Dbhandler(this);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        modify();



    }
    public void modify(){
        edt_insdname=(EditText)findViewById(R.id.idact_name);
        edt_insdmail=(EditText)findViewById(R.id.idact_mail);
        edt_insdmbnum=(EditText)findViewById(R.id.idact_mbnum);
        edt_insdpswd=(EditText)findViewById(R.id.idact_pswd);
        edt_insdid=(EditText)findViewById(R.id.idact_id);
        btn_insdsub=(Button)findViewById(R.id.idact_submit);
        btn_insdgetdt=(Button)findViewById(R.id.idact_getdata);
        btn_insdupdate=(Button)findViewById(R.id.idact_update);
        btn_insddelete=(Button)findViewById(R.id.idact_deletedata);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this,R.id.idact_name,"[a-zA-Z\\s]+",R.string.Username_error);
        awesomeValidation.addValidation(this,R.id.idact_mail, android.util.Patterns.EMAIL_ADDRESS,R.string.Email_error);
        awesomeValidation.addValidation(this,R.id.idact_mbnum, RegexTemplate.TELEPHONE,R.string.Mbnum_error);
        awesomeValidation.addValidation(this,R.id.idact_pswd, regexPassword,R.string.Password_error);
        btn_insdsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (awesomeValidation.validate()){
                            usrname=edt_insdname.getText().toString();
                            usremail=edt_insdmail.getText().toString();
                            usrmobilenum=edt_insdmbnum.getText().toString().trim();
                            usrpassword=edt_insdpswd.getText().toString();
                            boolean b= dbhandler.insertdata(usrname,usremail,usrmobilenum,usrpassword);
                                if (b==true){
                                    edt_insdname.setText("");
                                    edt_insdmail.setText("");
                                    edt_insdmbnum.setText("");
                                    edt_insdpswd.setText("");
                                    Toast.makeText(InsertdataActivity.this, "your data is inserted successfully", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                          /*  edt_insdname.setText("");
                            edt_insdmail.setText("");
                            edt_insdmbnum.setText("");
                            edt_insdpswd.setText("");*/
                            Toast.makeText(InsertdataActivity.this, "please enter all details ", Toast.LENGTH_SHORT).show();

                        }




            }
        });
    }

    public void submitdata(View view) {

    }

    public void getuserdata(View view) {
        Cursor res=dbhandler.getAllData();
        if(res.getCount()==0){
            showmessage("Error","No data found here");
            return;
        }

        StringBuffer stringBuffer=new StringBuffer();
        while (res.moveToNext()){
            stringBuffer.append("Id :"+res.getString(0)+"\n");
            stringBuffer.append("Username :"+res.getString(1)+"\n");
            stringBuffer.append("E_Mail :"+res.getString(2)+"\n");
            stringBuffer.append("Mobile Number :"+res.getString(3)+"\n");
            stringBuffer.append("Password :"+res.getString(4)+"\n");

        }
        showmessage("User Data",stringBuffer.toString());
    }

    public void showmessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateuserdata(View view) {
        if (usrid!=null){
            usrid=edt_insdid.getText().toString();
            usrname=edt_insdname.getText().toString();
            usremail=edt_insdmail.getText().toString();
            usrmobilenum=edt_insdmbnum.getText().toString();
            usrpassword=edt_insdpswd.getText().toString();
            boolean update=dbhandler.updatedata(usrid,usrname,usremail,usrmobilenum,usrpassword);
            if (update == true) {
                edt_insdid.setText("");
                edt_insdname.setText("");
                edt_insdmail.setText("");
                edt_insdmbnum.setText("");
                edt_insdpswd.setText("");
                Toast.makeText(this, "your data is updated successfully", Toast.LENGTH_SHORT).show();


            }
        }
        /*else {
                edt_insdid.setText("");
                edt_insdname.setText("");
                edt_insdmail.setText("");
                edt_insdmbnum.setText("");
                edt_insdpswd.setText("");
                Toast.makeText(this, "sorry your data is not updated ", Toast.LENGTH_SHORT).show();
            }*/


        else {
            edt_insdid.setText("");
            edt_insdname.setText("");
            edt_insdmail.setText("");
            edt_insdmbnum.setText("");
            edt_insdpswd.setText("");
            Toast.makeText(this, "sorry your data is not updated ", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteuserdata(View view) {
        usrid=edt_insdid.getText().toString();
       Integer deletedrow = dbhandler.deletedata(usrid);
       if (deletedrow > 0){
           edt_insdid.setText("");
           Toast.makeText(this, "Data is deleted successfully", Toast.LENGTH_SHORT).show();
       }
       else {
           edt_insdid.setText("");
           Toast.makeText(this, "Data is not deleted", Toast.LENGTH_SHORT).show();
       }
    }
}
