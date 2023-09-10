package my.edu.utar.group_assignment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//DONE BY OOI ZHENG YEE & LOH LE ZHOU


public class RecommendFragment extends Fragment {
    private PlacesClient placesClient;
    OkHttpClient client;
    TextView tv;
    String url;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        Places.initialize(getContext(), "AIzaSyBgSfpLhWlC62P8kV67lG5tJB4-a1U2_dU");
        placesClient = Places.createClient(requireContext());
        client = new OkHttpClient();

        //SearchBar Function
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment2);
        autocompleteFragment.getView().setBackground(getResources().getDrawable(R.drawable.searchbar));
        autocompleteFragment.setHint("Enter a place name");

        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                autocompleteFragment.setText("");
                // Handle the selected place
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                String placeId = place.getId();
                Intent intent2 = new Intent(getActivity(), SearchResult.class);
                intent2.putExtra("output",placeId);
                startActivity(intent2);

            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle error
                Log.e("PlacesFragment", "An error occurred: " + status);
            }
        });

        //Category function

        CardView nacv = view.findViewById(R.id.na);
        CardView sacv = view.findViewById(R.id.sa);
        CardView eucv = view.findViewById(R.id.eu);
        CardView ascv = view.findViewById(R.id.as);
        CardView occv = view.findViewById(R.id.oc);
        CardView afcv = view.findViewById(R.id.af);
        CardView mecv = view.findViewById(R.id.me);
        nacv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/north-america?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });
        sacv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/latin-america?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });
        eucv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/europe?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });
        ascv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/asia?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });
        occv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/oceania?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });
        afcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/africa?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });
        mecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url =  "https://nomad-list-cities.p.rapidapi.com/nomad-list/middle-east?size=10&page=1&sort=desc&sort_by=overall_score";
                get(url);

            }
        });

        //popular section
        //initialized view
        CardView syd = view.findViewById(R.id.sydney);
        CardView bali = view.findViewById(R.id.bali);
        CardView mal = view.findViewById(R.id.maldives);
        CardView chn = view.findViewById(R.id.china);
// response after button click
        mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), SearchResult.class);
                intent2.putExtra("output","ChIJTXE77EKEPzsRDdplrdNjAWE");
                startActivity(intent2);

            }
        });
        bali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), SearchResult.class);
                intent2.putExtra("output","ChIJgzbF1ag30i0RNfGRNGNYQS8");
                startActivity(intent2);

            }
        });
        syd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), SearchResult.class);
                intent2.putExtra("output","ChIJ3S-JXmauEmsRUcIaWtf4MzE");
                startActivity(intent2);

            }
        });
        chn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), SearchResult.class);
                intent2.putExtra("output","ChIJzyx_aNch8TUR3yIFlZslQNA");
                startActivity(intent2);

            }
        });



        return view;
    }

    public void get(String url){
//call API
        Request request = new Request.Builder().url(url)
                .get()
                .addHeader("X-RapidAPI-Key", "21aa5fbe30mshff523529cc02a03p186bdajsn6ac51c1d7c62")
                .addHeader("X-RapidAPI-Host", "nomad-list-cities.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //get response and pass it to the next page
                            String result = response.body().string();
                            Intent intent = new Intent(getActivity(), NorthAmerica.class);
                            intent.putExtra("output",result);
                            startActivity(intent);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}