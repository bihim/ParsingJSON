package com.example.parsingjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    private RequestQueue requestQueue;
    private ListView listView;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);

        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                jsonParse();
            }
        });
    }

    private void jsonParse()
    {
        final ArrayList<String> arrayList = new ArrayList<>();
        String url = "https://api.myjson.com/bins/ynt3t";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray = response.getJSONArray("superhero");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject superhero = jsonArray.getJSONObject(i);

                        String name= superhero.getString("name");
                        int age = superhero.getInt("age");
                        String ability = superhero.getString("ability");
                        arrayList.add(name);
                        arrayList.add(String.valueOf(age));
                        arrayList.add(ability);
                        arrayList.add("");

                        Log.d("Inside For", "onResponse: "+Arrays.toString(arrayList.toArray()));
                    }

                    arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            Toast.makeText(MainActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();

                        }
                    });

                    Log.d("Outside For", "onResponse: "+Arrays.toString(arrayList.toArray()));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
        //This is a test
    }
}
