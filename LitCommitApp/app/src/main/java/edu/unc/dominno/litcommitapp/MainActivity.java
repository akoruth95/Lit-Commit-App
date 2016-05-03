package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
// Add this to the header of your file:
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

//implements FacebookCallback

public class MainActivity extends AppCompatActivity {
    private TextView fbLoginInfo;
    private LoginButton loginButton; //FB Login button
    private CallbackManager callbackManager; //manages callbacks used in FB
    private AccessTokenTracker accessTokenTracker; //the current access token & profile
    private ProfileTracker profileTracker; //tracks current profile


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        setContentView(R.layout.activity_main);
        //FB Login Integration
        callbackManager = CallbackManager.Factory.create();
        //tracks whether user is already logged in to FB
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
            //If the access token is available already assign it

        };
        //fbLoginInfo = (TextView) findViewById(R.id.fbConnected);
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*fbLoginInfo.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
            }

            @Override
            public void onCancel() {
                //fbLoginInfo.setText("Login attempt canceled");
                showPop("Facebook Login Canceled!");
            }

            @Override
            public void onError(FacebookException error) {
                //fbLoginInfo.setText("Login attempt failed: " + error.getMessage());
                showPop("Facebook Login Failed!");
            }
        });

        if (isLoggedIn()) {
            // Send intent to Main2Activity
            Intent x = new Intent(this, Main2Activity.class);
            startActivity(x);
            finish();
        }

    }

    private void updateWithToken(AccessToken newAccessToken) {
        //TextView isFb = (TextView) findViewById(R.id.fbConnected);
        if (isLoggedIn()) {
            //isFb.setText("I'm connected");

            // Send intent to Main2Activity
            Intent x = new Intent(this, Main2Activity.class);
            startActivity(x);
            finish();
        } else {
            showPop("Facebook Login Failed!");
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void goWithoutFB(View v) {
        Intent x = new Intent(this, Main2Activity.class);
        startActivity(x);
        finish();
    }

    public void showPop(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }
}
