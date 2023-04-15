package NotePackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.example.moloassignment.R;

public class AddNotesActivity extends AppCompatActivity {
    EditText title_input, content_input;
    Button save_notes;
    ArrayList<Note> noteArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        title_input = findViewById(R.id.title_input);
        content_input = findViewById(R.id.content_input);
        save_notes = findViewById(R.id.save_notes);

        // https://developer.android.com/reference/java/text/DateFormat
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK);
        String dateString = dateFormat.format(currentDate);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        save_notes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MutatingSharedPrefs")
            @Override
            public void onClick(View v) {
                String title = title_input.getText().toString() + " " + dateString;
                String content = content_input.getText().toString();
                long createdTime = System.currentTimeMillis();

                Note note = new Note();
                note.setTitle(title);
                note.setDescription(content);
                note.setCreatedTime(createdTime);

                NoteManager.getInstance().add(note, AddNotesActivity.this);

                Intent intent = new Intent(AddNotesActivity.this, MainNotesActivity.class);
                intent.putParcelableArrayListExtra("notes", NoteManager.getInstance().getNotes());
                intent.putExtra("loginDetails", loginDetails);
                intent.putExtra("username", username);
                startActivity(intent);

                Toast.makeText(AddNotesActivity.this, "Notes Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}