package com.rubik.appimagegallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

        /**
             * Created by Rubik on 30/6/16.
             * Adapter of the image Gallery to select a SINGLE item by SharedPreferences
                -> Select the item by touch or when the page of ViewPager changes.
         */
    public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {
        private Context context;
        public  List<Images> listImages;
        private SharedPreferences sharedPref;


        public MyRecycleViewAdapter (MainActivity mainActivity, List<Images> listImages ) {
            this.context = mainActivity.getApplicationContext();
            sharedPref = context.getSharedPreferences("image", Context.MODE_PRIVATE); //init shared pref
            this.listImages = listImages;
        }

             @Override
        public MyRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_gallery_layout, parent, false);
            return new MyViewHolder(itemView);
        }

             @Override
        public void onBindViewHolder(final MyRecycleViewAdapter.MyViewHolder holder, final int position) {
                 // Change the barckground of the borderLayout (the background of the img) if is select.
            if (getImage(position).isSelected()) {
                holder.borderLayout.setBackgroundColor(Color.parseColor("#cc1974de"));
            } else {
                holder.borderLayout.setBackgroundColor(Color.TRANSPARENT);
            }

            Picasso.with(context.getApplicationContext()).load(getImage(position).getUrl()).resize(120, 120).into(holder.imageView);
        }

        private Images getImage(int position) {
            return listImages.get(position);
        }

        public void setSelected(int pos) {
            try {
                if (listImages.size() > 1) {
                    getImage(sharedPref.getInt("position", 0)).setSelected(false);
                    SharedPreferences.Editor editor = sharedPref.edit(); // open the editor
                    editor.putInt("position", pos); // put the position of item selected
                    editor.apply(); // apply changes
                }
                getImage(pos).setSelected(true); // select the item
                notifyDataSetChanged(); //update adapter
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



            @Override
        public long getItemId(int position) {return listImages.get(position).getIdImage();}

            @Override
        public int getItemCount() {return listImages.size();}


    public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            LinearLayout borderLayout;

            public MyViewHolder(View view) {
                super(view);

                imageView = (ImageView) view.findViewById(R.id.thumbnail);
                borderLayout = (LinearLayout) view.findViewById(R.id.linearLayoutBoder);
            }
        }


    }
