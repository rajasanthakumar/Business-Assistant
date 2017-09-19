package com.sourcey.BusinessAssist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessResultActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnSubmit;

    private String DB_NAME = "";
    private String ACCOUNT = "aeff4c33-a592-4853-a908-ad6acb6fe583-bluemix";
    private String USERNAME = "aeff4c33-a592-4853-a908-ad6acb6fe583-bluemix";
    private String PASSWORD = "779e364564081d94b0a29b48be20c88c2404ff3c0449d719a13874fca1c5de15";

    ArrayList<String> docid = new ArrayList<>();
    ArrayList<String> ReviewText = new ArrayList<>();
    ArrayList<String> PosReviewText = new ArrayList<>();
    ArrayList<String> NegReviewText = new ArrayList<>();
    ArrayList<String> Pdoctext = new ArrayList<>();
    ArrayList<String> Ndoctext = new ArrayList<>();
    ArrayList <String> dat = new ArrayList<>();;
    ArrayList <String> text = new ArrayList<>();
    ArrayList <Double> percent= new ArrayList<>();
    ArrayList <String> type = new ArrayList<>();

    private String CUSERNAME = "87d5555c-1009-4a11-90b6-d40baabab9cf";
    private String CPASSWORD = "Z0PqSxG7cDp2";
    private String CID="2a3173x97-nlc-11768";
    Classification classification;

    long timer=0;
    String dt;
    float[] Posmon=new float[12];
    float[] negmon=new float[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_result);
        Intent intent1=getIntent();
        DB_NAME=intent1.getStringExtra("DB_NAME");

        final ProgressDialog progressDialog = new ProgressDialog(BusinessResultActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call
                        new ReadAsyncTask().execute();
                        progressDialog.dismiss();
                    }
                },30000);

        addItemsOnSpinner();
        addListenerOnButton();
        addListenerOnButton1();
        addListenerOnButton2();
        addListenerOnButton3();
        addListenerOnButton4();
    }

    private void addListenerOnButton2() {
        Button post= (Button) findViewById(R.id.posrev);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pn=new Intent(BusinessResultActivity.this,PositiveReview.class);
                pn.putStringArrayListExtra("Posreview",PosReviewText);
                startActivity(pn);
            }
        });
    }
    private void addListenerOnButton4() {
        Button post= (Button) findViewById(R.id.negrev);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pn=new Intent(BusinessResultActivity.this,NegativeReview.class);
                pn.putStringArrayListExtra("Posreview",NegReviewText);
                startActivity(pn);
            }
        });
    }
    private void addListenerOnButton3() {
        Button negative= (Button) findViewById(R.id.negative);

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pn=new Intent(BusinessResultActivity.this,Negative.class);
                pn.putStringArrayListExtra("negative",Ndoctext);
                startActivity(pn);
            }
        });
    }

    private void addListenerOnButton1() {

        Button positive= (Button) findViewById(R.id.positive);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pn=new Intent(BusinessResultActivity.this,Positive.class);
                pn.putStringArrayListExtra("positive",Pdoctext);
                startActivity(pn);
            }
        });


    }

    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<String>();
        list.add("Food");
        list.add("Item");
        list.add("Service");
        list.add("Quality");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addListenerOnButton() {


        spinner = (Spinner) findViewById(R.id.spinner);
        btnSubmit = (Button) findViewById(R.id.Go);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String val1= String.valueOf(spinner.getSelectedItem());
                NegReviewText.clear();
                PosReviewText.clear();
                for(int i=0;i<docid.size();i++) {
                    dt = dat.get(i).substring(5, 7);
                    if (ReviewText.get(i).toLowerCase().contains(val1.toLowerCase())) {
                        if (type.get(i).equals("POSITIVE")) {
                            PosReviewText.add(ReviewText.get(i));
                        } else {
                            NegReviewText.add(ReviewText.get(i));
                        }
                    }
                }

                String val= String.valueOf(spinner.getSelectedItem());
                float[] Posmonf=new float[12];
                float[] negmonf=new float[12];
                for(int i=0;i<docid.size();i++) {
                    dt = dat.get(i).substring(5, 7);
                    if (ReviewText.get(i).toLowerCase().contains(val.toLowerCase())) {
                        if (type.get(i).equals("POSITIVE")) {
                            switch (dt) {
                                case "01":
                                    Posmonf[0]++;
                                    break;
                                case "02":
                                    Posmonf[1]++;
                                    break;
                                case "03":
                                    Posmonf[2]++;
                                    break;
                                case "04":
                                    Posmonf[3]++;
                                    break;
                                case "05":
                                    Posmonf[4]++;
                                    break;
                                case "06":
                                    Posmonf[5]++;
                                    break;
                                case "07":
                                    Posmonf[6]++;
                                    break;
                                case "08":
                                    Posmonf[7]++;
                                    break;
                                case "09":
                                    Posmonf[8]++;
                                    break;
                                case "10":
                                    Posmonf[9]++;
                                    break;
                                case "11":
                                    Posmonf[10]++;
                                    break;
                                case "12":
                                    Posmonf[11]++;
                                    break;
                            }
                        } else {
                            switch (dt) {
                                case "01":
                                    negmonf[0]++;
                                    break;
                                case "02":
                                    negmonf[1]++;
                                    break;
                                case "03":
                                    negmonf[2]++;
                                    break;
                                case "04":
                                    negmonf[3]++;
                                    break;
                                case "05":
                                    negmonf[4]++;
                                    break;
                                case "06":
                                    negmonf[5]++;
                                    break;
                                case "07":
                                    negmonf[6]++;
                                    break;
                                case "08":
                                    negmonf[7]++;
                                    break;
                                case "09":
                                    negmonf[8]++;
                                    break;
                                case "10":
                                    negmonf[9]++;
                                    break;
                                case "11":
                                    negmonf[10]++;
                                    break;
                                case "12":
                                    negmonf[11]++;
                                    break;

                            }

                        }
                    }
                }

                BarChart barChart = (BarChart) findViewById(R.id.bargraph);
                ArrayList<String> labels = new ArrayList<String>();
                labels.add("January");
                labels.add("February");
                labels.add("March");
                labels.add("April");
                labels.add("May");
                labels.add("June");
                labels.add("July");
                labels.add("August");
                labels.add("September");
                labels.add("October");
                labels.add("November");
                labels.add("December");

                ArrayList<BarEntry> group1 = new ArrayList<>();
                group1.add(new BarEntry(Posmonf[0],0));
                group1.add(new BarEntry(Posmonf[1],1));
                group1.add(new BarEntry(Posmonf[2],2));
                group1.add(new BarEntry(Posmonf[3],3));
                group1.add(new BarEntry(Posmonf[4],4));
                group1.add(new BarEntry(Posmonf[5],5));
                group1.add(new BarEntry(Posmonf[6],6));
                group1.add(new BarEntry(Posmonf[7],7));
                group1.add(new BarEntry(Posmonf[8],8));
                group1.add(new BarEntry(Posmonf[9],9));
                group1.add(new BarEntry(Posmonf[10],10));
                group1.add(new BarEntry(Posmonf[11],11));

                ArrayList<BarEntry> group2 = new ArrayList<>();
                group2.add(new BarEntry(negmonf[0],0));
                group2.add(new BarEntry(negmonf[1],1));
                group2.add(new BarEntry(negmonf[2],2));
                group2.add(new BarEntry(negmonf[3],3));
                group2.add(new BarEntry(negmonf[4],4));
                group2.add(new BarEntry(negmonf[5],5));
                group2.add(new BarEntry(negmonf[6],6));
                group2.add(new BarEntry(negmonf[7],7));
                group2.add(new BarEntry(negmonf[8],8));
                group2.add(new BarEntry(negmonf[9],9));
                group2.add(new BarEntry(negmonf[10],10));
                group2.add(new BarEntry(negmonf[11],11));
                BarDataSet barDataSet1 = new BarDataSet(group1, "Positive");
                barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                BarDataSet barDataSet2 = new BarDataSet(group2,"Negative");
                barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist<br />
                dataSets.add(barDataSet1);
                dataSets.add(barDataSet2);

                BarData data = new BarData(labels,dataSets); // initialize the Bardata with argument labels and dataSet<br />
                barChart.setData(data);
            }

        });
    }


    class ReadAsyncTask extends AsyncTask<String, Void, ReviewDocs>
    {

        @Override
        protected ReviewDocs doInBackground(String... arg0) {
            Businessdb businessdb=null;
            ReviewDocs reviewdocs= null;
            try {
                // Create a new CloudantClient instance for account endpoint <ACCOUNT>.cloudant.com
                CloudantClient client = ClientBuilder.account(ACCOUNT)
                        .username(USERNAME)
                        .password(PASSWORD)
                        .build();

                NaturalLanguageClassifier service = new NaturalLanguageClassifier();
                service.setUsernameAndPassword(CUSERNAME, CPASSWORD);

                Database db = client.database(DB_NAME, false);
                String _id=DB_NAME;
                businessdb=db.find(Businessdb.class,_id);


                int count= (int) db.info().getDocCount();

                for(int i=1;i<=10;i++) {
                    _id="reviews-"+i;
                    reviewdocs = db.find(ReviewDocs.class, _id);
                    if(reviewdocs.getText().length()<1000) {
                        classification = service.classify(CID, reviewdocs.getText()).execute();
                        if (classification.getTopClass().equals("ham")) {
                            docid.add(_id);
                        }
                    }

                }

                AlchemyLanguage alchemyService = new AlchemyLanguage();
                //pooja b399be71ac0ffc9735313dc933a44eb5a939fa46
                //raja 9f3b1d17b6fe82be3eb69c1c8e5f1f53a7b7e45a
                //pinky 823cc84e5580819762eb7bed5dc849a898266a28
                alchemyService.setApiKey("9f3b1d17b6fe82be3eb69c1c8e5f1f53a7b7e45a");

                Map<String,Object> params = new HashMap<String, Object>();

                for(int i=0;i<docid.size();i++) {

                    reviewdocs = db.find(ReviewDocs.class,docid.get(i));
                    dat.add(reviewdocs.getDate());
                    ReviewText.add(reviewdocs.getText());

                    params.put(AlchemyLanguage.TEXT,reviewdocs.getText());

                    DocumentSentiment sentiment = (DocumentSentiment) alchemyService.getSentiment(params).execute();

                    Keywords sentiment1= alchemyService.getKeywords(params).execute();
                    List<Keyword> lk=sentiment1.getKeywords();

                    text.add(lk.get(1).getText());
                    percent.add(lk.get(1).getRelevance());
                   // text.add(lk.get(2).getText());
                   // percent.add(lk.get(2).getRelevance());

                    type.add(sentiment.getSentiment().getType().toString());

                }
                for(int i=0;i<type.size();i++){
                    dt=dat.get(i).substring(5,7);

                    if(type.get(i).equals("POSITIVE")){
                        Pdoctext.add(text.get(i));
                        PosReviewText.add(ReviewText.get(i));
                        switch (dt){
                            case "01":Posmon[0]++;
                                break;
                            case "02" :Posmon[1]++;
                                break;
                            case "03":Posmon[2]++;
                                break;
                            case "04":Posmon[3]++;
                                break;
                            case "05":Posmon[4]++;
                                break;
                            case "06":Posmon[5]++;
                                break;
                            case "07":Posmon[6]++;
                                break;
                            case "08":Posmon[7]++;
                                break;
                            case "09":Posmon[8]++;
                                break;
                            case "10":Posmon[9]++;
                                break;
                            case "11":Posmon[10]++;
                                break;
                            case "12":Posmon[11]++;
                                break;
                        }
                    }
                    else {
                        Ndoctext.add(text.get(i));
                        NegReviewText.add(ReviewText.get(i));
                        switch (dt){
                            case "01":negmon[0]++;
                                break;
                            case "02":negmon[1]++;
                                break;
                            case "03":negmon[2]++;
                                break;
                            case "04":negmon[3]++;
                                break;
                            case "05":negmon[4]++;
                                break;
                            case "06":negmon[5]++;
                                break;
                            case "07":negmon[6]++;
                                break;
                            case "08":negmon[7]++;
                                break;
                            case "09":negmon[8]++;
                                break;
                            case "10":negmon[9]++;
                                break;
                            case "11":negmon[10]++;
                                break;
                            case "12":negmon[11]++;
                                break;

                        }

                    }

                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return reviewdocs;
        }
        @Override
        protected void onPostExecute(ReviewDocs reviewdocs) {
            super.onPostExecute(reviewdocs);

            if (reviewdocs != null ) {

                BarChart barChart = (BarChart) findViewById(R.id.bargraph);
                ArrayList<String> labels = new ArrayList<String>();
                        labels.add("January");
                        labels.add("February");
                        labels.add("March");
                        labels.add("April");
                        labels.add("May");
                        labels.add("June");
                        labels.add("July");
                        labels.add("August");
                        labels.add("September");
                        labels.add("October");
                        labels.add("November");
                        labels.add("December");

                ArrayList<BarEntry> group1 = new ArrayList<>();
                        group1.add(new BarEntry(Posmon[0],0));
                        group1.add(new BarEntry(Posmon[1],1));
                        group1.add(new BarEntry(Posmon[2],2));
                        group1.add(new BarEntry(Posmon[3],3));
                        group1.add(new BarEntry(Posmon[4],4));
                        group1.add(new BarEntry(Posmon[5],5));
                        group1.add(new BarEntry(Posmon[6],6));
                        group1.add(new BarEntry(Posmon[7],7));
                        group1.add(new BarEntry(Posmon[8],8));
                        group1.add(new BarEntry(Posmon[9],9));
                        group1.add(new BarEntry(Posmon[10],10));
                        group1.add(new BarEntry(Posmon[11],11));

                ArrayList<BarEntry> group2 = new ArrayList<>();
                        group2.add(new BarEntry((float)negmon[0],0));
                        group2.add(new BarEntry((float)negmon[1],1));
                        group2.add(new BarEntry((float)negmon[2],2));
                        group2.add(new BarEntry((float)negmon[3],3));
                        group2.add(new BarEntry((float)negmon[4],4));
                        group2.add(new BarEntry((float)negmon[5],5));
                        group2.add(new BarEntry((float)negmon[6],6));
                        group2.add(new BarEntry((float)negmon[7],7));
                        group2.add(new BarEntry((float)negmon[8],8));
                        group2.add(new BarEntry((float)negmon[9],9));
                        group2.add(new BarEntry((float)negmon[10],10));
                        group2.add(new BarEntry((float)negmon[11],11));
                BarDataSet barDataSet1 = new BarDataSet(group1, "Positive");
                barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                BarDataSet barDataSet2 = new BarDataSet(group2,"Negative");
                barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist<br />
                dataSets.add(barDataSet1);
                dataSets.add(barDataSet2);

                BarData data = new BarData(labels,dataSets); // initialize the Bardata with argument labels and dataSet<br />
                barChart.setData(data);

            }
            else
            {
            }
        }
    }
}
