package com.example.madcamp_week2.Frag1;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.madcamp_week2.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.http.Url;

public class Fragment1 extends Fragment {
    private RecyclerView recyclerView;
    private FragmentAdapter adapter;
    private ArrayList<Contacts> list = ContactsList.getInstance();
    Button loadBtn;
    Contacts item;

//    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private Boolean isPermission = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
//        loadBtn = (Button) rootView.findViewById(R.id.button1);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        list = Contacts.createContactsList(getActivity());
        AscendingName ascending = new AscendingName();

        TextView update = rootView.findViewById(R.id.update);

        Button uploadbtn = (Button)rootView.findViewById(R.id.upload);
        uploadbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://192.249.18.210:3000/post");
            }
        });

        Button downloadbtn = (Button)rootView.findViewById(R.id.download);
        downloadbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new JSONTask().execute("http://192.249.18.210:3000/receive");
            }
        });



        //리사이클러뷰 나누는 선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //여기서 뭔가..잘못된 것 같다..
        //list가 fragment에만 있어서 contact세트 다 모아놓은거
        Bundle bundle = getArguments();
        if (bundle == null) {
            if (list.isEmpty())
                list = Contacts.createContactsList(list, getActivity(), null);
            Log.i("Fragment1 item", "item null");
        } else {
            item = bundle.getParcelable("Contacts");
//            Log.i("Fragment1 item", item.nickname);
            list = Contacts.modifyContactsList(list, getActivity(), item);
//            Log.i("Fragment1 item", item.nickname);

        }
        Collections.sort(list, ascending);
        //어댑터에 contact세트 리스트를 넣는다..
        adapter = new FragmentAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        Log.i("Frag", "MainFragment");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        Intent intent = new Intent(getActivity(), ContactsEditActivity.class);
//        startActivity(intent);
//        loadBtn = (Button) rootView.findViewById(R.id.button1);
        return rootView;
    }

    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            if(urls[0].equals("http://192.249.18.210:3000/post")){
                int i = 0;
                while(list.size()>=i) {
                    try {
                        //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("tel", list.get(i).phNumbers);
                        jsonObject.accumulate("name", list.get(i).name);
                        jsonObject.accumulate("nick", list.get(i).nickname);

                        HttpURLConnection con = null;
                        BufferedReader reader = null;

                        try {
                            //URL url = new URL("http://192.168.25.16:3000/users");
                            URL url = new URL(urls[0]);
                            //연결을 함
                            con = (HttpURLConnection) url.openConnection();

                            con.setRequestMethod("POST");//POST방식으로 보냄
                            con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                            con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송


                            con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                            con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                            con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                            con.connect();

                            //서버로 보내기위해서 스트림 만듬
                            OutputStream outStream = con.getOutputStream();
                            //버퍼를 생성하고 넣음
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                            writer.write(jsonObject.toString());
                            writer.flush();
                            writer.close();//버퍼를 받아줌

                            //서버로 부터 데이터를 받음
                            InputStream stream = con.getInputStream();

                            reader = new BufferedReader(new InputStreamReader(stream));

                            StringBuffer buffer = new StringBuffer();

                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                buffer.append(line);
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (con != null) {
                                con.disconnect();
                            }
                            try {
                                if (reader != null) {
                                    reader.close();//버퍼를 닫아줌
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }

            }else if(urls[0].equals("http://192.249.18.210:3000/receive")){
                try{
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try{
                        URL url = new URL(urls[0]);
                        con = (HttpURLConnection) url.openConnection();

                        con.setRequestMethod("POST");
                        con.setRequestProperty("Cache-Control", "no-cache");
                        con.setRequestProperty("Content=Type", "application/json");

                        con.setRequestProperty("Accept", "text/html");
                        con.setDoOutput(false);
                        con.setDoInput(true);
                        con.connect();

                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();

                        String line = "";
                        while((line = reader.readLine()) != null){
                            buffer.append(line);
                        }

                        jsonParsing(buffer.toString());

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            return null;
            }

    }

    private void jsonParsing(String json){
        try{
            ArrayList<Contacts> list = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("contact");

            for(int i=0;i<contactArray.length();i++){
                JSONObject contactObject = contactArray.getJSONObject(i);

                Contacts contacts;
                contacts = new Contacts(contactObject.getString("name"), contactObject.getString("tel"), contactObject.getString("nick"), null, null);

                list.add(contacts);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class AscendingName implements Comparator<Contacts> {

        @Override
        public int compare(Contacts o1, Contacts o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}
