package com.rubik.appimagegallery;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rubik.appimagegallery.SQLiteManager.SQLiteHandler;
import com.rubik.appimagegallery.SQLiteManager.SQLiteManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static RecyclerView recyclerView;
    private MyRecycleViewAdapter myAdapter;
    private ViewPager viewPager;
    private List<Images> listImages;

    public static int pagerPosition = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();

        listImages = getListImages();
        InitViewPager();
        initRecycleView();
        closeGallery();

    }

    private List<Images> getListImages() {
        ImagesSQL imagesSQL = new ImagesSQL();
        return imagesSQL.getAllImage();
    }

    private void InitViewPager () {
        viewPager = (ViewPager) findViewById(R.id.pager);
        final MyViewPagerAdapter pagerAdapte = new MyViewPagerAdapter(this,listImages);
        viewPager.setAdapter(pagerAdapte);
        viewPager.setOffscreenPageLimit(listImages.size());
        setCurrentItem(pagerPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                displayImageInfo(position); //  Dysplay Text ant pos of the image selected
                pagerPosition = position;   //
                recyclerView.scrollToPosition(position); //scroolling the recicleView to selected posisiton

                myAdapter.setSelected(position);    // select the position of the page selecion in the galley with the sharedPref
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private void displayImageInfo(int position) {
        TextView lblCount = (TextView) findViewById(R.id.count);
        TextView lblTittle = (TextView) findViewById(R.id.titleImg);

        lblCount.setText((position + 1) + " of " + listImages.size());
        lblTittle.setText(listImages.get(position).getName());
    }

    public void setCurrentItem(int position) {
        viewPager.setCurrentItem(position,true);
        displayImageInfo(pagerPosition);
    }

    private void closeGallery() {
        ImageButton close = (ImageButton) findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyRecycleViewAdapter(this, listImages);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayout.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            myAdapter.setSelected(position);
                            viewPager.setCurrentItem(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
        myAdapter.notifyDataSetChanged();
    }



    private void initDatabase () {
        SQLiteHandler sqlHandler = new SQLiteHandler(getApplicationContext());
        SQLiteManager.initialize(sqlHandler);
        if (SQLiteManager.isDbEmmty("idImg", ImagesSQL.IMG_TABLE)) {
            initTestData();  //TODO : TEST

        }
    }
    private void initTestData() {

        ImagesSQL imagesSQL = new ImagesSQL();
        imagesSQL.addImage(new Images("The Wolf of Wall Street", "http://ia.media-imdb.com/images/M/MV5BMjIxMjgxNTk0MF5BMl5BanBnXkFtZTgwNjIyOTg2MDE@._V1_SY1000_CR0,0,674,1000_AL_.jpg"));
        imagesSQL.addImage(new Images("Captain America: Civil War", "http://www.cosmicbooknews.com/sites/default/files/imagecache/node-gallery-display/civilwarposterimh.jpg"));
        imagesSQL.addImage(new Images("The Dark Knight", "http://pics.filmaffinity.com/the_dark_knight-102763119-large.jpg"));
        imagesSQL.addImage(new Images("Shutter Island", "http://ia.media-imdb.com/images/M/MV5BMTMxMTIyNzMxMV5BMl5BanBnXkFtZTcwOTc4OTI3Mg@@._V1_.jpg"));
        imagesSQL.addImage(new Images("The Revenant", "http://ia.media-imdb.com/images/M/MV5BMjU4NDExNDM1NF5BMl5BanBnXkFtZTgwMDIyMTgxNzE@._V1_SY1000_CR0,0,674,1000_AL_.jpg"));
        imagesSQL.addImage(new Images("Suicide Squad", "http://ia.media-imdb.com/images/M/MV5BMTAyMTA5NTEyMzNeQTJeQWpwZ15BbWU4MDE1MTk5Mjkx._V1_SY1000_SX675_AL_.jpg"));
        imagesSQL.addImage(new Images("Memento", "http://ia.media-imdb.com/images/M/MV5BMTc4MjUxNDAwN15BMl5BanBnXkFtZTcwMDMwNDg3OA@@._V1_.jpg"));
        imagesSQL.addImage(new Images("Inception", "http://ia.media-imdb.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SY1000_CR0,0,675,1000_AL_.jpg"));
        imagesSQL.addImage(new Images("Star Wars: Episode VII - The Force Awakens", "http://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SY1000_CR0,0,677,1000_AL_.jpg"));


        imagesSQL.addImage(new Images("Game of Thrones", "http://ia.media-imdb.com/images/M/MV5BMjM5OTQ1MTY5Nl5BMl5BanBnXkFtZTgwMjM3NzMxODE@._V1_SY1000_CR0,0,674,1000_AL_.jpg,"));
        imagesSQL.addImage(new Images("Breaking Bad", "http://ia.media-imdb.com/images/M/MV5BMTQ0ODYzODc0OV5BMl5BanBnXkFtZTgwMDk3OTcyMDE@._V1_SY1000_CR0,0,678,1000_AL_.jpg,"));
        imagesSQL.addImage(new Images("The Wire","http://ia.media-imdb.com/images/M/MV5BNjc1NzYwODEyMV5BMl5BanBnXkFtZTcwNTcxMzU1MQ@@._V1_SY1000_CR0,0,735,1000_AL_.jpg"));
        imagesSQL.addImage(new Images("The Leftovers", "http://ia.media-imdb.com/images/M/MV5BMTQ4MzEzNTAxOF5BMl5BanBnXkFtZTgwMjQ1MTY3NjE@._V1_.jpg"));
        imagesSQL.addImage(new Images("Mr. Robot", "http://ia.media-imdb.com/images/M/MV5BMTYzMDE2MzI4MF5BMl5BanBnXkFtZTgwNTkxODgxOTE@._V1_SY1000_CR0,0,674,1000_AL_.jpg"));
        imagesSQL.addImage(new Images("Fargo", "http://ia.media-imdb.com/images/M/MV5BNDEzOTYzMDkzN15BMl5BanBnXkFtZTgwODkzNTAyNjE@._V1_.jpg"));
        imagesSQL.addImage(new Images("Better Call Saul", "http://ia.media-imdb.com/images/M/MV5BNjk5MjYwNjg4NV5BMl5BanBnXkFtZTgwNzAzMzc5NzE@._V1_.jpg"));


        imagesSQL.addImage(new Images("Nissan 370z","http://es.hdwall365.com/wallpapers/1604/Nissan-370Z-coupe-orange-car-rear-view_m.jpg"));
        imagesSQL.addImage(new Images("Camaro SS","http://media.caranddriver.com/images/15q3/660572/2016-chevrolet-camaro-ss-manual-first-drive-review-car-and-driver-photo-661947-s-429x262.jpg"));
        imagesSQL.addImage(new Images("Aston Martin Vanquish","http://www.ridelust.com/wp-content/uploads/Aston_Martin-DBS_Infa_Red_2008_1280x960_wallpaper_01.jpg"));

        imagesSQL.addImage(new Images("Leonardo DiCaprio","http://media3.popsugar-assets.com/files/2016/02/29/638/n/1922398/2845304c_edit_img_cover_file_17268548_1456755329_GettyImages-51UmW1V1.xxxlarge/i/Leonardo-DiCaprio-Oscars-Memes-2016.jpg"));
        imagesSQL.addImage(new Images("Margot Robbie","http://eva.hn/wp-content/uploads/2015/08/Margot-Robbie-Wolf-Wall-Street.jpg"));
        imagesSQL.addImage(new Images("Tom Hardy","http://www.mtv.co.uk/sites/default/files/styles/image-w-520-h-520-scale/public/mtv_uk/galleries/large/2015/05/13/11-tom-hardy-gallery-fb.jpg?itok=hqfsf16t"));
        imagesSQL.addImage(new Images("Scarlett Johansson","http://www.celebs101.com/gallery/Scarlett_Johansson/201825/allthatgossip_Scarlett_Johansson_GoldenGlobe_01.jpg"));
        imagesSQL.addImage(new Images("Blake Lively","http://www.speakerscorner.me/wp-content/uploads/2016/03/blake1.jpg"));
        imagesSQL.addImage(new Images("Cristian Bale","http://www.lahiguera.net/cinemania/actores/christian_bale/fotos/16700/christian_bale.jpg"));

        imagesSQL.addImage(new Images("Suicide Squad - Jared Leto 1","http://www.joblo.com/posters/images/full/Suicide-Squad-character-poster-10.jpg"));
        imagesSQL.addImage(new Images("Suicide Squad -  Margot Robbie 1","http://www.joblo.com/posters/images/full/Suicide-Squad-character-poster-2-6.jpg"));
        imagesSQL.addImage(new Images("Suicide Squad - Jared Leto 2","http://www.sinuousmag.com/sm/wp-content/uploads/2016/01/suicide-squad-2016-jared-leto-as-joker-poster-1b-650x963.jpg"));
        imagesSQL.addImage(new Images("Suicide Squad -  Margot Robbie 2","http://www.joblo.com/posters/images/full/Suicide-Squad-Character-Poster-1.jpg"));

        Log.d(TAG, " Imagenes Insertadas");

    }
}
