package edu.unc.dominno.rekindleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
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

        //FB Login Integration
        callbackManager = CallbackManager.Factory.create();
        /* accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                //set the access token using currentAccessToken when it's loaded or set

            }
            //If the access token is available already assign it

        }; */
        fbLoginInfo = (TextView) findViewById(R.id.fbInfo);
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbLoginInfo.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                fbLoginInfo.setText("Login attempt canceled");
            }

            @Override
            public void onError(FacebookException error) {

                fbLoginInfo.setText("Login attempt failed: " + error.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
