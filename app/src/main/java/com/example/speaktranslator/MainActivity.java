package com.example.speaktranslator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private Locale currentLanguage = new Locale("he");
    private TextView tvPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPhrase = findViewById(R.id.tvPhrase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // אתחול Text2Speech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(currentLanguage);
                }
            }
        });

    // חיבור הכפתור
        Button btnSpeak = findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakPhrase();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_hebrew) {
            tvPhrase.setText("שלום, מה שלומך?");
            currentLanguage = new Locale("he");
            return true;
        } else if (item.getItemId() == R.id.menu_english) {
            tvPhrase.setText("Hello, how are you?");
            currentLanguage = Locale.ENGLISH;
            return true;
        } else if (item.getItemId() == R.id.menu_spanish) {
            tvPhrase.setText("Hola, ¿cómo estás?");
            currentLanguage = new Locale("es");
            return true;
        } else if (item.getItemId() == R.id.menu_french) {
            tvPhrase.setText("Bonjour, comment allez-vous?");
            currentLanguage = Locale.FRENCH;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void speakPhrase() {
        String text = tvPhrase.getText().toString();

        // עדכון השפה
        textToSpeech.setLanguage(currentLanguage);

        // הקראת הטקסט
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
