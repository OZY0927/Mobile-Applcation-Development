package my.edu.utar.group_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//DONE BY OOI ZHENG YEE

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.Viewholder> {

    private final Context context;
    private final ArrayList<PopularPlaces> placeModelArrayList;

    public AdapterClass(Context context, ArrayList<PopularPlaces> placeModelArrayList) {
        this.context = context;
        this.placeModelArrayList = placeModelArrayList;
    }

    @NonNull
    @Override
    public AdapterClass.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.Viewholder holder, int position) {

        PopularPlaces model = placeModelArrayList.get(position);
        Picasso.get().load(model.getPicURL()).error(R.drawable.bali).into(holder.placePic);
//        holder.placePic.setImageResource(model.getPicURL());
        holder.placeNameTV.setText(model.getCityName()+", "+model.getCountry());
        holder.placeDescriptionTV.setText(model.getDescription());

    }

    @Override
    public int getItemCount() {
        return placeModelArrayList.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder{
        private final TextView placeNameTV;
        private final TextView placeDescriptionTV;
        private final ImageView placePic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            placePic = itemView.findViewById(R.id.cardPlacePic);
            placeNameTV = itemView.findViewById(R.id.cardPlaceName);
            placeDescriptionTV = itemView.findViewById(R.id.cardPlaceDetail);

        }
    }
}
