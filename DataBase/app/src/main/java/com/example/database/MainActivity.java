package com.example.database;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import  com.example.database.DBHelper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText name, rollNo, marks , result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(MainActivity.this);


        name = findViewById(R.id.name);
        rollNo = findViewById(R.id.roll_no);
        marks = findViewById(R.id.marks);
        result = findViewById(R.id.result);
        Button insertButton = findViewById(R.id.insertStudent);


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s_name = name.getText().toString();
                String s_marks = marks.getText().toString();
                String s_roll = rollNo.getText().toString();
                String s_result = result.getText().toString();

                int n_marks ;

                if(s_name.isEmpty() || s_roll.isEmpty() || s_marks.isEmpty() || s_result.isEmpty()){

                    Toast.makeText(MainActivity.this , "Every Info must be filled" , Toast.LENGTH_SHORT).show();
                    return;

                }

                try{
                     n_marks = Integer.parseInt(s_marks);
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this , "Marks should be valid integer" , Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.addNewStudent(s_name , s_roll , n_marks , s_result);
                Toast.makeText(MainActivity.this , "Student has been added" , Toast.LENGTH_SHORT).show();

                name.setText("");
                rollNo.setText("");
                marks.setText("");
                result.setText("");
            }
        });


        Button updateButton = findViewById(R.id.updateStudent);

        updateButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v){

                String s_name = name.getText().toString();
                String s_marks = marks.getText().toString();
                String s_roll = rollNo.getText().toString();
                String s_result = result.getText().toString();

                int n_marks ;

                if(s_name.isEmpty() || s_roll.isEmpty() || s_marks.isEmpty() || s_result.isEmpty()){

                    Toast.makeText(MainActivity.this , "Every Info must be filled" , Toast.LENGTH_SHORT).show();
                    return;

                }

                try{
                    n_marks = Integer.parseInt(s_marks);
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this , "Marks should be valid integer" , Toast.LENGTH_SHORT).show();
                    return;
                }

                int num_rows_affected = dbHelper.updateStudentByRole(s_roll , s_name  , n_marks , s_result);

                if(num_rows_affected == 0) {
                    Toast.makeText(MainActivity.this, "No record found to update", Toast.LENGTH_SHORT).show();
                    dbHelper.addNewStudent(s_name , s_roll , n_marks , s_result);
                    return;
                }
                else {
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }

                name.setText("");
                rollNo.setText("");
                marks.setText("");
                result.setText("");

            }

        });



        TextView display_name , display_role , display_mark , display_result;
        display_name = findViewById( R.id.view_name);
        display_role = findViewById( R.id.view_roll);
        display_mark = findViewById( R.id.view_mark);
        display_result = findViewById( R.id.view_result);

        EditText get_roll = findViewById(R.id.get_roll);
        Button getStudent = findViewById(R.id.getStudent);





        getStudent.setOnClickListener( new View.OnClickListener() {

            @Override
            public  void  onClick(View V){

                String role = String.valueOf(get_roll.getText());
                get_roll.setText("");
                Map<String,String> result = dbHelper.getStudentByRole(role);

                if(result.isEmpty()){
                    Toast.makeText(MainActivity.this , "No data found" , Toast.LENGTH_SHORT).show();
                    return;
                }

                display_name.setText(result.get("name"));
                display_role.setText(result.get("role"));
                display_mark.setText(result.get("mark"));
                display_result.setText(result.get("result"));

                return;



            }
        });

        Button deleteStudent = findViewById(R.id.deleteStudent);
        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roll =  String.valueOf(get_roll.getText());

                int num_rows_affected = dbHelper.deleteStudentByRole(roll);

                if(num_rows_affected == 0) {
                    Toast.makeText(MainActivity.this, "No record found to delete", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }

               get_roll.setText("");

            }
        });


    }}