package co.edu.konradlorenz.kscrum.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import co.edu.konradlorenz.kscrum.Activities.ProfileActivity;
import co.edu.konradlorenz.kscrum.Adapters.SprintsAdapter;
import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;



public class SprintFragment extends Fragment {

    private RecyclerView sprintRecycler;
    private SprintsAdapter sprintAdapter;
    private BottomAppBar sprintsBottonAppBar;
    private FloatingActionButton floatingActionButton;
    private View view;
    private Project projectSelected;

    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private ArrayList<Sprint> sprints;
    FirebaseUser user;
    private Bundle bundle;

    public SprintFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sprint, container, false);
        findMaterial();
        getBundleData();
        setUpSupportActionBar();
        fabHandler();
        return view;
    }

    private void getBundleData() {
        bundle = this.getArguments();
        projectSelected = (Project)bundle.getSerializable("PROJECT");
    }

    private void fabHandler() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent new Intent( getContext(), )
                Toast.makeText(getContext(),"Recuerda crear sprints", Toast.LENGTH_LONG);
            }
        });
    }

    private void setUpSupportActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(sprintsBottonAppBar);
    }

    private void findMaterial() {
        sprintsBottonAppBar = view.findViewById(R.id.bottom_app_bar_sprints_layout);
        floatingActionButton = view.findViewById(R.id.fab_sprints_layout);
        sprintRecycler = view.findViewById(R.id.sprints_recycler);
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

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        collectionReference =db.collection("sprints");
        sprints=new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        sprints = new ArrayList<>();

        collectionReference.whereEqualTo("projectId", projectSelected.getId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            if(e!=null){
                Log.e("Sprint fragment", e.getMessage());
                return;
            }sprints = new ArrayList<>();

            for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                Sprint sprint = new Sprint(doc.toObject(Sprint.class));
                Log.w("Sprint fr",doc.getId());
                sprint.setId(doc.getId());
                sprints.add(sprint);
            }
                recyclerSetUp();
            }
        });
    }

    private void recyclerSetUp() {
        sprintRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager recLayoutManager = new LinearLayoutManager(getContext());
        sprintRecycler.setLayoutManager(recLayoutManager);

        sprintAdapter = new SprintsAdapter(getContext(), sprints);
        sprintRecycler.setAdapter(sprintAdapter);
    }
}
