package my.edu.utar.group_assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//DONE BY OOI ZHENG YEE & WU JIAN WEI

public class IteneraryDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_itenerary_details);
        TextView detailTextView = findViewById(R.id.detail_textview);
        TextView nameTextView = findViewById(R.id.place_name_text_view);
        TextView daysTV = findViewById(R.id.days_textview);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonited);

        //get api response from previous fragment
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("output");
        // Parse the JSON string
        try{
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(jsonString).getAsJsonObject();
            //split the word
            String dayplace = jsonObject.get("key").getAsString();
            String[] dayplacesplit = dayplace.split("-");

            nameTextView.setText(dayplacesplit[1].toUpperCase());
            daysTV.setText(dayplacesplit[0]+ " Days Trip");


            String plan_details = new String();

            // Retrieve and display each activity and its related time and description for each day
            JsonArray plan = jsonObject.getAsJsonArray("plan");
            for (int i = 0; i < plan.size(); i++) {
                JsonObject dayObj = plan.get(i).getAsJsonObject();
                int day = dayObj.get("day").getAsInt();
                detailTextView.append("Day " + day + "\n");

                JsonArray activities = dayObj.getAsJsonArray("activities");
                for (int j = 0; j < activities.size(); j++) {
                    JsonObject activityObj = activities.get(j).getAsJsonObject();
                    String time = activityObj.get("time").getAsString();
                    String description = activityObj.get("description").getAsString();
                    detailTextView.append(time+ "  "+description + "\n");

                    if(j == 0 )
                    {
                        plan_details +=  "Day " + (i+1) + "\n\n";
                    }
                    plan_details += time + "  "+ description + "\n";

                }
                plan_details += "\n";
            }
            String finalResult = plan_details;
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PostData thread = new PostData(dayplacesplit[1].toUpperCase(), finalResult);
                    thread.start();

                    try {
                        thread.join(); // Wait for the thread to finish
                    } catch (InterruptedException e) {
                        // Handle the interruption if needed
                    }

                    Toast.makeText(IteneraryDetails.this, "Itinerary Saved", Toast.LENGTH_SHORT).show();
                    finish();
//                Intent intentToItinerary = new Intent(IteneraryDetails.this,IteneraryFragment.class);
//                startActivity(intentToItinerary);
                }
            });
        }catch (Exception e){
            Toast.makeText(IteneraryDetails.this, "Failed to Display", Toast.LENGTH_SHORT).show();
        }
    }


    private class PostData extends Thread
    {
        private String placeName;
        private String planDetail;

        public PostData(String placeName, String planDetail)
        {
            this.placeName = placeName;
            this.planDetail = planDetail;
        }

        public void run()
        {
            try {
                URL url = new URL("https://niofprleiivsaehorsjr.supabase.co/rest/v1/Itinerary");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("apikey",getString(R.string.SUPABASE_KEY));
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + getString(R.string.SUPABASE_KEY));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("place_name",placeName);
                jsonObject.put("plan_details",planDetail);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Property","return=minimal");
                httpURLConnection.setDoOutput(true);

                OutputStream output = httpURLConnection.getOutputStream();
                output.write(jsonObject.toString().getBytes());
                output.flush();

                int code = httpURLConnection.getResponseCode();
                Log.i("Response Code","code " + code);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}