package com.example.myapplicationws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private TextView txtMessage=null;
    private Button btnWS = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        btnWS = (Button) findViewById(R.id.btnCallWS);
        btnWS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://geo.api.gouv.fr/communes?codePostal=38200&fields=nom,code,codeDepartement,population";
                AsyncHttpClient request = new AsyncHttpClient();
                request.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.i("Code Réponse", String.valueOf(statusCode));
                        Log.i("Flux JSON", response.toString());
                        String msg="";
                        for(int i = 0 ; i < response.length() ; i++) {
                            try {
                                msg+= "\n" + response.getJSONObject(i).getString("code");
                                msg+= " - " + response.getJSONObject(i).getString("nom");
                                msg+= " (" + response.getJSONObject(i).getString("codeDepartement") + ") ";
                                msg+= " - " + Integer.toString(response.getJSONObject(i).getInt("population")) + " hab.";
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        txtMessage.setText("Liste des communes - Code postal 38200 :\n" + msg);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable err) {
                        txtMessage.setText("StatusCode = " + String.valueOf(statusCode) + "\n Erreur = " + err.toString());
                    }
                });
            }


        });
    }
}