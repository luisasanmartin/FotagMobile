package com.example.luisasanmartin.fotagmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private ImageCollectionModel model;
    private RatingBar filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("on create");
        setContentView(R.layout.mainactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_widget);
        filter = (RatingBar) findViewById(R.id.filter);
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.gridview);
        model = new ImageCollectionModel();
        imageAdapter = new ImageAdapter(this, model);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        this.model.addView(new IView() {
            public void updateView() {
                gridView.invalidateViews();
                imageAdapter.notifyDataSetChanged();
                gridView.setAdapter(imageAdapter);
            }
        });
        filter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    System.out.println("rating changed to: " + rating);
                    model.setMinRating((int) rating);
                }

            }
        });
        filter.setRating(model.getMinRating());

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.mainactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_widget);
        setSupportActionBar(toolbar);
        System.out.println("on rotate");
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.invalidateViews();
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    class RetrieveBitmapTask extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;

        protected Bitmap doInBackground(String... urlInput) {
            try {
                URL url = new URL(urlInput[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (Exception e) {
                System.out.println("Error when loading from internet: " + e);
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            System.out.println("add bitmap");
            if (b != null) {
                model.addImage(b);
            }

        }
    }
    public void getImageFromURL() {
        final EditText txtUrl = new EditText(this);

        txtUrl.setHint("http://media.mydogspace.com.s3.amazonaws.com/wp-content/uploads/2013/08/puppy-500x350.jpg");

        new AlertDialog.Builder(this)
                .setTitle("Search for Image")
                .setMessage("Paste in the link of an image to display!")
                .setView(txtUrl)
                .setPositiveButton("Display Image", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        new RetrieveBitmapTask().execute(url);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            getImageFromURL();
            return true;
        } else if (id == R.id.action_load) {
            System.out.println("loading");
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_0);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_1);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_2);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_3);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_4);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_5);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_6);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_7);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_folder);
            this.model.addImage(bitmap);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunset);
            this.model.addImage(bitmap);

           return true;
        } else if (id == R.id.action_clear) {
            this.model.clear();
        }

        return super.onOptionsItemSelected(item);
    }
}
