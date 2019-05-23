package co.edu.konradlorenz.kscrum.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private ConnectivityManager cm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        findMaterialElements();
        fabHandler();
        setUpView();
    }

    private void setUpView() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


            String bd=format.format(pbi.getPbiDate());
            String ld = format.format(pbi.getLastDate());
            lastDate.setText(" "+ld);
            beginningDate.setText(" "+bd);

        title.setText(pbi.getPbiTitle());
        description.setText(pbi.getPbiDescription());
        Glide.with(this).load(pbi.getPbiImage()).into(pbiImage);
        selectedItem = pbi.getPbiEstado();
    }

    public void fabHandler() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    Toast.makeText(context, "No Internet connection. Try later", Toast.LENGTH_LONG).show();
                }else {

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
                        Toast.makeText(context, "Error modifing pbi", Toast.LENGTH_LONG).show();
                        Log.e("TASK DETAIL", e.getMessage());
                    }
                });

            }
            }
        });
    }

    public void findMaterialElements() {
        context=this;
        floatingActionButton = findViewById(R.id.taskDetailFAB);
        title= findViewById(R.id.pbi_title); beginningDate = findViewById(R.id.pbi_beginning_date);
        lastDate=findViewById(R.id.pbi_last_date); description=findViewById(R.id.pbi_description);
        pbiImage=findViewById(R.id.pbi_image); pbi_file=findViewById(R.id.pbi_file);
        pbi = (Pbi)getIntent().getExtras().getSerializable("pbi");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("pbi").document(pbi.getPbiId());
        spinner =findViewById(R.id.spinner);
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
