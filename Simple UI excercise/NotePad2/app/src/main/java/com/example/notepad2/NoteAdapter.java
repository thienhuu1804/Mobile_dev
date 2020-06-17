package com.example.notepad2;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    LayoutInflater inflater;
    ArrayList<NoteObject> list;
    Context context;
    Controller controller;

    NoteAdapter(Context context, ArrayList<NoteObject> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        controller = new Controller(context);
    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        String title = list.get(position).getTitle();
        String date = list.get(position).getDate();
        String time = list.get(position).getTime();
        holder.title.setText(title);
        holder.date.setText(date);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,time,moreBtn;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            moreBtn = itemView.findViewById(R.id.more);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),Details.class);
                    i.putExtra("objList",list);
                    i.putExtra("id", getAdapterPosition());
                    context.startActivity(i);

                }
            });

            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,moreBtn);
                    popupMenu.inflate(R.menu.context_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.context_menu_edit:{
                                    Intent i = new Intent(context,Details.class);
                                    i.putExtra("id", getAdapterPosition());
                                    context.startActivity(i);
                                    return true;
                                }
                                case R.id.context_menu_delete:{
                                    //create alert dialoge when try to delete
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Delete note");
                                    builder.setMessage("Are you sure?");
                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            controller.delete(getAdapterPosition());
                                            Activity a = getActivity(context);
                                            a.finish();
                                            a.overridePendingTransition(0, 0);
                                            a.startActivity(a.getIntent());
                                            a.overridePendingTransition(0, 0);
                                            Toast.makeText(context,   "Deleted "+list.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });

                                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    return true;
                                }
                                case R.id.context_menu_clone:{
                                    controller.clone(getAdapterPosition());
                                    Activity a = getActivity(context);
                                    a.finish();
                                    a.overridePendingTransition(0, 0);
                                    a.startActivity(a.getIntent());
                                    a.overridePendingTransition(0, 0);
                                    return true;
                                }
                                default: return false;
                            }

                        }
                    });

                    popupMenu.show();
                }
            });

        }
    }
    public Activity getActivity(Context context) {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

}
