package co.edu.konradlorenz.kscrum.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.konradlorenz.kscrum.Entities.Usuario;
import co.edu.konradlorenz.kscrum.Fragments.PasswordRecoveryFragment;
import co.edu.konradlorenz.kscrum.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar loginProgressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private Button googleSignButton;
    private View.OnClickListener buttonGoogle;
    private FirebaseAuth mAuth;

    private static final String TAG ="LoginActivity";
    private static final int RC_SIGN_IN = 101;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        findMaterialElements();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    public void findMaterialElements() {
        loginProgressBar = findViewById(R.id.loading_spinner);
        googleSignButton = findViewById(R.id.google_login_button);
        googleSignButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
         db = FirebaseFirestore.getInstance();
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
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        credentialFirebaseSingIn(credential);
    }

    private void credentialFirebaseSingIn(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            logInSucceed();
                        } else {
                            logInConectionFailed();
                        }
                    }
                });
    }

    private void logInConectionFailed() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG);
    }

    private void logInSucceed() {
        FirebaseUser user = mAuth.getCurrentUser();
        Usuario newUser = new Usuario(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(), user.getUid());
        db.collection("Users").document(user.getUid()).set(newUser);
        Intent i = new Intent(LoginActivity.this, ProjectsContainerActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
