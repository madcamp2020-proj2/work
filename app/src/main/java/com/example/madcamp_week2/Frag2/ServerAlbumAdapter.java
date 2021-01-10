package com.example.madcamp_week2.Frag2;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madcamp_week2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.madcamp_week2.Frag2.Fragment2.initRetrofitClient;

public class ServerAlbumAdapter extends RecyclerView.Adapter<ServerAlbumAdapter.ViewHolder> {
    private ArrayList<String> urlList;
    private String BASE_URL = "http://192.249.18.236:3000";
    private Context context;

    ServerAlbumAdapter(ArrayList<String> data, Context activity) {
        urlList = data;
        context = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        FloatingActionButton deleteButton;
        FloatingActionButton downloadButton;
        String url;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            downloadButton = itemView.findViewById(R.id.downloadButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.url = urlList.get(position);
        Glide.with(holder.itemView.getContext())
                .load("http://192.249.18.236:3000" + holder.url)
                .into(holder.imageView);
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String url = "http://192.249.18.236:3000";
                Log.d("result: ", " : clicked download button");
                downloadImageNew("" + timestamp.getTime(), url + holder.url);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asdf", " : clicked delete button");
                deleteImage(holder.url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    private void deleteImage(String location) {
        ApiService apiService = initRetrofitClient();
        Call<ResponseBody> call = apiService.delImage(location);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Status: ", "connection complete");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Status: ", "connection fail");
            }
        });
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage) {
        try {
            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + filename + ".jpg");
            dm.enqueue(request);
        } catch (Exception e) {
            Log.d("result: ", "Downloads fail");
        }
    }
}
