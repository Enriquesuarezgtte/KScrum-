package co.edu.konradlorenz.kscrum.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import co.edu.konradlorenz.kscrum.Entities.Pbi;
import co.edu.konradlorenz.kscrum.R;

public class PbiAdapter extends RecyclerView.Adapter<PbiAdapter.PbiAdapterHolder> {
    private Context context;
    private List<Pbi> pbiList;
    private FragmentManager fragmentManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private Pbi pbi;

    public PbiAdapter(Context context, List<Pbi> pbiList) {
        this.context = context;
        this.pbiList = pbiList;
    }

    @NonNull
    @Override
    public PbiAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item, parent, false);
        return new PbiAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PbiAdapterHolder holder, int position) {
        pbi= pbiList.get(position);
        Glide.with(context).load(pbi.getPbiImage()).into(holder.placeholder);
        holder.percentage.setText(pbi.getpercentage());
        holder.fecha.setText(pbi.getPbiDate().toString());
        holder.pbiTitle.setText(pbi.getPbiTitle());

    }

    @Override
    public int getItemCount() {
        return pbiList.size();
    }

    public class PbiAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView placeholder;
        public TextView pbiTitle, percentage, fecha;
        public PbiAdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.placeholder=itemView.findViewById(R.id.toDoImage);
            this.pbiTitle = itemView.findViewById(R.id.to_do_title);
            this.fecha= itemView.findViewById(R.id.to_do_date);
            this.percentage=itemView.findViewById(R.id.to_do_percentage);

        }
    }
}
