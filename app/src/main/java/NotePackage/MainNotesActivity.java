package NotePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moloassignment.AccountActivity;
import com.example.moloassignment.OtherPackage.OtherActivity;
import com.example.moloassignment.QuizPackage.MainQuizActivity;
import com.example.moloassignment.R;
import com.example.moloassignment.TempUserDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainNotesActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener {
    AppCompatButton add_new_notes;
    TextView notes_title;
    RecyclerView notes_recycler_view;
    NoteManager noteManager;
    NoteAdapter noteAdapter;
    ArrayList<Note> noteArrayList = new ArrayList<>();
    BottomNavigationView notes_bottom_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);

        notes_bottom_navigation = findViewById(R.id.notes_bottom_navigation);
        add_new_notes = findViewById(R.id.add_new_notes);
        noteArrayList = (ArrayList<Note>) NoteManager.getInstance().getNotes();

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        if(noteArrayList == null) {
            noteArrayList = new ArrayList<>();
        }

        add_new_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteIntent = new Intent(MainNotesActivity.this, AddNotesActivity.class);
                addNoteIntent.putExtra("loginDetails", loginDetails);
                addNoteIntent.putExtra("username", username);
                startActivity(addNoteIntent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            noteArrayList = intent.getParcelableArrayListExtra("notes");
        }

        noteManager = NoteManager.getInstance();

        notes_recycler_view = findViewById(R.id.notes_recycler_view);
        notes_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(noteArrayList, MainNotesActivity.this);
        noteAdapter = new NoteAdapter(noteManager.getNotes(), this);
        new NoteAdapter.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                onNoteClick(position);
            }
            @Override
            public void onNoteLongClick(int position) {
                onNoteLongClick(position);
            }
        };
        notes_recycler_view.setAdapter(noteAdapter);

        noteAdapter.notifyDataSetChanged();

        setupBottomNavigationView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onNoteClick(int position) {
        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");
        if (noteArrayList != null && !noteArrayList.isEmpty()) {
            Note note = noteArrayList.get(position);
            if(note != null) {
                Intent editNotesIntent = new Intent(MainNotesActivity.this, EditNotesActivity.class);
                editNotesIntent.putExtra("title", note.getTitle());
                editNotesIntent.putExtra("content", note.getDescription());
                editNotesIntent.putExtra("position", position);
                editNotesIntent.putExtra("loginDetails", loginDetails);
                editNotesIntent.putExtra("username", username);
                startActivity(editNotesIntent);
            }
        }
    }

    @Override
    public void onNoteLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this note?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            public void onClick(DialogInterface dialog, int id) {
                if(position >= 0 && position < noteManager.getNotes().size() && noteArrayList != null && !noteArrayList.isEmpty()) {
                    Note deleteNote = noteManager.getNotes().get(position);
                    noteManager.delete(deleteNote);
                    noteArrayList.remove(position);
                    noteAdapter.notifyItemRemoved(position);
                    noteAdapter.notifyDataSetChanged();

                    Toast.makeText(MainNotesActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();

                    saveNotesToPrefs();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

    private void saveNotesToPrefs() {
        SharedPreferences prefs = getSharedPreferences("notesPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> notesSet = new HashSet<>();
        for (Note note : noteArrayList) {
            String noteStr = note.getTitle() + "|||" + note.getDescription();
            notesSet.add(noteStr);
        }
        editor.putStringSet("notes", notesSet);
        editor.apply();
    }

    private void setupBottomNavigationView() {
        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");
        notes_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        Intent homeIntent = new Intent(MainNotesActivity.this, TempUserDashboard.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("loginDetails", loginDetails);
                        startActivity(homeIntent);
                        return true;
                    case R.id.quiz_nav:
                        Intent quizIntent = new Intent(MainNotesActivity.this, MainQuizActivity.class);
                        quizIntent.putExtra("username", username);
                        quizIntent.putExtra("loginDetails", loginDetails);
                        startActivity(quizIntent);
                        return true;
                    case R.id.file_nav:
                        Intent fileIntent = new Intent(MainNotesActivity.this, MainNotesActivity.class);
                        fileIntent.putExtra("username", username);
                        fileIntent.putExtra("loginDetails", loginDetails);
                        startActivity(fileIntent);
                        return true;
                    case R.id.account_nav:
                        Intent accountIntent = new Intent(MainNotesActivity.this, AccountActivity.class);
                        accountIntent.putExtra("loginDetails", loginDetails);
                        accountIntent.putExtra("username", username);
                        startActivity(accountIntent);
                        return true;
                    case R.id.other_nav:
                        Intent otherIntent = new Intent(MainNotesActivity.this, OtherActivity.class);
                        otherIntent.putExtra("loginDetails", loginDetails);
                        otherIntent.putExtra("username", username);
                        startActivity(otherIntent);
                        return true;
                }
                return false;
            }
        });
    }

}