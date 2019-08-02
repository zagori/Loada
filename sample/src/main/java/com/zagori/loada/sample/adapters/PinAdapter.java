package com.zagori.loada.sample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zagori.loada.Loada;
import com.zagori.loada.interfaces.ImageErrorListener;
import com.zagori.loada.sample.R;
import com.zagori.loada.sample.models.Pin;

import java.util.List;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.MyViewHolder> {

    private static final String TAG = PinAdapter.class.getSimpleName();

    private List<Pin> pinList;
    private Context context;

    public PinAdapter(Context context, List<Pin> pinList) {
        this.context = context;
        this.pinList = pinList;
    }

    public void addPins(List<Pin> pinList){
        this.pinList.addAll(pinList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pin_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pin pin = pinList.get(position);

        holder.userName.setText(pin.getUser().getName());
        holder.likeCount.setText(String.valueOf(pin.getLikes()));

        // Load the pin image
        Loada.get(context).loadImage(pin.getUrls().getSmall(), holder.pinImage,
                new ImageErrorListener() {
                    @Override
                    public void onError(Throwable error) {
                        // do something with error
                    }
                });

        // Load the user profile image
        Loada.get(context).loadImage(pin.getUser().getProfile_image().getSmall(),
                holder.userProfileImage,
                new ImageErrorListener() {
                    @Override
                    public void onError(Throwable error) {
                        // do something with error
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Pin "+ position +" is clicked ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pinList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView pinImage;
        private ImageView userProfileImage;
        private TextView userName;
        private TextView likeCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pinImage = itemView.findViewById(R.id.pin_image);
            userProfileImage = itemView.findViewById(R.id.user_profile_image);
            userName = itemView.findViewById(R.id.user_name);
            likeCount = itemView.findViewById(R.id.likes);
        }
    }
}
