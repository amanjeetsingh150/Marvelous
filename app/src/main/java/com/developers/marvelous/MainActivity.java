package com.developers.marvelous;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.developers.marvelous.Model.Character;
import com.developers.marvelous.Model.CharectersTable;
import com.developers.marvelous.Model.Comic;
import com.developers.marvelous.Utils.API_KEY;
import com.developers.marvelous.Utils.UrlEndPoint;
import com.developers.marvelous.adapter.CharactersAdapter;
import com.developers.marvelous.adapter.ComicsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private RecyclerView recyclerView;
    private List<Character> characters;
    private List<Comic> comics;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        preferences=this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        selection=preferences.getString("choice","0");
        preferences.registerOnSharedPreferenceChangeListener(this);
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        if(selection.equals("0")){
            fetchFromApi();
        }
        if(selection.equals("1")) {
            fetchFromComicApi();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            preferences=this.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("choice","0");
            editor.commit();
        }
        if(id==R.id.action_settings1){
            preferences=this.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("choice","1");
            editor.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchFromApi(){
        String timestamp=String.valueOf(System.currentTimeMillis()/1000);
        Uri uri=Uri.parse(UrlEndPoint.getCharecters())
                .buildUpon().appendQueryParameter("ts",timestamp)
                .appendQueryParameter("apikey", API_KEY.getPublicKey())
                .appendQueryParameter("hash",UrlEndPoint.getMD5(timestamp)).build();
        Log.e("MainActivity",""+uri.toString()+" "+timestamp);
        JsonObjectRequest request=new JsonObjectRequest(uri.toString(), null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("MainActivity","response");
                try{
                    JSONArray array=response.getJSONObject("data").getJSONArray("results");
                    characters=new ArrayList<>(array.length());
                    for(int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        Character character=new Character();
                        character.setId(obj.getString("id"));
                        character.setName(obj.getString("name"));
                        character.setDescription(obj.getString("description"));
                        character.setThumbnail(array.getJSONObject(i).getJSONObject("thumbnail")
                                .getString("path") + ".jpg");
                        characters.add(character);
                        try{
                            getContentResolver().insert(CharectersTable.CONTENT_URI,CharectersTable.getContentValues(character,true));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    Cursor cursor=getContentResolver().query(CharectersTable.CONTENT_URI,null,null,null,null);
                    List<Character> characrterList=CharectersTable.getRows(cursor,false);
                    GridLayoutManager llm = new GridLayoutManager(getApplicationContext(),3);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(new CharactersAdapter(MainActivity.this,characrterList));
                    progressDialog.cancel();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error while fetching",Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(15000,3,1));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void fetchFromComicApi(){
        String timestamp=String.valueOf(System.currentTimeMillis()/1000);
        Uri uri=Uri.parse(UrlEndPoint.getComics())
                .buildUpon().appendQueryParameter("ts",timestamp)
                .appendQueryParameter("apikey", API_KEY.getPublicKey())
                .appendQueryParameter("hash",UrlEndPoint.getMD5(timestamp)).build();
        Log.e("MainActivity",""+uri.toString()+" "+timestamp);
        JsonObjectRequest request=new JsonObjectRequest(uri.toString(), null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("MainActivity","response");
                try{
                    JSONArray array=response.getJSONObject("data").getJSONArray("results");
                    comics=new ArrayList<>(array.length());
                    for(int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        Comic comic=new Comic();
                        comic.setId(obj.getString("id"));
                        comic.setTitle(obj.getString("title"));
                        comic.setDescription(obj.getString("description"));
                        comic.setThumbnail(array.getJSONObject(i).getJSONObject("thumbnail")
                                .getString("path") + ".jpg");
                        comic.setIssueNumber(obj.getString("issueNumber"));
                        comic.setFormat(obj.getString("format"));
                        comic.setPageCount(obj.getString("pageCount"));
                        comics.add(comic);
                    }
                    GridLayoutManager llm = new GridLayoutManager(getApplicationContext(),3);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(new ComicsAdapter(MainActivity.this,comics));
                    progressDialog.cancel();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error while fetching",Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(15000,3,1));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.e("MainActivity","in shared Listener");
        if(sharedPreferences.getString("choice","0").equals("1")){
            fetchFromComicApi();
        }
        else{
            fetchFromApi();
        }
    }
}
