package com.example.notepad2;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;


public class ReadWriteFileXML {

    DocumentBuilderFactory fac;
    DocumentBuilder builder;
    Document doc;
    ArrayList<NoteObject> noteList;
//    XmlPullParserFactory fc;
//    XmlPullParser parser;
    File file;
    Context context;
    String xmlFile;

    ReadWriteFileXML(String xmlFile, Context context){
        this.context = context;
        this.xmlFile = xmlFile;
        file = new File(this.context.getExternalFilesDir("/"),xmlFile);
    }

    public ArrayList<NoteObject> read(){
        noteList = new ArrayList<>();
        try{
            fac = DocumentBuilderFactory.newInstance();
            builder = fac.newDocumentBuilder();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            doc = builder.parse(inputStream);
            Element root = doc.getDocumentElement();//lay tag root
            NodeList list = root.getChildNodes();//Lay toan bo node con cua Root
            String datashow = "";
            Log.d("ele",root.toString());
            Log.d("list",list.toString());
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if(node instanceof Element){ //kiem tra node do co phai element?
                    Element note = (Element) node; // Lay tag "note" tuw xml
                    String title = note.getAttribute("title");
                    String date = note.getAttribute("date");
                    String time = note.getAttribute("time");
                    NoteObject obj = new NoteObject(title,"",date,time);

                    try{

                    NodeList listChild = note.getElementsByTagName("content");
                    String content = listChild.item(0).getTextContent();
                    obj.setContents(content);// lay noi dung cua tag content
                    datashow += title + "-" + content + "\n --------- \n";
                    Log.d("aha",datashow);
                    }catch (NullPointerException e){
                        Log.d("aa","kjkjk");
                        e.printStackTrace();
                    }
                    noteList.add(obj);
                }
            }
        } catch (IOException | SAXException e) {
            Log.d("file not found","file not found");
            e.printStackTrace();
        }

        return noteList;
    }

    public boolean write(ArrayList<NoteObject> notes, boolean append) {
        String data ="<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        data+= "\n<NotePad>";

//        if(!outputFile.isFile()){
//            try {
//                outputFile.getParentFile().mkdirs();
//                outputFile.createNewFile();
//            } catch (IOException e) {
//                Log.d("can","cant create file!!!");
//                e.printStackTrace();
//            }
//            string += "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
//        }
            try {
                File outputFile = new File(context.getExternalFilesDir("/"), xmlFile);
                FileOutputStream outputStream = new FileOutputStream(outputFile, append);
    //            Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                // vd: <note title='alada' date='22-11-2020' time='13:02' > <content> anababa </content> </note>
//                for(NoteObject n : noteList) {
                for(NoteObject note : notes){
                    data += "\n<note " + " title=\"" + note.getTitle() + "\""
                                        + " date=\"" + note.getDate() + "\""
                                        + " time=\"" + note.getTime() + "\"" + ">"
                                + "\n<content>" + note.getContents() + "</content>"
                            + "\n</note>";
//                }
                }
                data+= "\n</NotePad>";
                outputStream.write(data.getBytes());
                outputStream.flush();
                outputStream.close();

                Log.d("mytest", outputFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return true;
        }


    public ArrayList<NoteObject> getNotes(){
        return noteList;
    }

}
