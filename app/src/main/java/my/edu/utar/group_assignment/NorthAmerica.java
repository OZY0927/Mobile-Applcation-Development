package my.edu.utar.group_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//DONE BY OOI ZHENG YEE

public class NorthAmerica extends AppCompatActivity {
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north_america);
//       get result from previous page and initialize view
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("output");
        RecyclerView courseRV = findViewById(R.id.idRVPlace);
        ArrayList<PopularPlaces> popularPlacesArrayList = new ArrayList<PopularPlaces>();

        ImageButton imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Parse the JSON string
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String city = item.getString("name");
                String country = item.getString("country");
                String imgUrl = item.getString("image");
                String description = item.getString("descriptionFromReview");

                if (description == "null"){
                    description= "No description for this place.";
                }

                // Do something with the temperature value, like displaying it
                popularPlacesArrayList.add(new PopularPlaces(city, country, imgUrl, description));
            }
        } catch (JSONException e) {
            popularPlacesArrayList.add(new PopularPlaces("Nothing to display", "", "https://www.tripsavvy.com/thmb/DnTMIADvI4AZwsnKZhaAyuo9wok=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/penang-malaysia-b40c38589e794a61ba904d64c0a02c43.jpg", "NA"));
        }

        AdapterClass adapterClass = new AdapterClass(this, popularPlacesArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(adapterClass);

    }



}