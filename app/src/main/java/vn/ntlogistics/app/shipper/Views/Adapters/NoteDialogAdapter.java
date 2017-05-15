package vn.ntlogistics.app.shipper.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.shipper.R;

/**
 * Created by minhtan2908 on 3/1/17.
 */

public class NoteDialogAdapter extends RecyclerView.Adapter<NoteDialogAdapter.DataObjectHolder> {

    private Context context;
    private List<String> mListNote;

    public NoteDialogAdapter(Context context, List<String> mListNote) {
        this.context = context;
        this.mListNote = mListNote;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder{
        TextView tvNoteDialog;
        int iTag;
        public DataObjectHolder(View itemView) {
            super(itemView);
            tvNoteDialog = (TextView) itemView.findViewById(R.id.tvNoteDialog);
        }
    }

    @Override
    public NoteDialogAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_dialog, parent, false);
        NoteDialogAdapter.DataObjectHolder dataObjectHolder = new NoteDialogAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(NoteDialogAdapter.DataObjectHolder holder, int position) {
        holder.iTag = position;
        String header = "";
        if(mListNote.size() > 1){
            header = position+1 + ". ";
        }
        holder.tvNoteDialog.setText( header + mListNote.get(position));
    }

    @Override
    public int getItemCount() {
        return mListNote.size();
    }

}
