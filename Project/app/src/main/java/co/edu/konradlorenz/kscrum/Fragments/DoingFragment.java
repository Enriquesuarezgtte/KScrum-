package co.edu.konradlorenz.kscrum.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.edu.konradlorenz.kscrum.Activities.TaskDetailActivity;
import co.edu.konradlorenz.kscrum.Adapters.PbiAdapter;
import co.edu.konradlorenz.kscrum.Entities.Pbi;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;


public class DoingFragment extends Fragment {
    private RecyclerView pbiRecycler;
    private PbiAdapter pbiAdapter;
    private View view;
    private MaterialCardView materialCardView;
    private Sprint sprint;


    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private ArrayList<Pbi> pbis;
    FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doing, container, false);

        findMaterialElements();
       // cardHandler();

        return view;
    }

    public void cardHandler() {
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), TaskDetailActivity.class);
                startActivity(newIntent);
            }
        });
    }

    public void findMaterialElements() {
        sprint = (Sprint) getArguments().getSerializable("sprint");
        pbiRecycler = view.findViewById(R.id.doing_recycler);


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        collectionReference =db.collection("pbi");
        pbis=new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onResume() {
        super.onResume();

        collectionReference.whereEqualTo("sprintId", sprint.getId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.e("ToDo Fragment", e.getMessage());
                    return;
                }
                pbis=new ArrayList<>();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    if (doc.toObject(Pbi.class).getPbiEstado().equals("Doing")) {
                        Pbi pbi = new Pbi(doc.toObject(Pbi.class));
                        pbi.setSprintId(doc.getId());
                        pbis.add(pbi);
                    }
                }
                recyclerSetUp();
            }
        });
    }

    private void recyclerSetUp() {
        pbiRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager reclaLayoutManager = new LinearLayoutManager(getContext());
        pbiRecycler.setLayoutManager(reclaLayoutManager);
        pbiAdapter = new PbiAdapter(getContext(), pbis);
        pbiRecycler.setAdapter(pbiAdapter);
    }

}
