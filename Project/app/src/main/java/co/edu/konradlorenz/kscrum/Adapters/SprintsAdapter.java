package co.edu.konradlorenz.kscrum.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import co.edu.konradlorenz.kscrum.Activities.PBIActivity;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;

public class SprintsAdapter extends RecyclerView.Adapter<SprintsAdapter.AdapterHolder>{
    private Context context;
    private List<Sprint> sprintList;
    private FragmentManager fragmentManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    Sprint newSprint;

    class MyMenuItemClickListener extends  AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit_pro_spr:
                   // Toast.makeText(context,R.string.action_add_favourite, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_delete_pro_spr:
                    System.out.println("adfs");
                   collectionReference.document(newSprint.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           Toast.makeText(context,"Delete succesfully",Toast.LENGTH_LONG);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(context,"Error on delete",Toast.LENGTH_LONG);

                       }
                   });
                    return true;
                default:
            }
            return false;
        }
    }

    @NonNull
    @Override
    public SprintsAdapter.AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_item, parent,false);
        return new AdapterHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHolder holder, int position) {
        newSprint =sprintList.get(position);
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("sprints");
          holder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sprintList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{
        public ImageView placeHolder;
        public TextView sprintName,percentage, sprintDescripcion;
        private View view;
        private ImageView dots;

        public AdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.placeHolder = itemView.findViewById(R.id.placeholder_image_item_s);
            this.sprintName = itemView.findViewById(R.id.sprint_name_item);
            this.sprintDescripcion = itemView.findViewById(R.id.description_item_s);
            this.percentage = itemView.findViewById(R.id.percentage_complete_item_s);
            this.view = itemView;
            this.dots= itemView.findViewById(R.id.overflow);
        }
    }

    public SprintsAdapter(Context context, List<Sprint> sprintList) {
        this.context = context;
        this.sprintList = sprintList;
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_pro_spr, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }



}
