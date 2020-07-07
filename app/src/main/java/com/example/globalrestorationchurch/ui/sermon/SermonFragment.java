package com.example.globalrestorationchurch.ui.sermon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalrestorationchurch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SermonFragment extends Fragment {

//    private static final String API_KEY = "AIzaSyAkWwsGfbqMay48tO1xSWHEss9YQsW5f_o";
//    private static final String PLAYLISTS = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&playlistId=PL21rEW-r8cqeumA2WciZ77zUVEycwK-C7&key=" + API_KEY;
//    ArrayList<String> playlistItems;
    private SermonViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> videoUrls;
//    private String[] myDataset = {"pej", "poj", "D'banj", "Frank"};
    private static final String PLAYLISTVIDEOSTRING = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2C+id&playlistId=PL21rEW-r8cqeumA2WciZ77zUVEycwK-C7&key=AIzaSyAkWwsGfbqMay48tO1xSWHEss9YQsW5f_o&maxResults=20";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(SermonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sermon, container, false);
//        final TextView textView = root.findViewById(R.id.text_sermon);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        setUpRecycler(root);
        new TaskRunner().executeAsync(new LongRunningPlaylistTask(PLAYLISTVIDEOSTRING), (data) -> {

            setRecyclerAdapter(data);
        });

        return root;
    }

    private void setUpRecycler(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter(ArrayList<SermonDetails> details) {
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(details);
        recyclerView.setAdapter(mAdapter);
    }

}

class TaskRunner {
    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    //    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());
    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
    // Creates a thread pool manager
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue
    );

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        threadPoolExecutor.execute(() -> {
            final R result;
            try {
                result = callable.call();
                handler.post(() -> callback.onComplete(result));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    public interface Callback<R> {
        void onComplete(R result);
    }
}

class LongRunningPlaylistTask implements Callable<ArrayList<SermonDetails>> {
    private final String input;

    public LongRunningPlaylistTask(String input) {
        this.input = input;
    }

    @Override
    public ArrayList<SermonDetails> call() {
        // Some long running task
        String playlistJSON = doInBackground(input);
        return parseResult(playlistJSON);
    }

    private String doInBackground(String playlists) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(playlists);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
//                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
            }
            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ArrayList<SermonDetails> parseResult(String playlist) {
        ArrayList<SermonDetails> details = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(playlist);
            JSONArray style = jsonObject.getJSONArray("items");
            for (int i = 0; i < style.length(); i++) {

//                JSONObject content = style.getJSONObject(i).getJSONObject("contentDetails");
//                String id = content.getString("videoId");
                JSONObject snippet = style.getJSONObject(i).getJSONObject("snippet");
                String id = snippet.getJSONObject("resourceId").getString("videoId");
                String title = snippet.getString("title");
                String description = snippet.getString("description");
                String thumbnail = snippet.getJSONObject("thumbnails").getJSONObject("maxres").getString("url");
                String published = snippet.getString("publishedAt");

                details.add(new SermonDetails(id, title, description, thumbnail, published));
//                details.add(SermonFragment.getVideoURL(id));
            }

        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        return details;
    }
}