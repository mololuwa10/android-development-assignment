package NotePackage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NoteManager {
    public ArrayList<Note> noteArrayList;
    private static NoteManager instance;
    NoteManager() {
        noteArrayList = new ArrayList<>();
    }

    public static NoteManager getInstance() {
        if (instance == null) {
            instance = new NoteManager();
        }
        return instance;
    }

    public void add(Note note, Context context) {
        noteArrayList.add(note);

        SharedPreferences notesPrefs = context.getSharedPreferences("notesPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = notesPrefs.edit();
        Set<String> notesSet = new HashSet<>();
        for (Note n : noteArrayList) {
            String noteStr = n.getTitle() + "|||" + n.getDescription() + "|||" + n.getCreatedTime();
            notesSet.add(noteStr);
        }
        editor.putStringSet("notes", notesSet);
        editor.apply();
    }

    public ArrayList<Note> getNotes() {
        return noteArrayList;
    }

    public void delete(Note note) {
        noteArrayList.remove(note);
    }
}
