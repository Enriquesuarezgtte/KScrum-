package co.edu.konradlorenz.kscrum.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.edu.konradlorenz.kscrum.Activities.CreateProjectActivity;
import co.edu.konradlorenz.kscrum.Activities.LoginActivity;
import co.edu.konradlorenz.kscrum.Activities.PBIActivity;
import co.edu.konradlorenz.kscrum.Activities.ProfileActivity;
import co.edu.konradlorenz.kscrum.Adapters.ProjectsAdapter;
import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.R;

public class ProjectsFragment extends Fragment {

    private RecyclerView projectsRecycler;
    private ProjectsAdapter projectsAdapter;
    private BottomAppBar projectsBottomAppBar;
    private FloatingActionButton floatingActionButton;
    private View actContext;
    private FirebaseFirestore db;
    private CollectionReference cr;
    private ArrayList<Project> projects;
    private  ListenerRegistration registration;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        actContext = inflater.inflate(R.layout.projects_layout, container, false);

        findMaterials();
        setUpSupportActionBar();
        fabHandler();

        return actContext;
    }

    private void setUpSupportActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(projectsBottomAppBar);
    }

    private void recyclerSetUp() {

        projectsRecycler.setHasFixedSize(true);
        //----- getACTIVITY VS getContext
        RecyclerView.LayoutManager recycleManager = new LinearLayoutManager(getContext());
        projectsRecycler.setLayoutManager(recycleManager);

        //----- getACTIVITY VS getContext
        projectsAdapter = new ProjectsAdapter(getContext(), projects);
        projectsRecycler.setAdapter(projectsAdapter);
    }

    public void fabHandler() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), CreateProjectActivity.class);
                startActivity(newIntent);
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_profile:
                //----- getACTIVITY VS getContext
                Intent newIntent = new Intent(getContext(), ProfileActivity.class);
                startActivity(newIntent);
                break;
            case android.R.id.home:
                BottomNavigationDrawerFragment bottomNavigationDrawerFragment = new BottomNavigationDrawerFragment();
                bottomNavigationDrawerFragment.show(getFragmentManager(), bottomNavigationDrawerFragment.getTag());
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void runEffectBAB(View view) {
        if (projectsBottomAppBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_END) {
            projectsBottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        } else {
            projectsBottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        }
    }

    public void findMaterials() {
        projectsBottomAppBar = actContext.findViewById(R.id.bottom_app_bar_projects_layout);
        floatingActionButton = actContext.findViewById(R.id.fab_projects_layout);
        projectsRecycler = actContext.findViewById(R.id.projects_recycler);
    }

    public void openSprint(View view) {
        Intent newIntent = new Intent(getActivity(), PBIActivity.class);
        startActivity(newIntent);
    }

    public ProjectsFragment() {
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


                cr = db.collection("projects");
        projects = new ArrayList<>();

    }

    @Override
    public void onResume() {
        super.onResume();

        registration= cr.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    if(e.getCode().toString().equals("PERMISSION_DENIED")) {

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Intent newIntent = new Intent(getContext(), LoginActivity.class);
                        newIntent.putExtra("LogOut", "logout");
                        Toast.makeText(getContext(), "Domain not allowed only @gmail/ @konradlorenz.edu.co", Toast.LENGTH_LONG).show();
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                        return;
                    }
                    Log.w("PROJECTSFRAGMENT", e.getCode().toString(), e);
                    return;
                }else {
                    projects = new ArrayList<>();
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        System.out.println(doc.getDocument().getReference().getPath());
                        Project project = new Project(doc.getDocument().toObject(Project.class));
                        project.setId(doc.getDocument().getId());
                        projects.add(project);
                    }
                    recyclerSetUp();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        registration.remove();
    }
}
