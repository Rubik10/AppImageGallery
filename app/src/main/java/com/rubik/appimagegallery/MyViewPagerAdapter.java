package com.rubik.appimagegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.List;

        /**
         * Created by Rubik on 30/6/16.
         */
    public class MyViewPagerAdapter extends PagerAdapter {

        private MainActivity activity;
        private List<Images> listImages;
        private LayoutInflater inflater;

        public MyViewPagerAdapter (MainActivity mainActivity, List<Images> listImages) {
            this.activity = mainActivity;
            inflater = (LayoutInflater) mainActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.listImages = listImages;
        }

            @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            final SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.image);

                // Asynchronously load the image and set the thumbnail of pager view with the Glide lib
            Glide.with(activity.getApplicationContext())
                    .load(listImages.get(position).getUrl())
                    .asBitmap()
                    .thumbnail(0.5f)
                    .into(new SimpleTarget<Bitmap>() {
                            @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                        }
                    });

            MainActivity.pagerPosition = position; // update the pager position

            imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {
                   // Intent to Full Image
                }
            });

            return itemView;
        }

            @Override
        public int getCount() {
            return listImages.size();
        }

            @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

            @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }


}
