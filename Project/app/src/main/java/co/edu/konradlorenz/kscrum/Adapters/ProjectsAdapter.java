package co.edu.konradlorenz.kscrum.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import co.edu.konradlorenz.kscrum.Activities.CRUProjectsActivity;
import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.Fragments.ProjectTabletDetailFragment;
import co.edu.konradlorenz.kscrum.Fragments.SprintFragment;
import co.edu.konradlorenz.kscrum.R;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> {

    private Context actualContext;
    private List<Project> projectsList;
    private FragmentManager fragmentManager;
    private CollectionReference collectionReference, collectionSprintReference;
    private FirebaseFirestore firebaseFirestore;
    private Project newProject2;



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView placeHolder, overflowp;
        public TextView projectName, projectDescription;
        private View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.placeHolder = itemView.findViewById(R.id.placeholder_image_item);
            this.projectName = itemView.findViewById(R.id.project_name_item);
            this.projectDescription = itemView.findViewById(R.id.percentage_complete_item);
            this.view = itemView;
            this.overflowp = itemView.findViewById(R.id.overflowp);
        }
    }

    public ProjectsAdapter(Context actualContext, ArrayList<Project> projects) {
        this.actualContext = actualContext;
        this.projectsList = projects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);

        return new MyViewHolder(view);
    }

    private void loadProjectDetail(int id, FragmentManager manager, Project newProject) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("PROJECT", newProject);

         ProjectTabletDetailFragment pTDF = new ProjectTabletDetailFragment();
        pTDF.setArguments(bundle);

        if (id == R.id.projects_fragment) {
            manager.beginTransaction().add(id, pTDF).addToBackStack(null).commit();
        } else {
            manager.beginTransaction().add(id, pTDF).commit();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         final Project newProject = projectsList.get(position);
         newProject2=new Project(newProject);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = ((AppCompatActivity) actualContext).getSupportFragmentManager();

                if (actualContext.getResources().getBoolean(R.bool.has_two_panes)) {

                    //Toast.makeText(actualContext, "Está en modo Landscape", Toast.LENGTH_SHORT).show();
                    loadProjectDetail(R.id.tablet_detail_project_fragment, manager, newProject);

                } else {

                    //Toast.makeText(actualContext, "Está en modo Portrait", Toast.LENGTH_SHORT).show();
                    loadProjectDetail(R.id.projects_fragment, manager, newProject);
                }
            }
        });

        Glide.with(actualContext).load(newProject.getProjectPhotoURL()).into(holder.placeHolder);
        holder.projectName.setText(newProject.getProjectDisplayName());
        holder.projectDescription.setText(newProject.getProjectDescription());
        firebaseFirestore =  FirebaseFirestore.getInstance();
        collectionSprintReference = firebaseFirestore.collection("sprints");
        collectionReference = firebaseFirestore.collection("projects");
        holder.overflowp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(actualContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_pro_spr, popup.getMenu());
        popup.setOnMenuItemClickListener(new ProjectsAdapter.MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit_pro_spr:
                    Intent intent = new Intent(actualContext , CRUProjectsActivity.class);
                    intent.putExtra("UUID" , newProject2.getId());
                    actualContext.startActivity(intent);

                    return true;
                case R.id.action_delete_pro_spr:

                    collectionReference.document(newProject2.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            deleteSprintFromProject();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(actualContext,"Error on delete",Toast.LENGTH_LONG);

                        }
                    });
                    return true;
                default:
            }
            return false;
        }
    }

    private void deleteSprintFromProject() {
        collectionSprintReference.whereEqualTo("projectId", newProject2.getId()
        ).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.e("Sprint fragment", "Error in query");
                    return;
                }
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                   doc.getReference().delete();
                }

            }
        });
    }
}
