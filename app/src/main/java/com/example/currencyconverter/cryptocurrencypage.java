package com.example.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class cryptocurrencypage extends AppCompatActivity {
    Button btn;
    EditText et;
    TextView cusd,ceur,cinr;
    private double inputValue;
    private RequestQueue mQueue;
    public Double[] result = new Double[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptocurrencypage);
        initalizefields();


        String url="https://blockchain.info/ticker";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject apiusd = response.getJSONObject("USD");
                            Double usd=apiusd.getDouble("buy");
                            JSONObject apieur = response.getJSONObject("EUR");
                            Double eur=apieur.getDouble("sell");
                            JSONObject apiinr = response.getJSONObject("INR");
                            Double inr=apiinr.getDouble("last");
                            Log.d("valuesusd",""+usd);
                            Log.d("valueseur",""+eur);
                            Log.d("valuesinr",""+inr);
                            result[0]=usd;
                            result[1]=eur;
                            result[2]=inr;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusd.setText("wait...");
                ceur.setText("wait...");
                cinr.setText("wait...");

                if (et.getText().toString().isEmpty()||et.getText().toString().equals(".")){
                    Toast.makeText(getApplicationContext(),"enetr a valid amount",Toast.LENGTH_SHORT).show();
                }else {
                    String textValue = et.getText().toString();
                    inputValue=Double.parseDouble(textValue);
                    new calculate().execute();
                }
            }
        });



    }

    private void initalizefields() {
        btn=findViewById(R.id.btn);
        cusd=findViewById(R.id.cusd);
        ceur=findViewById(R.id.ceur);
        cinr=findViewById(R.id.cinr);
        et=findViewById(R.id.et);
        mQueue= Volley.newRequestQueue(this);
    }


    class calculate extends AsyncTask<String,String,Double[]>{
        Double [] values=new Double[6];
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Double[] doubles) {
            String msg1,msg2,msg3;

            msg1=String.valueOf(values[0]);
            msg2=String.valueOf(values[1]);
            msg3=String.valueOf(values[2]);

            cusd.setText("$ "+msg1);
            ceur.setText("€ "+msg2);
            cinr.setText("₹ "+msg3);


        }

        @Override
        protected Double[] doInBackground(String... strings) {
            double usdval,usdinp,eurval,eurinp,inrval,inrinp;
            usdval=result[0];
            eurval=result[1];
            inrval=result[2];
            usdinp=inputValue*usdval;
            eurinp=inputValue*eurval;
            inrinp = inputValue * inrval;
            values[0]=usdinp;
            values[1]=eurinp;
            values[2]=inrinp;
            return values;

        }
    }
}
