package com.example.exchange_rate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private TextView outputText;
    private EditText inputText;
    private Spinner spinnerListPre, spinnerListAfter;

    private ArrayList<Currency> listCurrency = new ArrayList<>(); //danh sách tiền tệ
    private ArrayList<String> listNation = new ArrayList<>();
    private boolean isRunFisrt = true;
    private double exchangeRate = 1;
    private Currency pre = new Currency(), after = new Currency();
    NumberFormat formatCurrency = NumberFormat.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //tạo danh sách
        new Thread(new Runnable() {
            @Override
            public void run() {
                new HttpGetTask().execute("https://vnd.fxexchangerate.com/rss.xml");
            }
        }).start();
        outputText = (TextView) findViewById(R.id.outputText);
        outputText.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (savedInstanceState != null){
            outputText.setText(savedInstanceState.getString("textView"));
        }
    }

    private void initView(){ //ánh xạ các phần tử của giao diện
        inputText = (EditText) findViewById(R.id.inputText);
        outputText = (TextView) findViewById(R.id.outputText);
        spinnerListAfter = findViewById(R.id.spinnerListAfter);
        spinnerListPre = findViewById(R.id.spinnerListPre);

    }

    class HttpGetTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            return docDUlieu(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isRunFisrt){
                createListCurrency(s);
                isRunFisrt = false;
            } else getExchangeRate(s);

        }

    }

    private static String docDUlieu(String theURL){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(theURL);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }

            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private void getExchangeRate(String s){
        s = s.replace("</item>", "");
        String[] array = s.split("<item>");
        String input = "";

        //lấy ra phần tử tiền đích
        for (int i = 1; i < array.length; i++) {
            if( array[i].indexOf(after.getName()) != -1 ) {
                input = array[i];  //phần tử của mảng chứa tiền đích
                //Log.i(MainActivity.class.getSimpleName(), "ví trí : "+ i);
                break;
            }
        }

        try {
            //tạo định dạng cho XML
            XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
            XmlPullParser parserXML = fc.newPullParser();

            //chuyển string thành parserXML
            Reader reader = new StringReader(input);
            parserXML.setInput(reader);

            parserXML.next(); //đẩy parserXML đến tag title

            while(parserXML.getEventType() != XmlPullParser.END_DOCUMENT)//chạy cho đến kết thúc tài liệu
            {
                switch (parserXML.getEventType()) {
                    case XmlPullParser.START_TAG: //kiểm tra có phải là tag mở hay không
                        if (parserXML.getName().equals("description")){
                            parserXML.next(); //đẩy parser tới text

                            //lấy tỉ giá
                            String[] arrayNameAfter = after.getName().split("\\(");
                            exchangeRate = stringToExchangeRate(parserXML.getText(), arrayNameAfter[0]);// lấy tỉ giá
                        }
                        break;
                    default: //type là text hoặc là một tag đóng
                        parserXML.next();
                        break;
                }
                parserXML.next();
            }// end while
        } catch (XmlPullParserException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

//        textView.setText(pre.getName() + " = "+exchangeRate + " " + after.getName());

    }

    //lấy ra phần tỉ giá từ chuỗi String
    private double stringToExchangeRate(String strDes, String nameAfter ){
        int positionStart = strDes.indexOf("=") + 2; //lấy vị trí bắt đầu của tỉ giá trong chuỗi description
        int positionEnd = strDes.indexOf(nameAfter)-1; ////lấy vị trí kết thúc của tỉ giá trong chuỗi description
        //lấy subtring tỉ giá
        String result = strDes.substring(positionStart, positionEnd);

        //Log.i(MainActivity.class.getSimpleName(), result);
        return Double.valueOf(result);
    }

    private void createListCurrency(String s) {
        s = s.replace("</item>", "");
        String[] array = s.split("<item>");

        for (int i = 1; i < array.length; i++) {
            //Log.i(MainActivity.class.getSimpleName(), array[i]);
            try{
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                XmlPullParser parserXML = fc.newPullParser();

                //chuyển string thành parserXML
                Reader reader = new StringReader(array[i]);
                parserXML.setInput(reader);

                //xử lý tag title
                parserXML.next();
                if (parserXML.getEventType() == XmlPullParser.START_TAG) {
                    switch (parserXML.getName()) { //nếu đúng các tag cần thì lấy dữ liệu ra
                        case "title":
                            parserXML.next();
                            //chuyển parser đến text
                            listCurrency.add( createCurrency(parserXML.getText()) );
                            listNation.add(listCurrency.get(listCurrency.size()-1).getName());
                            break;
                        default:
                            parserXML.next();
                    }
                    parserXML.nextTag(); //chuyển parser đến tag đóng
                } else parserXML.nextTag();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        spinner();
    }

    private   void spinner(){
        //sắp xếp thứ tự chữ cái
        //tao spinner
        ArrayAdapter <String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listNation);
        spinnerListAfter.setAdapter(adapter);
        spinnerListPre.setAdapter(adapter);
        spinnerListAfter.setSelection(1);
        spinnerListPre.setSelection(2);
        spinnerListPre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pre = listCurrency.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new HttpGetTask().execute("https://"+pre.getCode()+".fxexchangerate.com/rss.xml");
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerListAfter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                after = listCurrency.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new HttpGetTask().execute("https://"+pre.getCode()+".fxexchangerate.com/rss.xml");
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private Currency createCurrency(String data){
        Currency result = new Currency();
        String []arrayList = data.split("/");
        result.setName(arrayList[1]);

        int indexPre = arrayList[1].indexOf("(");
        String code = arrayList[1].substring(indexPre+1, indexPre+4);
        result.setCode(code);

        return result;
    }

    public void convert(View view){
        //lấy số tiền nhập vào
        String input =inputText.getText().toString();
        if (input.length() == 0){
            return;
        }
        // String history = historyText.getText().toString();
        double outputValue;
        double inputValue = Double.valueOf(input).doubleValue();

        //lấy tỉ giá chuyển đổi


        //tính toán kq
        outputValue = (double) ((inputValue)*exchangeRate);

        outputText.setText(String.valueOf(formatCurrency.format(inputValue)) +" "+ pre.getCode()+" = "+String.valueOf(formatCurrency.format(outputValue))+" "+after.getCode()+"\n" );
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString("textView", outputText.getText().toString());
        super.onSaveInstanceState(outState);
    }
}// đong class
