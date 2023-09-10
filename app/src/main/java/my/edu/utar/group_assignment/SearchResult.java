package my.edu.utar.group_assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

//DONE BY LOH LE ZHOU


public class SearchResult extends AppCompatActivity {

    private View rootView;
    private PlacesClient placesClient;
    private ImageView imageView;
    private TextView placeNameTextView, clockTextView, phoneTextView,
            ratingTextView, webTextView;
    private CardView phoneCard, webCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String placeId = intent.getStringExtra("output");

        Places.initialize(this, "AIzaSyBgSfpLhWlC62P8kV67lG5tJB4-a1U2_dU");
        placesClient = Places.createClient(this);

        // Initialize view
        imageView = findViewById(R.id.place_photo_image_view);
        placeNameTextView = findViewById(R.id.place_name_text_view);
        clockTextView = findViewById(R.id.clockText);
        phoneTextView = findViewById(R.id.phoneText);
        ratingTextView = findViewById(R.id.ratingText);
        webTextView = findViewById(R.id.webText);
        ImageButton imageButton = findViewById(R.id.bk_btnasr);
        phoneCard = findViewById(R.id.phoneCard);
        webCard = findViewById(R.id.webCard);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final List<Place.Field> fields = Arrays.asList(
                Place.Field.PHOTO_METADATAS, Place.Field.NAME, Place.Field.RATING, Place.Field.OPENING_HOURS, Place.Field.PHONE_NUMBER, Place.Field.WEBSITE_URI
        );

        // Create a FetchPlaceRequest using the provided place ID and fields
        final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

        // Fetch the place using the request
        placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {

            final Place place = response.getPlace();

            // Display the retrieved place details
            placeNameTextView.setText(place.getName());

            if (place.getRating() != null) {
                ratingTextView.setText("Rating: " + place.getRating());
            }

            if (place.getOpeningHours() != null) {
                Log.i(TAG, "time: " + place.getOpeningHours().getWeekdayText());

                List<String> openingHours = place.getOpeningHours().getWeekdayText();
                if (openingHours != null ) {
                    for (int i=0; i<openingHours.size();i++){
                        clockTextView.append(openingHours.get(i)+"\n\n");
                    }

                } else {
                    clockTextView.setText("N/A");
                }
            }
            else {
                clockTextView.setText("N/A");
            }

            if (place.getPhoneNumber() != null) {
                final String phoneNumber = place.getPhoneNumber();
                phoneCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Phone Number", phoneNumber);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(v.getContext(), "Phone number copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                phoneTextView.setText("N/A");
            }

            if (place.getWebsiteUri() != null) {
                webTextView.setMovementMethod(LinkMovementMethod.getInstance());

                // Optionally, you can also set a custom click listener to handle the click event
                webCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, place.getWebsiteUri());
                        startActivity(intent);
                    }
                });
            } else {
                webTextView.setText("N/A");
            }

            // Get the photo metadata
            final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
            if (metadata == null || metadata.isEmpty()) {
                Log.w(TAG, "No photo metadata.");
                return;
            }

            // Get the first photo metadata
            final PhotoMetadata photoMetadata = metadata.get(0);

            // Create a FetchPhotoRequest.
            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(500) // Optional.
                    .setMaxHeight(300) // Optional.
                    .build();

            // Fetch the photo
            placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap bitmap = fetchPhotoResponse.getBitmap();
                imageView.setImageBitmap(bitmap); // Set the retrieved photo to ImageView
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    final ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place photo fetch failed: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    // TODO: Handle error with given status code.
                }
            });
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                final ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place details fetch failed: " + exception.getMessage());
                final int statusCode = apiException.getStatusCode();
                // TODO: Handle error with given status code.
            }
        });
    }
}