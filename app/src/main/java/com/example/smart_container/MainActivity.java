package com.example.smart_container;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTodayData(); //THIS GENERATES TODAY DATE AND THEN GETS IN THE posts array
        //Whatever you want to implement, implement inside the onResponse method
    }

    public void getTodayData() {
        textView_result = findViewById(R.id.textView_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://smartcontainer-rest-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholder jsonPlaceholder = retrofit.create(JsonPlaceholder.class);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        Log.i("myApp", formattedDate);
        Call<List<Post>> call = jsonPlaceholder.getPosts(formattedDate);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView_result.setText("Code : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = "";
                    content += "ID:  " + post.get_id() + "\n";
                    content += "Sensor ID:  " + post.getSensor_id() + "\n";
                    content += "Creation Date:  " + post.getCreation_date() + "\n";
                    content += "Height:  " + post.getHeight() + "\n\n";
                    textView_result.append(content);
                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView_result.setText(t.getMessage());
            }
        });
    }
}