package healthypond017.healthy.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import healthypond017.healthy.R;

import healthypond017.healthy.post.PostFragment;

public class CommentFragment extends Fragment {
    Bundle bundle;
    ArrayList<Comment> _comments = new ArrayList<Comment>();
    ListView _commentList;
    CommentAdapter _commentAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bundle = this.getArguments();
        String ag = bundle.getString("ID");
        Log.d("Comment", "id = "+ag);
        initBackBtn();
        getComment(ag);
    }

    private void initBackBtn(){
        Button backBtn = getView().findViewById(R.id.comment_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PostFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void getComment(final String commentId){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/"+commentId+"/comments")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<Collection<Comment>>(){}.getType();
                    Collection<Comment> comment = gson.fromJson(result, type);
                    Comment[] commeentResult = comment.toArray(new Comment[comment.size()]);

                    _commentList = getView().findViewById(R.id.comment_list);
                    _commentAdapter = new CommentAdapter(getActivity(), R.layout.fragment_comment_item, _comments);

                    for(int i = 0; i < commeentResult.length; i++){
                        Comment cmt = new Comment();
                        cmt.setPostId(commeentResult[i].getPostId());
                        cmt.setId(commeentResult[i].getId());
                        cmt.setBody(commeentResult[i].getBody());
                        cmt.setEmail(commeentResult[i].getEmail());
                        cmt.setName(commeentResult[i].getName());
                        _comments.add(cmt);
                    }
                    CommentFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _commentList.setAdapter(_commentAdapter);
                        }
                    });
                }
            }
        });
    }
}
