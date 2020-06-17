package com.example.notepad2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Details extends AppCompatActivity {
    TextView content, savedTime, title;
    ArrayList<NoteObject> noteObjects;
    int id;
    Controller controller;
    boolean saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        controller = new Controller(this);


        content = findViewById(R.id.content);
        savedTime = findViewById(R.id.savedTime);
        title = findViewById(R.id.noteTitle);

//        view.findViewById();

        noteObjects = new ArrayList<>();
//        noteObjects = i.getParcelableArrayListExtra("objList");
        noteObjects = controller.getNoteList();


        Intent i = getIntent();
        id = i.getIntExtra("id",0);
        getSupportActionBar().setTitle(noteObjects.get(id).getTitle());
        content.setText(noteObjects.get(id).getContents());
        title.setText(noteObjects.get(id).getTitle());
        content.setMovementMethod(new ScrollingMovementMethod());
        savedTime.setText("Last modified: "+noteObjects.get(id).getDate()+"  "+noteObjects.get(id).getTime());

//        Toast.makeText(this, noteObjects.get(id).getTitle(), Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){
            saved = save();
        }else if(item.getItemId() == android.R.id.home){
            saved = save();
//            Toast.makeText(this,"Back button",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean save(){
        if(title.getText().toString().trim().length() != 0){

            //get current date and time
            Calendar c = Calendar.getInstance();
            String currentDate = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" +c.get(Calendar.DAY_OF_MONTH);
            String currentTime = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);

            NoteObject newNote = new NoteObject();
            newNote.setTitle(title.getText().toString());
            newNote.setContents(content.getText().toString());
            newNote.setDate(currentDate);
            newNote.setTime(currentTime);

            Intent intent = new Intent(this,MainActivity.class);
            controller.edit(id,newNote);
            startActivity(intent);
            return true;
        } else{
            Toast.makeText(this, "Title can't be empty !", Toast.LENGTH_LONG).show();
            return false;
        }
    }

//    @Override
//    public void onBackPressed() {
//        boolean saved = save();
//        if(saved)
//            Toast.makeText(this,"auto saved!",Toast.LENGTH_LONG).show();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if(saved)
            Toast.makeText(this,"Saved!",Toast.LENGTH_LONG).show();
        else{
            save();
        }
    }
}
