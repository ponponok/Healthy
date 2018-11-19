package healthypond017.healthy.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import healthypond017.healthy.R;

public class CommentAdapter extends ArrayAdapter<Comment> {

    ArrayList<Comment> _comments = new ArrayList<Comment>();
    Context _context;
    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this._comments = objects;
        this._context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_comment_item, parent, false);
        TextView id = v.findViewById(R.id.comment_item_id);
        TextView body = v.findViewById(R.id.comment_item_body);
        TextView name = v.findViewById(R.id.comment_item_name);
        TextView email = v.findViewById(R.id.comment_item_email);
        id.setText(String.valueOf(_comments.get(position).getPostId())+" : "+String.valueOf(_comments.get(position).getId()));
        body.setText(_comments.get(position).getBody());
        name.setText(_comments.get(position).getName());
        email.setText(_comments.get(position).getEmail());
        return v;
    }
}
