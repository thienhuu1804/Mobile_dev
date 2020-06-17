package com.example.notepad2;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class Controller {
    ReadWriteFileXML readWriteFileXML;
    ArrayList<NoteObject> noteList;
    Context context;

    public Controller(Context context){
        this.context = context;
        readWriteFileXML = new ReadWriteFileXML("appData.xml",context);
        noteList = readWriteFileXML.read();
    }

    public void delete(int id){
        noteList.remove(id);
        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show();
        save(noteList);
    }

    public void add(NoteObject note){
        noteList.add(note);
        save(noteList);
    }

    public void edit(int id, NoteObject note){
        noteList.get(id).setTitle(note.getTitle());
        noteList.get(id).setContents(note.getContents());
        noteList.get(id).setTime(note.getTime());
        noteList.get(id).setDate(note.getDate());
        save(noteList);
    }

    public void save(ArrayList<NoteObject> noteObjects){
        readWriteFileXML.write(noteList,false);
        Toast.makeText(context,"writed!",Toast.LENGTH_LONG).show();
    }

    public void clone(int id){
        noteList.add(noteList.get(id));
        save(noteList);
    }

    public ArrayList<NoteObject> getNoteList(){
        return noteList;
    }
}
