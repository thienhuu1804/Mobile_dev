package com.example.cookingebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class object_adapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<object> objectList;
    private TextView textView_NameProduct;
    private ImageView imageView_Product;
    private ImageView imageView_HighResProduct;


    public object_adapter(Context context, int layout, List<object> objectList) {
        this.context = context;
        this.layout = layout;
        this.objectList = objectList;
    }

    //@Override
    public int getCount() {
        return objectList.size();
    }

   // @Override
    public Object getItem(int position) {
        return null;
    }

    //@Override
    public long getItemId(int position) {
        return 0;
    }

    //@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView  =  layoutInflater.inflate(layout, null);

        textView_NameProduct = convertView.findViewById(R.id.textView_Product);
        imageView_Product = convertView.findViewById(R.id.imageView_Product);


        object product = objectList.get(position);
        textView_NameProduct.setText(product.getNameCooking());
        imageView_Product.setImageResource(product.getImageProduct());
        imageView_Product.setContentDescription(product.getImageProduct()+"");
        textView_NameProduct.setContentDescription(product.getUrl());


        return convertView;
    }



}
