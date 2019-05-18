package co.edu.konradlorenz.kscrum.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.konradlorenz.kscrum.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView username;
    private ImageView imageUser;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findMaterialElements();

    }

    private void findMaterialElements() {

        username = findViewById(R.id.user_profile);
        imageUser = findViewById(R.id.avatar);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        username.setText(user.getDisplayName());
        Glide.with(this).load(user.getPhotoUrl()).into(imageUser);
    }

}
