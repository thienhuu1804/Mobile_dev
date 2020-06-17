package com.example.landmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewLandmarks;
    private ArrayList<Landmark> arrayListLandmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewLandmarks = findViewById(R.id.listViewLandmarks);

        arrayListLandmark = new ArrayList<>();
        arrayListLandmark.add(new Landmark("1. Cleveland Tower City", "230 W Huron Rd, Cleveland, OH 44113, Hoa Kỳ","towercitycenter.com"));
        arrayListLandmark.add(new Landmark("2. Browns Football Field", "100 Alfred Lerner Way, Cleveland, OH 44114, Hoa Kỳ", "firstenergystadium.com"));
        arrayListLandmark.add(new Landmark("3. Cleveland State University", "2121 Euclid Ave, Cleveland, OH 44115, Hoa Kỳ", "csuohio.edu"));
        arrayListLandmark.add(new Landmark("4. Playhouse Square", "1501 Euclid Ave #200, Cleveland, OH 44115, Hoa Kỳ", "playhousesquare.org"));
        arrayListLandmark.add(new Landmark("5. Art Museum", "2600 Benjamin Franklin Pkwy, Philadelphia, PA 19130, Hoa Kỳ", "philamuseum.org"));
        arrayListLandmark.add(new Landmark("6. SGU", "273 An Dương Vương, Phường 3, Quận 5, Hồ Chí Minh 700000, Việt Nam", "sgu.edu.vn"));

        String arrayName[] = new String[arrayListLandmark.size()];
        for (int i=0; i < arrayListLandmark.size(); i++){
            arrayName[i] = arrayListLandmark.get(i).getName();
        }

        //khởi tạo adapter
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,
                                                android.R.layout.simple_list_item_activated_1,
                                                arrayName);
        listViewLandmarks.setAdapter(adapter); //hiển thị danh sách
        adapter.notifyDataSetChanged();

        listViewLandmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //lấy đối tượng chọn
                Landmark temp = arrayListLandmark.get(position);

               //chuyển activity và truyền dữ liệu
                Intent newActivity = new Intent(MainActivity.this, Main2Activity.class);
                newActivity.putExtra("dataMap", temp.getLocal());
                newActivity.putExtra("dataMoreInfo", temp.getUrl());

                startActivity(newActivity);
            }
        });
    }
}
