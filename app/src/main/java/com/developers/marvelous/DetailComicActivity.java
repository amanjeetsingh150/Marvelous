package com.developers.marvelous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.developers.marvelous.Model.Comic;
import com.squareup.picasso.Picasso;

public class DetailComicActivity extends AppCompatActivity {

    private Comic comic;
    private ImageView imageView;
    private TextView title,comic_id,desc,issue_no,pg_no;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        toolbar= (Toolbar) findViewById(R.id.comic_toolbar);
        comic=getIntent().getParcelableExtra("comics");
        imageView= (ImageView) findViewById(R.id.comic_toolbar_image);
        title= (TextView) findViewById(R.id.comic_name);
        comic_id= (TextView) findViewById(R.id.comic_id);
        desc= (TextView) findViewById(R.id.comic_desc);
        issue_no= (TextView) findViewById(R.id.comic_issue);
        pg_no= (TextView) findViewById(R.id.comic_pg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(comic.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(DetailComicActivity.this).load(comic.getThumbnail()).into(imageView);
        title.setText(comic.getTitle());
        comic_id.setText("ID: "+comic.getId());
        if(comic.getDescription().equals("null")){
            desc.setText("Description Not Available");
        }
        else{
            desc.setText(comic.getDescription());
        }
        issue_no.setText("Issue Number: "+comic.getIssueNumber());
        pg_no.setText("Page Number: "+comic.getPageCount());
    }
}
