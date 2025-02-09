package com.example.shadowfoxlogin;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<String> locations;

    public LocationAdapter(List<String> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override

    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new LocationViewHolder(view);
    }

    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position){
        holder.locationName.setText(locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView locationName;

        public LocationViewHolder(@NonNull View itemView){
            super(itemView);
            locationName = itemView.findViewById(android.R.id.text1);
        }
    }
}
