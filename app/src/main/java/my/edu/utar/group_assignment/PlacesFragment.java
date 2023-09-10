package my.edu.utar.group_assignment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//DONE BY OOI ZHENG YEE


public class PlacesFragment extends Fragment {

    private View rootView;
    OkHttpClient client;

    String url;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_places, container, false);
// initialized view
        TextView country = rootView.findViewById(R.id.country);
        TextView place = rootView.findViewById(R.id.place);
        TextView days = rootView.findViewById(R.id.days);
        Button submit = rootView.findViewById(R.id.itebtn);
        client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);
        client = builder.build();

// respond after button click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(country.getText().toString().isEmpty() || place.getText().toString().isEmpty()|| days.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Fill Up All The Details", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Place: " + country.getText());
                    return;
                }
                //show loading element
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Generating Itinerary..."); // Setting Message
                progressDialog.setTitle("Loading"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                url = "https://ai-trip-planner.p.rapidapi.com/?days="+days.getText().toString()+"&destination="+place.getText().toString()+"%2C"+country.getText().toString();
                get(url);


            }
        });



        return rootView;
    }
    public void get(String url){
//call API
        Request request = new Request.Builder().url(url)
                .get()
                .addHeader("X-RapidAPI-Key", "41781a2080msh1f24d31849a0f9cp1e3a5fjsn691af09fcfed")
                .addHeader("X-RapidAPI-Host", "ai-trip-planner.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //get API response and pass it to the next activity
                            String result = response.body().string();
                            Intent intent = new Intent(getActivity(), IteneraryDetails.class);
                            intent.putExtra("output",result);
                            progressDialog.dismiss();
                            startActivity(intent);

                        } catch (IOException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }
                    }
                });
            }
        });
    }

}


