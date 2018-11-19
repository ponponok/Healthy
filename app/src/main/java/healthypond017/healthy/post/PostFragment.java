package healthypond017.healthy.post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

import healthypond017.healthy.MenuFragment;
import healthypond017.healthy.R;
import healthypond017.healthy.comment.CommentFragment;

public class PostFragment extends Fragment {
    ArrayList<Post> _posts = new ArrayList<Post>();
    ListView _postList;
    PostAdapter _postAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        okht();
//        new JSONTask().execute();
    }

    private void initBackBtn(){
        Button backBtn = getView().findViewById(R.id.post_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void ListClick(){
        _postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post gPost = _posts.get(position);
                //Toast.makeText(getActivity(), String.valueOf(gPost.getId()), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("ID",String.valueOf(gPost.getId()));
                CommentFragment cf = new CommentFragment();
                cf.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, cf)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void okht(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
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
                    // do something wih the result
                    //Log.d("post", response.body().string());
                    String result = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<Collection<Post>>(){}.getType();
                    Collection<Post> post = gson.fromJson(result, type);
                    Post[] postResult = post.toArray(new Post[post.size()]);

                    _postList =  getView().findViewById(R.id.post_list);
                    _postAdapter = new PostAdapter(getActivity(), R.layout.fragment_post_item, _posts);

                    for(int i = 0; i < postResult.length; i++){
                        Post pts = new Post();
                        pts.setId(postResult[i].getId());
                        pts.setTitle(postResult[i].getTitle());
                        pts.setBody(postResult[i].getBody());
                        _posts.add(pts);
                    }
                    PostFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _postList.setAdapter(_postAdapter);
                            ListClick();
                        }
                    });
                }
            }
        });


    }
}