package co.edu.konradlorenz.kscrum.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import co.edu.konradlorenz.kscrum.Activities.TaskDetailActivity;
import co.edu.konradlorenz.kscrum.Entities.Pbi;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;

public class PbiAdapter extends RecyclerView.Adapter<PbiAdapter.PbiAdapterHolder> {
    private Context context;
    private List<Pbi> pbiList;
    private FragmentManager fragmentManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private Pbi pbi;
    private CardView cardView;
    private Sprint sprint;

    public PbiAdapter(Context context, List<Pbi> pbiList) {
        this.context = context;
        this.pbiList = pbiList;
         sprint = (Sprint) ((Activity)context).getIntent().getSerializableExtra("sprint");
    }

    @NonNull
    @Override
    public PbiAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item, parent, false);
        cardView = view.findViewById(R.id.toDoCard);
        return new PbiAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PbiAdapterHolder holder, int position) {
        pbi= pbiList.get(position);
        Glide.with(context).load(pbi.getPbiImage()).into(holder.placeholder);
        holder.percentage.setText(pbi.getPercentage());
        holder.fecha.setText(pbi.getPbiDate().toString());
        holder.pbiTitle.setText(pbi.getPbiTitle());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(context, TaskDetailActivity.class);
                newIntent.putExtra("sprint", sprint);
                newIntent.putExtra("pbi", pbi);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return pbiList.size();
    }

    public class PbiAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView placeholder;
        public TextView pbiTitle, percentage, fecha;
        public CardView cardView;

        public PbiAdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.placeholder=itemView.findViewById(R.id.toDoImage);
            this.pbiTitle = itemView.findViewById(R.id.to_do_title);
            this.fecha= itemView.findViewById(R.id.to_do_date);
            this.percentage=itemView.findViewById(R.id.to_do_percentage);
            this.cardView = itemView.findViewById(R.id.toDoCard);

        }
    }
}
