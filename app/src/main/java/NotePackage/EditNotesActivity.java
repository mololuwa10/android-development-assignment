package NotePackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.moloassignment.R;

public class EditNotesActivity extends AppCompatActivity {
    TextView edit_title_input, edit_content_input;
    AppCompatButton edit_notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        edit_title_input = findViewById(R.id.edit_title_input);
        edit_content_input = findViewById(R.id.edit_content_input);
        edit_notes = findViewById(R.id.edit_notes);

        Intent getDataIntent = getIntent();
        String title = getDataIntent.getStringExtra("title");
        String content = getDataIntent.getStringExtra("content");

        edit_title_input.setText(title);
        edit_content_input.setText(content);

        String[][] loginDetails = (String[][]) getIntent().getSerializableExtra("loginDetails");
        String username = getIntent().getStringExtra("username");

        edit_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = edit_title_input.getText().toString();
                String updatedContent = edit_content_input.getText().toString();
                int notePosition = getIntent().getIntExtra("position", -1);

                Note notes = NoteManager.getInstance().getNotes().get(notePosition);
                notes.setTitle(updatedTitle);
                notes.setDescription(updatedContent);

                Intent mainActivityIntent = new Intent(EditNotesActivity.this, MainNotesActivity.class);
                mainActivityIntent.putParcelableArrayListExtra("notes", NoteManager.getInstance().getNotes());
                mainActivityIntent.putExtra("note", notes);
                mainActivityIntent.putExtra("position", notePosition);
                mainActivityIntent.putExtra("loginDetails", loginDetails);
                mainActivityIntent.putExtra("username", username);
                setResult(RESULT_OK, mainActivityIntent);
                startActivity(mainActivityIntent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}