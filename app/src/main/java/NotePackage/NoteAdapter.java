package NotePackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moloassignment.R;

import java.text.DateFormat;
import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    ArrayList<Note> noteArrayList;
    OnNoteListener onNoteListener;
    public NoteAdapter(ArrayList<Note> noteArrayList, OnNoteListener onNoteListener) {
        this.noteArrayList = noteArrayList;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false), onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteArrayList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.contentOutput.setText(note.getDescription());

        String dateFormat = DateFormat.getDateInstance().format(note.createdTime);
        holder.timeOutput.setText(dateFormat);
    }

    @Override
    public int getItemCount() {
        if (noteArrayList != null) {
            return noteArrayList.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView titleOutput, contentOutput, timeOutput;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            titleOutput = itemView.findViewById(R.id.title_output);
            contentOutput = itemView.findViewById(R.id.content_output);
            timeOutput = itemView.findViewById(R.id.time_output);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onNoteListener != null) {
                onNoteListener.onNoteClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onNoteListener != null) {
                onNoteListener.onNoteLongClick(getAdapterPosition());
                return true;
            }
            return false;
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
        void onNoteLongClick(int position);
    }
}
