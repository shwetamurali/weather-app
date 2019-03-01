package com.example.shwet.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String json;
    JSONObject first;
    TextView text0,text1,texty2,text3,text4,text5,text6,text7,text8,text9,bigTemp,texty10,text11,text12,text13,text14,description;
    Button b1;
    ImageView image0,image1,image2,image3,image4,bigPic;
    ArrayList<TextView> times, high, low;
    ArrayList<ImageView> images;
    ArrayList<Integer> IDs;
    ArrayList<String> conditions, quotes;
    EditText zipCode;
    String apiKey;
    String zip;
    URLConnection urlConnection;
    InputStream stream;
    BufferedReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text0 = (TextView) findViewById(R.id.textView0);
        text1 = (TextView) findViewById(R.id.textView1);
        texty2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        text4 = (TextView) findViewById(R.id.textView4);
        text5 = (TextView) findViewById(R.id.textView5);
        text6 = (TextView) findViewById(R.id.textView6);
        text7 = (TextView) findViewById(R.id.textView7);
        text8 = (TextView) findViewById(R.id.textView8);
        text9 = (TextView) findViewById(R.id.textView9);
        texty10 = (TextView) findViewById(R.id.textView10);
        text11 = (TextView) findViewById(R.id.textView12);
        text12 = (TextView) findViewById(R.id.textView13);
        text13 = (TextView) findViewById(R.id.textView14);
        text14 = (TextView) findViewById(R.id.textView11);
        image0 = (ImageView) findViewById(R.id.imageView);
        image1 = (ImageView) findViewById(R.id.imageView2);
        image2 = (ImageView) findViewById(R.id.imageView4);
        image3 = (ImageView) findViewById(R.id.imageView5);
        image4 = (ImageView) findViewById(R.id.imageView6);
        bigTemp = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView15);
        bigPic = (ImageView) findViewById(R.id.imageView3);
        b1 = (Button) findViewById(R.id.button);
        json = "";
        zipCode = (EditText) findViewById(R.id.editText);
        times = new ArrayList<>();
        times.add(texty10);
        times.add(text11);
        times.add(text12);
        times.add(text13);
        times.add(text14);
        high = new ArrayList<>();
        high.add(text5);
        high.add(text6);
        high.add(text7);
        high.add(text8);
        high.add(text9);
        low= new ArrayList<>();
        low.add(text0);
        low.add(text1);
        low.add(texty2);
        low.add(text3);
        low.add(text4);
        images = new ArrayList<>();
        images.add(image0);
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        IDs = new ArrayList<>();
        IDs.add(R.drawable.rain);
        IDs.add(R.drawable.sun);
        IDs.add(R.drawable.clouds);
        IDs.add(R.drawable.snow);
        IDs.add(R.drawable.thunder_rain);
        conditions= new ArrayList<>();
        conditions.add("Rain");
        conditions.add("Clear");
        conditions.add("Clouds");
        conditions.add("Snow");
        conditions.add("Thunderstorm");
        quotes = new ArrayList<>();
        quotes.add("You can stand under my umbrella");
        quotes.add("Purple Rain, Purple Rain");
        quotes.add("I’m walkin’ on sunshine, and don’t it feel good!");
        quotes.add("Here comes the sun");
        quotes.add("Drift away on a puffy cloud");
        quotes.add("We could watch it from the clouds");
        quotes.add("Let it Snow! Let it Snow! Let it Snow!");
        quotes.add("The snow glows white on the mountain tonight");
        quotes.add("Let's make it boom like thunder and lightning");
        quotes.add("Thunder! Feel the thunder!");
        zip = "08852";
        apiKey = "http://api.openweathermap.org/data/2.5/forecast?zip=08852&appid=33f89bc6def7de477de2f0605dbc02f6";
        final AsyncThread thread = new AsyncThread();
        thread.execute(zip);
        zipCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipCode.setText("");
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG,", "hello");
                zip = zipCode.getText()+"";
                AsyncThread thread1 = new AsyncThread();
                thread1.execute(zip);
            }
        });

    }

    public class AsyncThread extends AsyncTask<String,Void,JSONObject> {
        @Override
        protected JSONObject doInBackground(String... zipss) {

            try {
                String code = zipss[0];
                Log.d("sup", ""+code);
                apiKey = "http://api.openweathermap.org/data/2.5/forecast?zip="+code+"&appid=33f89bc6def7de477de2f0605dbc02f6";
                URL url = new URL(apiKey);
                urlConnection = url.openConnection();
                stream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line = reader.readLine();
                first = new JSONObject(line);
                Log.d("broo", ""+line);
            } catch (Exception e) {
                Log.d("TAG,", "1");
                e.printStackTrace();
                Log.d("TAG,", "1");
                Log.d("TAG,", e.toString());
            }
            return first;

        }

        @Override
        protected void onPostExecute(JSONObject j) {
            Log.d("TAG,", "post");
            j=first;
            Log.d("TAG", ""+first);
            try {
                for (int i=0; i<5;i++) {
                    first = j;
                    JSONArray getL = first.getJSONArray("list");
                    JSONObject getT = getL.getJSONObject(i);
                    JSONObject getM = getT.getJSONObject("main");
                    Long dt = Long.parseLong(getT.get("dt")+"");
                    int date = Integer.parseInt(new java.text.SimpleDateFormat("HH").format(new java.util.Date (dt*1000)));
                    if (date ==12)
                        times.get(i).setText(date+ " PM");
                    if (date ==0)
                        times.get(i).setText("12 AM");
                    if (date>12) {
                        int setDate = date % 12;
                        times.get(i).setText(setDate+" PM");
                    }
                    else if (date < 12) {
                        int setDate = date % 12;
                        times.get(i).setText(setDate+" AM");
                    }
                    JSONArray getW = getT.getJSONArray("weather");
                    JSONObject wp = getW.getJSONObject(0);
                    double getLow = (double)getM.get("temp_min");
                    double getHigh = (double)getM.get("temp_max");
                    double getTe = (double)getM.get("temp");
                    String findPic = (String) wp.get("main");
                    int lowTemp = (int) ( ((1.8)*(getLow - 273.15))+32);
                    int highTemp = (int) ( ((1.8)*(getHigh - 273.15))+32);
                    int temp = (int) ( ((1.8)*(getTe - 273.15))+32);
                    high.get(i).setText(highTemp+"°F");
                    low.get(i).setText(lowTemp+"°F");
                    int num = conditions.indexOf(findPic);
                    images.get(i).setImageResource(IDs.get(num));
                    if (i==0) {
                        bigTemp.setText(temp + "°F");
                        bigPic.setImageResource(IDs.get(num));
                        int rand = (int)(Math.random()*2+1);
                        if(num==0){
                            if(rand==1)
                                description.setText(quotes.get(0));
                            else
                                description.setText(quotes.get(1));
                        }
                        else if(num==1) {
                            if(rand==1)
                                description.setText(quotes.get(2));
                            else
                                description.setText(quotes.get(3));
                        }
                        else if(num==2) {
                            if(rand==1)
                                description.setText(quotes.get(4));
                            else
                                description.setText(quotes.get(5));
                        }
                        else if(num==3) {
                            if(rand==1)
                                description.setText(quotes.get(6));
                            else
                                description.setText(quotes.get(7));
                        }
                        else if(num==4) {
                            if(rand==1)
                                description.setText(quotes.get(8));
                            else
                                description.setText(quotes.get(9));
                        }

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG,", "error2");
            }

        }
    }
}