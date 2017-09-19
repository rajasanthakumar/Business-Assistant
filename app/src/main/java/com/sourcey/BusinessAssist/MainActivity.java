package com.sourcey.BusinessAssist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.btn_Business) Button _BusinessButton;
    @Bind(R.id.btn_tradeOff) Button _TradeoffButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent1=getIntent();

        String DB_NAME=intent1.getStringExtra("DB_NAME");

        final Intent intent2= new Intent(this,BusinessResultActivity.class);
        intent2.putExtra("DB_NAME",DB_NAME);
        final Intent intent3= new Intent(this,TradeResultActivity.class);
        intent3.putExtra("DB_NAME",DB_NAME);

        _BusinessButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        _TradeoffButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

    }

   /* public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    } */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
