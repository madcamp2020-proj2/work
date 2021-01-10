
package com.example.madcamp_week2.Frag2;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.madcamp_week2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment2 extends Fragment {
    private static ApiService apiService;
    private FloatingActionButton load;
    private FloatingActionButton upload;
    private FloatingActionButton serverLoad;
    private FloatingActionButton multiLoad;
    private ImageView imageView;
    private byte[] imageData;
    private List<byte[]> imageGroup = new ArrayList();
    private int token = 0;
    ArrayList<String> urlList;

    public Fragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        load = (FloatingActionButton) view.findViewById(R.id.fab);
        upload = (FloatingActionButton) view.findViewById(R.id.fabUpload);
        serverLoad = (FloatingActionButton) view.findViewById(R.id.fabLoad);
        multiLoad = (FloatingActionButton) view.findViewById(R.id.fabMulti);
        imageView = view.findViewById(R.id.imageView);
        initRetrofitClient();

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getImageFromAlbum(), 1111);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postimage(imageData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        serverLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageUrl();
            }
        });

        multiLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postMultiImage(imageGroup, imageGroup.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            try {
                if (data.getClipData() == null) {
                    Uri a = data.getData();
                    InputStream is = getContext().getContentResolver().openInputStream(data.getData());
                    Glide.with(this).load(a.toString()).into(imageView);
                    imageData = getBytes(is);
                } else {
                    ClipData clipData = data.getClipData();
                    int dataNum = clipData.getItemCount();
                    for (int i = 0; i < dataNum; i++) {
                        InputStream is = getContext().getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                        imageGroup.add(getBytes(is));
                    }
                    Log.d("result: ", "선택한 사진 개수: " + clipData.getItemCount());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    private void postimage(byte[] data) throws IOException {
        RequestBody reqfile = RequestBody.create(MediaType.parse("image/*"), data);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", "asdf", reqfile);
        RequestBody name = RequestBody.create(MediaType.parse("image/png"), "upload");

        Call<ResponseBody> req = apiService.postImage(body, name);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("result: ", "Success");
                    Toast.makeText(getContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void postMultiImage(List<byte[]> dataGroup, int size) throws IOException {
        List<MultipartBody.Part> parts = new ArrayList();
        for (int i = 0; i < size; i++) {
            RequestBody reqfile = createPartFromByte(dataGroup.get(i));
            MultipartBody.Part part = prepareFilePart("uploading" + token, reqfile);
            Log.d("result: ", "" + token);
            parts.add(part);
        }
        token++;

        RequestBody name = RequestBody.create(MediaType.parse("image/png"), "upload");
        Call<ResponseBody> call = apiService.postMultiImage(parts, name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("result: ", "Success multi images");
                    Toast.makeText(getContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private RequestBody createPartFromByte(byte[] data) {
        return RequestBody.create(MediaType.parse("image/*"), data);
    }

    private MultipartBody.Part prepareFilePart(String partName, RequestBody reqfile) {
        return MultipartBody.Part.createFormData(partName, "asdf", reqfile);
    }

    private void loadImageUrl() {
        Call<List<Gets>> call = apiService.getImageUrl();
        call.enqueue(new Callback<List<Gets>>() {
            @Override
            public void onResponse(Call<List<Gets>> call, Response<List<Gets>> response) {
                urlList = new ArrayList<String>();
                List<Gets> getsList = response.body();
                for (Gets get : getsList) {
                    String content = "";
                    content += get.getLocation();
                    content += "/" + get.getName();
                    urlList.add(content);
                }
                Intent intent = new Intent(getContext(), ServerAlbum.class);
                intent.putExtra("urlData", urlList);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Gets>> call, Throwable t) {
                Log.d("result: ", "Connection error");
            }
        });
    }

    public static ApiService initRetrofitClient() {
        OkHttpClient client = new OkHttpClient();
        client.callTimeoutMillis();
        client.readTimeoutMillis();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.236:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    public Intent getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        return intent;
    }
}