package com.developers.marvelous;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.developers.marvelous.Model.Character;
import com.developers.marvelous.Utils.API_KEY;
import com.developers.marvelous.Utils.UrlEndPoint;
import com.developers.marvelous.adapter.ComicListAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private Character character;
    private Toolbar toolbar;
    private ImageView banner;
    private TextView char_id,charName,description;
    private ListView list;
    private ArrayList<String> comicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        character=getIntent().getParcelableExtra("character");
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        char_id= (TextView) findViewById(R.id.char_id);
        list= (ListView) findViewById(R.id.list);
        comicList=new ArrayList<>();
        charName= (TextView) findViewById(R.id.char_name);
        description= (TextView) findViewById(R.id.desc);
        banner= (ImageView) findViewById(R.id.toolbar_image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(character.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(DetailActivity.this).load(character.getThumbnail()).into(banner);
        char_id.setText(character.getId());
        charName.setText(character.getName());
        if(character.getDescription().length()>0){
            description.setText(character.getDescription());
        }
        else{
            description.setText("Description Not Available");
        }
        String timestamp=String.valueOf(System.currentTimeMillis()/1000);
        Uri uri=Uri.parse(UrlEndPoint.getCharecters()+"/"+character.getId())
                .buildUpon().appendQueryParameter("ts",timestamp)
                .appendQueryParameter("apikey", API_KEY.getPublicKey())
                .appendQueryParameter("hash",UrlEndPoint.getMD5(timestamp)).build();
        JsonObjectRequest request=new JsonObjectRequest(uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONObject("data").getJSONArray("results");
                    for(int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        JSONObject comicObj=obj.getJSONObject("comics");
                        JSONArray comicArray=comicObj.getJSONArray("items");
                        if(comicArray.length()==0){
                            comicList.add("Not Available!!");
                        }
                        Log.e("DetailActivity",""+comicArray.length());
                        for(int j=0;j<comicArray.length();j++){
                            String title=comicArray.getJSONObject(j).getString("name");
                            comicList.add(title);
                        }
                    }
                    ComicListAdapter comicListAdapter=new ComicListAdapter(DetailActivity.this,comicList);
                    list.setAdapter(comicListAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error while fetching list of comics!!",Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(15000,3,1));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
