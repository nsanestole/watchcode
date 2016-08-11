package com.diplom.service;


import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;
import com.diplom.model.VimeoVideo;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stole on 4/27/2016.
 */
@Service
public class VideoService {
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT,JSON_FACTORY, new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
        }
    }).setApplicationName("codewatch").build();

    public Vimeo vimeo = new Vimeo("3221dc5b76abfa861d287d11b821a3fa");

    public List<SearchResult> searchYoutube(String queryTerm,Integer resultSize) throws IOException {
        YouTube.Search.List search = youtube.search().list("id,snippet");

        // Set your developer key from the {{ Google Cloud Console }} for
        // non-authenticated requests. See:
        // {{ https://cloud.google.com/console }}
        String apiKey = "AIzaSyAk3XKgfAnGHS8G-WATnb-rLCwS0wxid_Q";
        search.setKey(apiKey);
        search.setQ(queryTerm);

        // Restrict the search results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type
        search.setType("video");

        // To increase efficiency, only retrieve the fields that the
        // application uses.
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/high/url,snippet/description)");
        search.setMaxResults(Long.valueOf(resultSize));

        // Call the API and print results.
        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();

    }

    public List<VimeoVideo> searchVimeo(String queryTerm, Integer resultSize) throws Exception {
        VimeoResponse response = vimeo.searchVideos(queryTerm.replaceAll(" ","_"),"1",resultSize.toString());
        JSONArray rez = response.getJson().getJSONArray("data");
        List<VimeoVideo> rezList = new ArrayList<>();
        for(int i = 0; i < rez.length(); i++)
        {
            JSONObject video = rez.getJSONObject(i);
            VimeoVideo temp = new VimeoVideo();
            temp.setVideoUri(video.getString("uri"));
            temp.setName(video.getString("name"));
            String desc;
            try {
                desc = video.getString("description");
            }
            catch (JSONException e)
            {
                desc = "Sorry. No description avaliable.";
            }
            if(desc.length() > 150)
            {
                desc = desc.substring(0,149);
            }
            temp.setDescription(desc);
            temp.setImageUri(video.getJSONObject("pictures").getJSONArray("sizes").getJSONObject(3).getString("link"));
            rezList.add(temp);
        }
        return rezList;
    }

    public Video getVideoYoutube(String id) throws IOException {
        VideoListResponse response = youtube.videos().list("snippet").setKey("AIzaSyAk3XKgfAnGHS8G-WATnb-rLCwS0wxid_Q").setId(id).execute();
        List<Video> videoList = response.getItems();
        if(!videoList.isEmpty())
        {
            return videoList.get(0);
        }
        return null;
    }

    public List<SearchResult> relatedYoutube(String id) throws IOException {
        YouTube.Search.List search = youtube.search().list("id,snippet");

        // Set your developer key from the {{ Google Cloud Console }} for
        // non-authenticated requests. See:
        // {{ https://cloud.google.com/console }}
        String apiKey = "AIzaSyAk3XKgfAnGHS8G-WATnb-rLCwS0wxid_Q";
        search.setKey(apiKey);
        search.setRelatedToVideoId(id);

        // Restrict the search results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type
        search.setType("video");

        // To increase efficiency, only retrieve the fields that the
        // application uses.
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/medium/url,snippet/description)");
        search.setMaxResults((long) 4);

        // Call the API and print results.
        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();
    }

    public VimeoVideo getVideoVimeo(String id) throws IOException {
        VimeoResponse response = vimeo.getVideoInfo("https://api.vimeo.com" + id);
        VimeoVideo temp = new VimeoVideo();

        JSONObject video = response.getJson();
        temp.setVideoUri(video.getString("uri"));
        temp.setName(video.getString("name"));
        String desc;
        try {
            desc = video.getString("description");
        }
        catch (JSONException e)
        {
            desc = "Sorry. No description avaliable.";
        }

        return temp;
    }

    public List<VimeoVideo> relatedVimeo(String id) throws IOException {
        VimeoResponse response = vimeo.getVideoInfo("https://api.vimeo.com" + id + "/videos?page=1&per_page=4&filter=related");
        JSONArray rez = response.getJson().getJSONArray("data");
        List<VimeoVideo> rezList = new ArrayList<>();
        for(int i = 0; i < rez.length(); i++)
        {
            JSONObject video = rez.getJSONObject(i);
            VimeoVideo temp = new VimeoVideo();
            temp.setVideoUri(video.getString("uri"));
            temp.setName(video.getString("name"));
            String desc;
            try {
                desc = video.getString("description");
            }
            catch (JSONException e)
            {
                desc = "Sorry. No description avaliable.";
            }
            if(desc.length() > 150)
            {
                desc = desc.substring(0,149);
            }
            temp.setDescription(desc);
            temp.setImageUri(video.getJSONObject("pictures").getJSONArray("sizes").getJSONObject(3).getString("link"));
            rezList.add(temp);
        }
        return rezList;
    }

}
