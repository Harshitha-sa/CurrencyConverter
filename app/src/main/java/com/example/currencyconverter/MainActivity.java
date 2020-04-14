package com.example.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    //    private static HttpURLConnection connection;
    private Toolbar mToolbar;
    private TextView eur,usd,inr;
    private Button btn;
    private EditText et;
    private Spinner spin;
    private int index;
    private  double inputvalue;
    private RequestQueue mQueue;
    public Double[] result = new Double[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eur = (TextView)findViewById(R.id.eur);
        usd = (TextView)findViewById(R.id.usd);
        inr = (TextView)findViewById(R.id.inr);
        btn =(Button)findViewById(R.id.btn);
        spin=(Spinner)findViewById(R.id.spin);
        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        et=(EditText) findViewById(R.id.et);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Currency converter");
        mQueue= Volley.newRequestQueue(this);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.currency, R.layout.support_simple_spinner_dropdown_item );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        JsonObjectRequest request;
        String url;
     Log.d("index","oncreate"+index);
        if (index==0){
            url= "https://api.exchangeratesapi.io/latest?base=USD&symbols=USD,EUR,INR";
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Double eur = rates.getDouble("EUR");
                                Double inr = rates.getDouble("INR");
                                Log.d("api","index0"+eur);
                                Log.d("api","index0"+inr);
                                Log.d("apiindex",""+index);
                                result[1]= inr;
                                result[0]= eur;
                                Log.d("ioioio",""+result[0]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("ero","in catch block");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }else if (index==1){
            url= "https://api.exchangeratesapi.io/latest?symbols=USD,INR";
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Double usd = rates.getDouble("USD");
                                Double inr = rates.getDouble("INR");
                                Log.d("api","index1"+inr);
                                Log.d("api","index1"+usd);
                                Log.d("apiindex",""+index);
                                result[1]= inr;
                                result[0]= usd;
                                Log.d("ioioio",""+result[0]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("ero","in catch block");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }

            });
            mQueue.add(request);
        }else if (index==2){
            url= "https://api.exchangeratesapi.io/latest?base=INR&symbols=USD,EUR,INR";
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Double eur = rates.getDouble("EUR");
                                Double usd = rates.getDouble("USD");
                                Log.d("api","index2"+eur);
                                Log.d("api","index2"+usd);
                                Log.d("apiindex",""+index);
                                result[1]= usd;
                                result[0]= eur;
                                Log.d("ioioio",""+result[0]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("ero","in catch block");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }

            });
            mQueue.add(request);
        }
//        Log.d("index","beforespin"+index);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         Log.d("index","setonclicklistner"+index);
                usd.setText("wait...");
                eur.setText("wait...");
                inr.setText("wait...");

                if(!(et.getText().toString().isEmpty()) && !et.getText().toString().equals(".")){
                    String textValue = et.getText().toString();
                    inputvalue= Double.parseDouble(textValue);
                    new calculate().execute();

                }else{
                    Toast.makeText(getApplicationContext(),"Please enter the value",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

     class calculate extends AsyncTask<String,String,Double[]> {//<Params,Progress,result>


        Double [] values=new Double[6];


        @Override
        protected void onPostExecute(Double[] result) {
            Log.d("index1","onpostexcecute"+index);
            Log.d("index2","onpost"+values[0]);
                String msg1,msg2,msg3;
                msg1=String.valueOf(values[0]);
                usd.setText(msg1);
                msg2=String.valueOf(values[1]);
                eur.setText(msg2);
                msg3=String.valueOf(values[2]);
                inr.setText(msg3);
         }
        @Override
        protected Double[] doInBackground(String... strings) {
            Log.d("index","doinbackground"+index);

            Log.d("afterfunction call","res0:"+result[0]+"res1"+result[1]);
            if(index==0) {
                AssignResults();
                mQueue.start();
                Log.d("inputval",""+inputvalue);
                double usdtoeuroval,usdtoinrval,usdtoeuroinp,usdtoinrinp,usdtousdinp;
                usdtousdinp= inputvalue*1;
                values[0]=usdtousdinp;
                Log.d("0backgroundres0",""+result[0]);
                usdtoeuroval= result[0];
                usdtoeuroinp=inputvalue*usdtoeuroval;
                values[1]=usdtoeuroinp;
                Log.d("0backgroundres1",""+result[1]);
                usdtoinrval=result[1];
                usdtoinrinp=inputvalue*usdtoinrval;
                values[2]=usdtoinrinp;
            }else if(index==1){
                AssignResults();
                mQueue.start();
                Log.d("inputval",""+inputvalue);
                double eurtousdval,eurtoinrval,eurtousdinp,eurtoinrinp,eurtoeurinp;
                eurtoeurinp= inputvalue*1;
                values[1]=eurtoeurinp;
                Log.d("1backgroundres0",""+result[0]);
                eurtousdval= result[0];
                eurtousdinp=inputvalue*eurtousdval;
                values[0]=eurtousdinp;
                Log.d("1backgroundres1",""+result[1]);
                eurtoinrval=result[1];
                eurtoinrinp=inputvalue*eurtoinrval;
                values[2]=eurtoinrinp;
                Log.d("index3","background"+values[0]);
            }else if(index==2){
                AssignResults();
                mQueue.start();
                Log.d("inputval",""+inputvalue);
                double inrtoeurval,inrtousdval,inrtoeurinp,inrtousdinp,inrtoinrinp;
                inrtoinrinp= inputvalue*1;
                values[2]=inrtoinrinp;
                Log.d("2backgroundres0",""+result[0]);
                inrtousdval= result[0];
                inrtousdinp=inputvalue*inrtousdval;
                values[0]=inrtousdinp;
                Log.d("2backgroundres1",""+result[1]);
                inrtoeurval=result[1];
                inrtoeurinp=inputvalue*inrtoeurval;
                values[1]=inrtoeurinp;
            }
            return values;
        }
    }

    public void AssignResults() {
        String url;
        JsonObjectRequest request;
        if (index==0){
            url= "https://api.exchangeratesapi.io/latest?base=USD&symbols=USD,EUR,INR";
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Double eur = rates.getDouble("EUR");
                                Double inr = rates.getDouble("INR");
                                Log.d("funapi","index0:"+eur);
                                Log.d("funapi","index0:"+inr);
                                Log.d("funapiindex",""+index);
                                result[1]= inr;
                                result[0]= eur;

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("ero","in catch block");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }else if (index==1){
            url= "https://api.exchangeratesapi.io/latest?symbols=USD,INR";
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Double usd = rates.getDouble("USD");
                                Double inr = rates.getDouble("INR");
                                Log.d("funapi","index1:"+inr);
                                Log.d("funapi","index1:"+usd);
                                Log.d("funapiindex",""+index);
                                result[1]= inr;
                                result[0]= usd;

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("ero","in catch block");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }

            });
            mQueue.add(request);
        }else if (index==2){
            url= "https://api.exchangeratesapi.io/latest?base=INR&symbols=USD,EUR,INR";
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Double eur = rates.getDouble("EUR");
                                Double usd = rates.getDouble("USD");
                                Log.d("funapi","index2:"+eur);
                                Log.d("funapi","index2:"+usd);
                                Log.d("funapiindex",""+index);
                                result[0]= usd;
                                result[1]= eur;

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("ero","in catch block");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }

            });
            mQueue.add(request);
        }


    }
}
