package com.sourcey.BusinessAssist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class PositiveReview extends AppCompatActivity {
    ArrayList<String> pos;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_review);

        Intent intent=getIntent();
        pos=intent.getStringArrayListExtra("Posreview");

        Set<String> hs = new LinkedHashSet<>();
        hs.addAll(pos);
        pos.clear();
        pos.addAll(hs);

        ListView lv =(ListView) findViewById(R.id.lt) ;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pos);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lv.setAdapter(dataAdapter);
        addButtonListener();
    }

    private void addButtonListener()
    {
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextToSpeechWebServiceHandler ttswsh = new TextToSpeechWebServiceHandler(getApplicationContext());
                String inputText = pos.get(i);
                ttswsh.execute(inputText);
                i++;
            }
        });
    }
}
