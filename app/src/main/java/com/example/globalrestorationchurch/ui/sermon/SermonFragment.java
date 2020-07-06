package com.example.globalrestorationchurch.ui.sermon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalrestorationchurch.MainActivity;
import com.example.globalrestorationchurch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class SermonFragment extends Fragment {

    private SermonViewModel homeViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] myDataset = {"pej", "poj", "D'banj", "Frank"};

    private static final String API_KEY = "AIzaSyAkWwsGfbqMay48tO1xSWHEss9YQsW5f_o";
    private static final String PLAYLISTS = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&playlistId=PL21rEW-r8cqeumA2WciZ77zUVEycwK-C7&key=" + API_KEY;


//    private SermonDetails[] myDataset = {};
    static String getVideoURL(String id) {
        return "https://www.googleapis.com/youtube/v3/videos?id=" + id + "&part=snippet,contentDetails,statistics,status&key=" + API_KEY;
    }


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


        new JsonTask().execute(PLAYLISTS);


        setUpRecycler(root);

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

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }


}

class JsonTask extends AsyncTask<String, String, String> {

    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
//                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        Log.e("tag", result);
        ArrayList<String> temp = parseResult(result);
    }

    private ArrayList<String> parseResult(String playlist) {
        ArrayList<String> details = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(playlist);
            JSONArray style = jsonObject.getJSONArray("items");
            for (int i = 0; i < style.length(); i++) {

                JSONObject content = style.getJSONObject(i).getJSONObject("contentDetails");
                String id = content.getString("videoId");
                Log.e("e", SermonFragment.getVideoURL(id));

                details.add(SermonFragment.getVideoURL(id));
            }


        } catch (JSONException err){
            Log.d("Error", err.toString());
        }
        return details;
    }

}
