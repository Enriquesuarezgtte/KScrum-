package co.edu.konradlorenz.kscrum.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.edu.konradlorenz.kscrum.Activities.PBIActivity;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;

public class SprintsAdapter extends RecyclerView.Adapter<SprintsAdapter.AdapterHolder>{
    private Context context;
    private List<Sprint> sprintList;
    private FragmentManager fragmentManager;

    @NonNull
    @Override
    public SprintsAdapter.AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_item, parent,false);
        return new AdapterHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
        final Sprint newSprint =sprintList.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PBIActivity.class);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(newSprint.getImagen()).into(holder.placeHolder);
        holder.sprintName.setText(newSprint.getTitle());
        holder.sprintDescripcion.setText(newSprint.getExtraInfo());
        holder.percentage.setText(newSprint.getPercentage());

    }

    @Override
    public int getItemCount() {
        return sprintList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{
        public ImageView placeHolder;
        public TextView sprintName,percentage, sprintDescripcion;
        private View view;

        public AdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.placeHolder = itemView.findViewById(R.id.placeholder_image_item_s);
            this.sprintName = itemView.findViewById(R.id.sprint_name_item);
            this.sprintDescripcion = itemView.findViewById(R.id.description_item_s);
            this.percentage = itemView.findViewById(R.id.percentage_complete_item_s);
            this.view = itemView;
        }
    }

    public SprintsAdapter(Context context, List<Sprint> sprintList) {
        this.context = context;
        this.sprintList = sprintList;
    }


}
