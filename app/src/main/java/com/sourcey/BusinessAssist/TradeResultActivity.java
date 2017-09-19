package com.sourcey.BusinessAssist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.TradeoffAnalytics;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Dilemma;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Option;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Problem;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Solution;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.CategoricalColumn;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.Column;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.DateColumn;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.NumericColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TradeResultActivity extends AppCompatActivity {
    ArrayList<String> pos=new ArrayList<>();
    private String USERNAME = "6480a849-c569-445b-aef0-192de636f3af";
    private String PASSWORD = "LaRwNAol0Zsa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_result);
        new ReadAsyncTask().execute();
    }

    class ReadAsyncTask extends AsyncTask<String, Void, Dilemma>
    {
        @Override
        protected Dilemma doInBackground(String... arg0) {
            Dilemma dilemma=null;

            try {
                TradeoffAnalytics service = new TradeoffAnalytics();
                service.setUsernameAndPassword(USERNAME, PASSWORD);
                // Create a new CloudantClient instance for account endpoint <ACCOUNT>.cloudant.com

                String price = "price";
                String weight = "weight";
                String brand = "brand";
                String rDate = "rDate";

                String ge = "GE";
                String Whirlpool = "Whirlpool";
                String samsung = "Samsung";
                String lg = "LG";
                //String bosch = "Bosch";

                List<String> categories = new ArrayList<>();
                categories.add(ge);
                categories.add(Whirlpool);
                categories.add(samsung);
                categories.add(lg);
                //categories.add(bosch);


                List<String> preferences = new ArrayList<>();
                preferences.add(samsung);
                preferences.add(ge);
                preferences.add(Whirlpool);

                Problem problem = new Problem("Wall Ovens");

                // Define the objectives.
                List<Column> columns = new ArrayList<>();
                problem.setColumns(columns);

                NumericColumn priceColumn = new NumericColumn();
                priceColumn.setKey(price);
                priceColumn.setGoal(Column.Goal.MIN);
                priceColumn.setObjective(true);
                priceColumn.range(0, 4000);
                priceColumn.setFullName("Price");
                priceColumn.setFormat("number:2");
                columns.add(priceColumn);

                NumericColumn weightColumn = new NumericColumn();
                weightColumn.setKey(weight);
                weightColumn.setGoal(Column.Goal.MIN);
                weightColumn.setObjective(true);
                weightColumn.setFullName("Weight");
                weightColumn.setFormat("number:0");
                columns.add(weightColumn);

                CategoricalColumn brandColumn = new CategoricalColumn();
                brandColumn.setKey(brand);
                brandColumn.setGoal(Column.Goal.MIN);
                brandColumn.setObjective(true);
                brandColumn.setRange(categories);
                brandColumn.setPreference(preferences);
                brandColumn.setFullName("Brand");
                columns.add(brandColumn);

                DateColumn rDateColumn = new DateColumn();
                rDateColumn.setKey(rDate);
                rDateColumn.setGoal(Column.Goal.MIN);
                rDateColumn.setObjective(true);
                rDateColumn.setFullName("Release Date");
                rDateColumn.setFormat("date: 'MMM dd, yyyy'");
                columns.add(rDateColumn);

// Define the options.
                List<Option> options = new ArrayList<Option>();
                problem.setOptions(options);

                HashMap<String, Object> galaxyS4Specs = new HashMap<String, Object>();
                galaxyS4Specs.put(price, 2999);
                galaxyS4Specs.put(weight, 130);
                galaxyS4Specs.put(brand, samsung);
                galaxyS4Specs.put(rDate, "2013-04-29T00:00:00Z");
                options.add(new Option("1", "Samsung - 30\" Double Wall Oven ").values(galaxyS4Specs));

                HashMap<String, Object> iphone5Specs = new HashMap<String, Object>();
                iphone5Specs.put(price, 1999);
                iphone5Specs.put(weight, 112);
                iphone5Specs.put(brand, ge);
                iphone5Specs.put(rDate, "2012-09-21T00:00:00Z");
                options.add(new Option("2", "LG - 30\" Built-In Double Electric ").values(iphone5Specs));

                HashMap<String, Object> oneSpecs = new HashMap<String, Object>();
                oneSpecs.put(price, 1199);
                oneSpecs.put(weight, 112);
                oneSpecs.put(brand,ge);
                oneSpecs.put(rDate, "2013-03-01T00:00:00Z");
                options.add(new Option("3", "GE - 30\" Built-In Single Electric").values(oneSpecs));

                HashMap<String, Object> galaxyS5Specs = new HashMap<String, Object>();
                galaxyS5Specs.put(price, 1349);
                galaxyS5Specs.put(weight, 135);
                galaxyS5Specs.put(brand, samsung);
                galaxyS5Specs.put(rDate, "2014-04-29T00:00:00Z");
                options.add(new Option("4", "Samsung -30\" single wall").values(galaxyS5Specs));

                HashMap<String, Object> iphone6Specs = new HashMap<String, Object>();
                iphone6Specs.put(price, 1299);
                iphone6Specs.put(weight, 118);
                iphone6Specs.put(brand, ge);
                iphone6Specs.put(rDate, "2013-09-21T00:00:00Z");
                options.add(new Option("5", "GE - 25\" Built-In Single Electric").values(iphone6Specs));

                HashMap<String, Object> iphone7Specs = new HashMap<String, Object>();
                iphone7Specs.put(price, 1499);
                iphone7Specs.put(weight, 118);
                iphone7Specs.put(brand, lg);
                iphone7Specs.put(rDate, "2014-09-21T00:00:00Z");
                options.add(new Option("6", "Lg- 30\" Built-In Single Electric").values(iphone7Specs));

                HashMap<String, Object> xperiaSpecs = new HashMap<String, Object>();
                xperiaSpecs.put(price, 1799);
                xperiaSpecs.put(weight, 120);
                xperiaSpecs.put(brand, Whirlpool);
                xperiaSpecs.put(rDate, "2014-08-21T00:00:00Z");
                options.add(new Option("7", "Whirlpool- 30\" Built-In Single Electric").values(xperiaSpecs));

                dilemma = service.dilemmas(problem).execute();
               // System.out.println(dilemma);

            } catch (Exception e){
                e.printStackTrace();
            }
            return dilemma;
        }
        @Override
        protected void onPostExecute(Dilemma dilemma) {
            super.onPostExecute(dilemma);

            if (dilemma != null ) {
                List<Option> option=dilemma.getProblem().getOptions();
                List<Solution> solutions=dilemma.getResolution().getSolutions();
                for(int i=0;i<solutions.size();i++) {
                   String status= solutions.get(i).getStatus();
                    if(status.equals("FRONT")){
                        pos.add(option.get(i).getName()+"\n $\t"+option.get(i).getValues().get("price"));
                    }
                }
                ListView lv =(ListView) findViewById(R.id.lt) ;
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(TradeResultActivity.this,android.R.layout.simple_list_item_1, pos);
                //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lv.setAdapter(dataAdapter);

            }
        }
    }
}
