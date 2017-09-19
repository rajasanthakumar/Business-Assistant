package com.sourcey.BusinessAssist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private String DB_NAME = "";
    private String ACCOUNT = "aeff4c33-a592-4853-a908-ad6acb6fe583-bluemix";
    private String USERNAME = "aeff4c33-a592-4853-a908-ad6acb6fe583-bluemix";
    private String PASSWORD = "779e364564081d94b0a29b48be20c88c2404ff3c0449d719a13874fca1c5de15";

    private String consumerKey="IYnkGF-g6t2aCmnpEndbIw";
    private String consumerSecret="DZ4BY963kAEzvx2956BmnPD0A9c";
    private String token="4Szblv1HxjNjWzlE66ClaEK9PylMFJ-h";
    private String tokenSecret="own93gzsl8Duawo3mBLI-KD52EY";

    String businessid;
    String password;

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_id) EditText _busineesID;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        businessid = _busineesID.getText().toString();
        String mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        new WriteAsyncTask().execute();

                        //
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    class WriteAsyncTask extends AsyncTask<Void, Void, Businessdb>
    {
        @Override
        protected Businessdb doInBackground(Void... arg0) {

            YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
            YelpAPI yelpAPI = apiFactory.createAPI();

            Call<Business> call = yelpAPI.getBusiness(businessid);
            Businessdb businessdb = null;
            try {
                Response<Business> response = call.execute();
                Business business = response.body();
                CloudantClient client = ClientBuilder.account(ACCOUNT)
                        .username(USERNAME)
                        .password(PASSWORD)
                        .build();

                client.createDB(businessid);
                DB_NAME=businessid;
                Database db = client.database(DB_NAME, false);
                businessdb = new Businessdb(DB_NAME,business.name(),business.reviewCount(),business.rating(),password);
                db.save(businessdb);
                ReviewDoc reviewdoc = new ReviewDoc("review-1",business.reviews().get(0).excerpt(),business.reviews().get(0).rating(),business.reviews().get(0).timeCreated());
                db.save(reviewdoc);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return businessdb;
        }
        @Override
        protected void onPostExecute(Businessdb businessdb) {
            super.onPostExecute(businessdb);
            if(businessdb !=null)
            onSignupSuccess();
            else
                onSignupFailed();

        }
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent= new Intent(this,MainActivity.class);
        intent.putExtra("DB_NAME",DB_NAME);
        startActivity(intent);

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String busid = _busineesID.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (busid.isEmpty()) {
            _busineesID.setError("enter a valid businessID");
            valid = false;
        } else {
            _busineesID.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}