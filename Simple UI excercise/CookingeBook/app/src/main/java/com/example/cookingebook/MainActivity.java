package com.example.cookingebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewObject;
    private object_adapter objectAdapter;
    private ArrayList<object> arrayListObject;
    private TextView textViewHighResProduct;
    private ImageView imageViewHighResProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewObject = findViewById(R.id.listView_Product);
        textViewHighResProduct = (TextView) findViewById(R.id.textView_HighResProduct);
        imageViewHighResProduct = (ImageView) findViewById(R.id.imageView_HighResProduct);

        //lấy mảng tên từ strings.xml
        String [] nameCooking = getResources().getStringArray(R.array.nameCooking);

        //lấy mảng url từ string.xml
        String [] urlCooking = getResources().getStringArray(R.array.urlCooking);

        // tạo mảng object
        arrayListObject = new ArrayList<>();

        createArrayObject(nameCooking, urlCooking);

        //đổ adapter cho listView
        objectAdapter = new object_adapter(this, R.layout.activity_row_cooking, arrayListObject);
        listViewObject.setAdapter(objectAdapter);
        objectAdapter.notifyDataSetChanged();

        listViewObject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // hiển thị vị trí đã lấy từ listView
                //Toast.makeText( getApplicationContext(), arrayListObject.get(position).getUrl() +"" , Toast.LENGTH_SHORT).show();

                //truyền dữ liệu qua intent
                Intent intent = new Intent(MainActivity.this, webView.class);
                intent.putExtra("dataURL", arrayListObject.get(position).getUrl() +"");

                //chuyển activity khác
                startActivity(intent);
            }
        });

    }

    public void createArrayObject (String [] nameCooking, String [] urlCooking){
        arrayListObject.add(new object( nameCooking[0], urlCooking[0], R.drawable.kanelbullar_swedish_cinnamon_buns_recipe));
        arrayListObject.add(new object( nameCooking[1], urlCooking[1], R.drawable.baked_eggplant_parmesan_recipe));
        arrayListObject.add(new object( nameCooking[2], urlCooking[2], R.drawable.italian_sausage_and_orzo_soup));
        arrayListObject.add(new object( nameCooking[3], urlCooking[3], R.drawable.spaghetti_zucchini_noodles_with_basil_walnut_pesto));
        arrayListObject.add(new object( nameCooking[4], urlCooking[4], R.drawable.spiralized_zucchini_nest_eggs));
        arrayListObject.add(new object( nameCooking[5], urlCooking[5], R.drawable.baked_spiralized_butternut_squash_fries));
        arrayListObject.add(new object( nameCooking[6], urlCooking[6], R.drawable.skinny_chicken_parmesan_pasta_bake));
        arrayListObject.add(new object( nameCooking[7], urlCooking[7], R.drawable.mediterranean_chicken_pasta_bake));
        arrayListObject.add(new object( nameCooking[8], urlCooking[8], R.drawable.slow_cooker_lasagna));
        arrayListObject.add(new object( nameCooking[9], urlCooking[9], R.drawable.seafood_paella));
        arrayListObject.add(new object( nameCooking[10], urlCooking[10], R.drawable.squash_and_cucumber_marinated_salad));
    }


}
