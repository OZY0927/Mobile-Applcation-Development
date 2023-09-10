package my.edu.utar.group_assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

//DONE BY WU JIAN WEI


public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ViewHolder>{
    Context context;
    public ArrayList<Itinerary> itineraryList;
    private int colorEven;
    private int colorOdd;

    public ItineraryAdapter(Context context, ArrayList<Itinerary> itineraryList) {
        this.context = context;
        this.itineraryList = itineraryList;
        this.colorEven = ContextCompat.getColor(context, R.color.card_view_even);
        this.colorOdd = ContextCompat.getColor(context, R.color.card_view_odd);
    }

    @NonNull
    @Override
    public ItineraryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itinerary_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItineraryAdapter.ViewHolder holder, int position) {
        Itinerary itinerary = itineraryList.get(position);
        holder.placeName.setText(itinerary.getPlaceName().toUpperCase(Locale.ROOT));
        holder.planDetails.setText(itinerary.getPlanDetail());

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(colorEven);
        } else {
            holder.itemView.setBackgroundColor(colorOdd);
        }
    }

    @Override
    public int getItemCount() {
        return itineraryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView placeName,planDetails;
        ImageButton del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            planDetails = itemView.findViewById(R.id.plan_detail);

        }
    }


}
