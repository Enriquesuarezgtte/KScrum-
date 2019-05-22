package co.edu.konradlorenz.kscrum.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.konradlorenz.kscrum.Entities.Pbi;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;

public class TaskDetailActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private TextView title, beginningDate, lastDate;
    private TextInputEditText description;
    private ImageView pbiImage;
    private Button pbi_file;
    private Pbi pbi;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private Spinner spinner;
    private String selectedItem;
    private ArrayAdapter adapterSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        findMaterialElements();
        fabHandler();
        setUpView();
    }

    private void setUpView() {

        title.setText(pbi.getPbiTitle());
        beginningDate.setText(pbi.getPbiDate().toString());
        lastDate.setText(pbi.getLastDate().toString());
        description.setText(pbi.getPbiDescription());
        Glide.with(this).load(pbi.getPbiImage()).into(pbiImage);
        context=this;
        selectedItem = pbi.getPbiEstado();
    }

    public void fabHandler() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbi.setPbiDescription(description.getText().toString());
                pbi.setPbiEstado(selectedItem);
                documentReference.set(pbi).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Pbi modified succesfully", Toast.LENGTH_LONG);
                        Intent newIntent = new Intent(TaskDetailActivity.this, PBIActivity.class);
                        newIntent.putExtra("pbi", pbi);
                        newIntent.putExtra("sprint", (Sprint)getIntent().getSerializableExtra("sprint"));
                        startActivity(newIntent);
                        TaskDetailActivity.this.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error modifing pbi", Toast.LENGTH_LONG);
                        Log.e("TASK DETAIL", e.getMessage());
                    }
                });

            }
        });
    }

    public void findMaterialElements() {
        floatingActionButton = findViewById(R.id.taskDetailFAB);
        title= findViewById(R.id.pbi_title); beginningDate = findViewById(R.id.pbi_beginning_date);
        lastDate=findViewById(R.id.pbi_last_date); description=findViewById(R.id.pbi_description);
        pbiImage=findViewById(R.id.pbi_image); pbi_file=findViewById(R.id.pbi_file);
        pbi = (Pbi)getIntent().getExtras().getSerializable("pbi");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("pbi").document(pbi.getPbiId());
        spinner =findViewById(R.id.spinner);

        adapterSpinner=ArrayAdapter.createFromResource(this, R.array.status, R.layout.spinner_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setSelection(adapterSpinner.getPosition(pbi.getPbiEstado()));

    }

    @Override
    protected void onStart() {
        super.onStart();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedItem =pbi.getPbiEstado();
            }
        });
    }
}
