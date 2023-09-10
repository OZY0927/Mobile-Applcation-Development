package my.edu.utar.group_assignment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//DONE BY WU JIAN WEI

public class IteneraryFragment extends Fragment {

    RecyclerView recyclerView;
    ItineraryAdapter recycleViewAdapter;
    ArrayList<Itinerary> itineraryList = new ArrayList<>();
    Handler handler = new Handler();

    public IteneraryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_itenerary, container, false);

        recyclerView = rootView.findViewById(R.id.itineraryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recycleViewAdapter = new ItineraryAdapter(getContext(),itineraryList);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recycleViewAdapter);

        // retrieve data from database
        RetrieveData thread = new RetrieveData();
        thread.start();

        return rootView;
    }

    private class RetrieveData extends Thread
    {
        public void run()
        {
            try {
                URL url = new URL("https://niofprleiivsaehorsjr.supabase.co/rest/v1/Itinerary?select=*");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("apikey",getString(R.string.SUPABASE_KEY));
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + getString(R.string.SUPABASE_KEY));

                InputStream input = httpURLConnection.getInputStream();
                String result = readStream(input);
                JSONArray jsonArray = new JSONArray(result);

                String placeName;

                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String planDetail = new String();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    placeName = jsonObject.getString("place_name");
                    planDetail = jsonObject.getString("plan_details");

                    itineraryList.add(new Itinerary(placeName,planDetail));
                }

                // update recycleview item once complete retrieve
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recycleViewAdapter.notifyDataSetChanged();
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // swipe action for item
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            Itinerary selectedItem = itineraryList.get(position);
            String item_name = selectedItem.placeName;

            DeleteData thread = new DeleteData(item_name);
            thread.start();

            itineraryList.remove(position);
            recycleViewAdapter.notifyDataSetChanged();

        }
    };

    private class DeleteData extends Thread
    {
        private String placeName;

        public DeleteData(String placeName)
        {
            this.placeName = placeName;
        }

        public void run()
        {
            try {
                URL url = new URL("https://niofprleiivsaehorsjr.supabase.co/rest/v1/Itinerary?select=*,*&place_name=eq." + placeName);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setRequestProperty("apikey",getString(R.string.SUPABASE_KEY));
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + getString(R.string.SUPABASE_KEY));

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Itinerary Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Fail to Delete", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readStream(InputStream inputStream)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            return "";
        }
    }
}

