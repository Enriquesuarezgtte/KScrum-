package co.edu.konradlorenz.kscrum.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import co.edu.konradlorenz.kscrum.Fragments.PasswordRecoveryFragment;
import co.edu.konradlorenz.kscrum.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar loginProgressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private Button googleSignButton;
    private View.OnClickListener buttonGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findMaterialElements();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    public void findMaterialElements() {
        loginProgressBar = findViewById(R.id.loading_spinner);
        googleSignButton = findViewById(R.id.google_login_button);
        googleSignButton.setOnClickListener(this);

    }

    public void openMainActivity(View view) {
        loginProgressBar.setVisibility(View.VISIBLE);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // SplashActivity screen pause time
                    while (waited < 1000) {
                        sleep(250);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                } finally {
                    Intent newIntent = new Intent(LoginActivity.this, ProjectsContainerActivity.class);
                    startActivity(newIntent);
                    LoginActivity.this.finish();
                }
            }
        };
        splashTread.start();
    }

    public void signUpHandler(View view) {
        Intent newIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(newIntent);
    }

    public void openDialogFragment(View view) {
        Fragment fragment = new PasswordRecoveryFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.login_layout, fragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Toast.makeText(this, "Login succesfully", Toast.LENGTH_LONG);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                sigIn();

        }
    }

    private void sigIn() {
    Intent signIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signIntent, RC_S);
    }

}
