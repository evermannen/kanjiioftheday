package com.eversoft.kanjiioftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayWelcomeMessage();
    }

    private void displayWelcomeMessage() {
        final TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        ImageView kanjiView = findViewById(R.id.kanjiImageView);
        welcomeTextView.setVisibility(View.VISIBLE);
        loadKanjiImageFromDrawable(this, "kanji", kanjiView);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeTextView.setVisibility(View.GONE);
                // Load and display today's Kanji here (covered in step 2 of the previous response)

                // Get the Kanji information for today and update the TextViews
                KanjiInfo kanjiInfo = getKanjiInfoForToday(MainActivity.this);
                updateKanjiInfo(kanjiInfo);
                loadKanjiImageFromDrawable(MainActivity.this, "kanji_dog", kanjiView);
                kanjiView.invalidate();

            }
        }, 5000);
    }

    public String generateKanjiFilename() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
        String formattedDate = currentDate.format(formatter);
        String kanjiFilename = formattedDate + "_kanji";
        return kanjiFilename;
    }

    private void loadKanjiImageFromDrawable(Context context, String filename, ImageView imageView) {
        Resources resources = context.getResources();
        int imageResourceId = resources.getIdentifier(filename, "drawable", context.getPackageName());

        if (imageResourceId != 0) {
            imageView.setImageResource(imageResourceId);
            imageView.invalidate();
        } else {
            // Handle the case where the resource is not found, e.g., load a placeholder or show an error message.
        }
    }

    private KanjiInfo getKanjiInfoForToday(Context context) {
        String kanjiFilename = generateKanjiFilename();
        String todayDate = kanjiFilename.split("_")[0];

        try {
            InputStream inputStream = context.getAssets().open("kanji_data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String date = jsonObject.getString("date");

                if (todayDate.equals(date)) {
                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("description");
                    String filename = jsonObject.getString("file");
                    return new KanjiInfo(date, title, description, filename);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Handle exceptions while reading or parsing the JSON file.
        }

        return null;
    }

    private void updateKanjiInfo(KanjiInfo kanjiInfo) {
        TextView kanjiTitleTextView = findViewById(R.id.kanjiTitleTextView);
        TextView kanjiDescriptionTextView = findViewById(R.id.kanjiDescriptionTextView);

        if (kanjiInfo != null) {
            kanjiTitleTextView.setText(kanjiInfo.getTitle());
            kanjiDescriptionTextView.setText(kanjiInfo.getDescription());
        } else {
            kanjiTitleTextView.setText("No Kanji information available");
            kanjiDescriptionTextView.setText("");
        }
    }
}