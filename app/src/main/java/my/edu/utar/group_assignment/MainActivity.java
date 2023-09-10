package my.edu.utar.group_assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

//DONE BY OOI ZHENG YEE


public class MainActivity extends AppCompatActivity {

    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout recommendLayout = findViewById(R.id.recommend);
        final LinearLayout placesLayout = findViewById(R.id.places);
        final LinearLayout iteLayout = findViewById(R.id.itenarary);
        final LinearLayout docuLayout = findViewById(R.id.document);


        final ImageView recommendIMG = findViewById(R.id.recommendIMG);
        final ImageView placesIMG = findViewById(R.id.placesIMG);
        final ImageView iteIMG = findViewById(R.id.itenararyIMG);
        final ImageView docuIMG = findViewById(R.id.documentIMG);


        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer,RecommendFragment.class,null)
                .commit();

        recommendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 1){

                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,RecommendFragment.class,null)
                            .commit();

                    placesIMG.setImageResource(R.drawable.ic_outline_map_24);
                    iteIMG.setImageResource(R.drawable.ic_baseline_calendar_today_24);
                    docuIMG.setImageResource(R.drawable.ic_outline_checklist_24);


                    placesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    iteLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    docuLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    recommendIMG.setImageResource(R.drawable.ic_outline_favorite_border_24_selected);
                    recommendLayout.setBackground(getDrawable(R.drawable.circle_background));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0F,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    recommendLayout.startAnimation(scaleAnimation);

                    selectedTab=1;

//                    if (!placeIDList.isEmpty()){
//                        for(int i=0; i<placeIDList.size();i++){
//                            Log.i(TAG, "test "+placeIDList.get(i));
//                        }
//
//                    }
                }

            }
        });

        placesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 2){

                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,PlacesFragment.class,null)
                            .commit();

                    recommendIMG.setImageResource(R.drawable.ic_outline_favorite_border_24);
                    iteIMG.setImageResource(R.drawable.ic_baseline_calendar_today_24);
                    docuIMG.setImageResource(R.drawable.ic_outline_checklist_24);

                    recommendLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    iteLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    docuLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    placesIMG.setImageResource(R.drawable.ic_outline_map_24_selected);
                    placesLayout.setBackground(getDrawable(R.drawable.circle_background));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0F,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    placesLayout.startAnimation(scaleAnimation);

                    selectedTab=2;
                }


            }
        });

        iteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 3){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,IteneraryFragment.class,null)
                            .commit();

                    recommendIMG.setImageResource(R.drawable.ic_outline_favorite_border_24);
                    placesIMG.setImageResource(R.drawable.ic_outline_map_24);
                    docuIMG.setImageResource(R.drawable.ic_outline_checklist_24);


                    recommendLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    placesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    docuLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    iteIMG.setImageResource(R.drawable.ic_baseline_calendar_today_24_selected);
                    iteLayout.setBackground(getDrawable(R.drawable.circle_background));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0F,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    iteLayout.startAnimation(scaleAnimation);

                    selectedTab=3;
                }

            }
        });

        docuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTab != 4){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,DocumentFragment.class,null)
                            .commit();

                    recommendIMG.setImageResource(R.drawable.ic_outline_favorite_border_24);
                    placesIMG.setImageResource(R.drawable.ic_outline_map_24);
                    iteIMG.setImageResource(R.drawable.ic_baseline_calendar_today_24);


                    recommendLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    placesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    iteLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    docuIMG.setImageResource(R.drawable.ic_outline_checklist_24_selected);
                    docuLayout.setBackground(getDrawable(R.drawable.circle_background));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0F,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    docuLayout.startAnimation(scaleAnimation);

                    selectedTab=4;
                }

            }
        });


    }
}