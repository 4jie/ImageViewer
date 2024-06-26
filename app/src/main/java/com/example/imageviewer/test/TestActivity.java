package com.example.imageviewer.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imageviewer.R;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.util.ImageLoader;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private List<ImageItem> datalist;
    private int position;
    private HorizonSrollViewEx horizonSrollViewEx;
    private ImageLoader loader;
    private int screenWidth;
    private static final int IS_LOADED=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        LinearLayout layout=findViewById(R.id.layout);
        horizonSrollViewEx=new HorizonSrollViewEx(this);
        Intent intent=getIntent();


        loader=ImageLoader.build(this);

        datalist=(List<ImageItem>) intent.getSerializableExtra("datalist");
        Log.e("TAG", "onCreate: "+datalist.toString() );


        position=intent.getIntExtra("position",0);
        Log.e("TAG", "onCreate: position:"+position );



        for (int i = 0; i < datalist.size(); i++) {
            final String uri=datalist.get(i).getDownLoadUri();
            final ImageView view=new ImageView(this);
            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);



            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            view.setLayoutParams(layoutParams);

          //  loader.bindBitmap(uri,view,1080,0);
            horizonSrollViewEx.addView(view);


        }


            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            horizonSrollViewEx.setLayoutParams(params);

        screenWidth=getResources().getDisplayMetrics().widthPixels;
        Log.e("TAG", "onCreate: 屏幕宽度"+screenWidth );
        layout.addView(horizonSrollViewEx);


        horizonSrollViewEx.smoothScrollBy(position*screenWidth,0);
        preLoadImage(position);
    }

   private void preLoadImage(int position){
        loader.bindBitmap(datalist.get(position).getDownLoadUri(), (ImageView)horizonSrollViewEx.getChildAt(position),screenWidth,0);
        if(position-1>=0){
            ImageView view=(ImageView)horizonSrollViewEx.getChildAt(position-1);
            loader.bindBitmap(datalist.get(position-1).getDownLoadUri(), view
                   ,screenWidth,0);
            view.setTag(IS_LOADED);
        }

        ImageView view=(ImageView)horizonSrollViewEx.getChildAt(position+1);
        horizonSrollViewEx.setmChildIndex(position);
        loader.bindBitmap(datalist.get(position+1).getDownLoadUri(), view
                ,screenWidth,0);
        view.setTag(IS_LOADED);

    }



    public void checkAndPreloadingImage(int position,boolean left,boolean right){

        if(left&&right){
            return;
        }else if(left){
            Log.e("TAG", "checkAndPreloadingImage: left");
            if(position-1>=0){
                ImageView view=(ImageView)horizonSrollViewEx.getChildAt(position-1);
             //   if(view.getTag()!=null){
                    loader.bindBitmap(datalist.get(position-1).getDownLoadUri(), view
                            ,screenWidth,0);
                    view.setTag(IS_LOADED);
               // }
            }
            if(position-2>=0){
                ImageView view=(ImageView)horizonSrollViewEx.getChildAt(position-2);
              //  if(view.getTag()!=null){
                    loader.bindBitmap(datalist.get(position-2).getDownLoadUri(), view
                            ,screenWidth,0);
                    view.setTag(IS_LOADED);
               // }
            }

        }else if(right){
            Log.e("TAG", "checkAndPreloadingImage: right");
            if(position+1<datalist.size()){
                ImageView view=(ImageView)horizonSrollViewEx.getChildAt(position+1);
               // if(view.getTag()!=null){
                    loader.bindBitmap(datalist.get(position+1).getDownLoadUri(), view
                            ,screenWidth,0);
                    view.setTag(IS_LOADED);
               // }
            }
            if(position+2<datalist.size()){
                ImageView view=(ImageView)horizonSrollViewEx.getChildAt(position+2);
                    loader.bindBitmap(datalist.get(position+2).getDownLoadUri(), view
                            ,screenWidth,0);
                    view.setTag(IS_LOADED);
            }
        }
    }




}