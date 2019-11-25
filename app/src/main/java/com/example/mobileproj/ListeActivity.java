package com.example.mobileproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ListeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        final EditText recherche = (EditText) findViewById(R.id.recherche);
        final ListView listeview = (ListView) findViewById(R.id.listeView);
        final ConstraintLayout fond = (ConstraintLayout) findViewById(R.id.fond);

        RequestQueue mQueue = Volley.newRequestQueue(this);

        recherche.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                recherche.setText("");
            }
        });


        recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                jsonParse();
            }
        });
     }


     private void jsonParse(){
         final String url = "https://swapi.co/api/";
         System.out.println("A");

         JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
             new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                     try {
                         JSONArray jsonArray = response.getJSONArray("people");
                         System.out.println("B");

                         for(int i =0;i< jsonArray.length();i++){
                             System.out.println("C");
                             JSONObject perso = jsonArray.getJSONObject(i);
                             int nom = perso.getInt(""+i);

                             Log.i("test",""+nom);
                             System.out.println(jsonArray.length());
                         }

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     error.printStackTrace();
                     System.out.println("non");
                 }
            });

     }
}
