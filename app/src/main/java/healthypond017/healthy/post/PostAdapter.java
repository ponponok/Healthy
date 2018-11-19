package healthypond017.healthy.post;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import healthypond017.healthy.R;

public class PostAdapter extends ArrayAdapter<Post> {

    ArrayList<Post> _posts = new ArrayList<Post>();
    Context _context;

    public PostAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);
        this._posts = objects;
        this._context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(_context).inflate(R.layout.fragment_post_item, parent, false);
        TextView id = v.findViewById(R.id.post_item_id);
        TextView body=  v.findViewById(R.id.post_item_body);
        Post row = this._posts.get(position);
        id.setText(String.valueOf(row.getId())+" : "+row.getTitle());
        body.setText(row.getBody());
        return v;
    }
}