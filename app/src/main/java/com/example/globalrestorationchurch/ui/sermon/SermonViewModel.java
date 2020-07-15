package com.example.globalrestorationchurch.ui.sermon;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.globalrestorationchurch.MainActivity;

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
import java.util.List;
import java.util.concurrent.Callable;

public class SermonViewModel extends ViewModel {

    private static final String PLAYLISTID = "PL21rEW-r8cqeumA2WciZ77zUVEycwK-C7";
    private static final String PLAYLISTVIDEOSTRING = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet%2C+id&playlistId=" + PLAYLISTID + "&key=" + MainActivity.API_KEY + "&maxResults=20";
    private MutableLiveData<List<SermonDetails>> details;

    public LiveData<List<SermonDetails>> getUsers() {
        if (details == null) {
            details = new MutableLiveData<>();
            loadUsers();
        }
        return details;
    }

    private void loadUsers() {
        new TaskRunner().executeAsync(new LongRunningPlaylistTask(PLAYLISTVIDEOSTRING), (data) -> {
            details.setValue(data);
        });
    }

    static class LongRunningPlaylistTask implements Callable<ArrayList<SermonDetails>> {
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
                    JSONObject snippet = style.getJSONObject(i).getJSONObject("snippet");
                    String id = snippet.getJSONObject("resourceId").getString("videoId");
                    String title = snippet.getString("title");
                    String description = snippet.getString("description");
                    String thumbnail = snippet.getJSONObject("thumbnails").getJSONObject("maxres").getString("url");
                    String published = snippet.getString("publishedAt");

                    details.add(new SermonDetails(id, title, description, thumbnail, published));
                }
            } catch (JSONException err) {
                Log.e("Error", err.toString());
            }
            return details;
        }
    }
}

