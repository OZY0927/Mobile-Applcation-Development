package my.edu.utar.group_assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//DONE BY FOO JIA SYUEN


public class DocumentFragment extends Fragment {
    OkHttpClient client;
    TextView tv;
    SearchView searchView;
    ListView myListView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);

        //findView
        client = new OkHttpClient();
        tv = view.findViewById(R.id.title);
        searchView = view.findViewById(R.id.searchView);
        myListView = view.findViewById(R.id.list);

        myListView.setVisibility(View.GONE);

        arrayList= new ArrayList<>();
        arrayList.add("Malaysia");
        arrayList.add("Singapore");
        arrayList.add("China");
        arrayList.add("America");
        arrayList.add("Canada");
        arrayList.add("Australia");
        arrayList.add("Germany");
        arrayList.add("Japan");
        arrayList.add("Korea");
        arrayList.add("Denmark");
        arrayList.add("Thailand");
        arrayList.add("Indonesia");
        arrayList.add("Vietnam");
        arrayList.add("Italy");
        arrayList.add("France");
        arrayList.add("Turkey");
        arrayList.add("Mexico");
        arrayList.add("Ukraine");
        arrayList.add("Spain");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        myListView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text
                String selectedItem = (String) adapter.getItem(position);
                get(selectedItem);
            }
        });

        return view;
    }

    public void get(String selectedItem){
        //String countryName = getCountryName.getText().toString();
        String formatCountryName = formatCountryName(selectedItem);
        LinearLayout dgc = getView().findViewById(R.id.containerLayout);
        Request request = new Request.Builder()
                .url("https://ohifagvtoxxhnogvvfzz.supabase.co/rest/v1/Visa_requirements?Country_name=eq." + formatCountryName)
                .addHeader("apiKey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9oaWZhZ3Z0b3h4aG5vZ3Z2Znp6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTI5NTUzMDksImV4cCI6MjAwODUzMTMwOX0.kHQlLBsk9R0kRr-wmoGWhKEcutFWhkjVNeqWBh9NLQg")
                .addHeader("Authorization","Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9oaWZhZ3Z0b3h4aG5vZ3Z2Znp6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTI5NTUzMDksImV4cCI6MjAwODUzMTMwOX0.kHQlLBsk9R0kRr-wmoGWhKEcutFWhkjVNeqWBh9NLQg")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //dynamically generate checkboxes - dgc
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String myResponse = response.body().string();
                            JSONArray jsonArray = new JSONArray(myResponse);
                            dgc.removeAllViews();
                            if (jsonArray.length()>0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String countryNameFromJSON = jsonObject.optString("Country_name");
                                    if (countryNameFromJSON.equals(formatCountryName)) {
                                        //Visa
                                        if (jsonObject.optBoolean("Visa")) {
                                            View visaCheckbox = generateCheckboxContainer("Visa", "*compulsory");
                                            dgc.addView(visaCheckbox);
                                        }
                                        //passport
                                        if (jsonObject.optBoolean("Passport")) {
                                            View passportCheckbox = generateCheckboxContainer("Passport", "*compulsory");
                                            dgc.addView(passportCheckbox);
                                        }
                                        //health_report
                                        if (jsonObject.optBoolean("Health_report")) {
                                            View healthReportCheckbox = generateCheckboxContainer("Health Report", "*compulsory");
                                            dgc.addView(healthReportCheckbox);
                                        }
                                        //corona
                                        if (jsonObject.optBoolean("Corona_vaccination_certificate")) {
                                            View coronaVaccinationCheckbox = generateCheckboxContainer("Corona Vaccination Certificate", "*compulsory");
                                            dgc.addView(coronaVaccinationCheckbox);
                                        }
                                        //financial statement
                                        if (jsonObject.optBoolean("Financial_statement")) {
                                            View financialStatementCheckbox = generateCheckboxContainer("Financial Statement", "*compulsory");
                                            dgc.addView(financialStatementCheckbox);
                                        }
                                        //travelInsurance
                                        View travelInsuranceCheckbox = generateCheckboxContainer("Travel Insurance", "*optional");
                                        dgc.addView(travelInsuranceCheckbox);
                                        //flightTicket
                                        View flightTicketCheckbox = generateCheckboxContainer("Flight Ticket", "*optional");
                                        dgc.addView(flightTicketCheckbox);
                                        //accommodation
                                        View accommodationCheckbox = generateCheckboxContainer("Accommodation", "*optional");
                                        dgc.addView(accommodationCheckbox);
                                        return;
                                    }
                                }
                            }
                            //country not found
                            Toast.makeText(getActivity(), "Country not found!", Toast.LENGTH_SHORT).show();
                            return;
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private View generateCheckboxContainer(String checkboxText, String additionalText) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View customCheckboxLayout = inflater.inflate(R.layout.checkbox_layout, null);

        CheckBox checkbox = customCheckboxLayout.findViewById(R.id.checkbox01);
        TextView textView = customCheckboxLayout.findViewById(R.id.textView01);

        checkbox.setText(checkboxText);
        textView.setText(additionalText);

        //set margin for each dynamically generated checkbox layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 25);
        customCheckboxLayout.setLayoutParams(params);
        return customCheckboxLayout;
    }

    public String formatCountryName(String input) {
        if (input == null || input.isEmpty()) {
            Toast.makeText(getActivity(), "Fill in country name", Toast.LENGTH_SHORT).show();
            return "";
        }
        input = input.toLowerCase();
        input = input.substring(0, 1).toUpperCase() + input.substring(1);
        return input;
    }
}