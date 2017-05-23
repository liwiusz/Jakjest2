package mobstudio.jakjest.main;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mobstudio.jakjest.R;
import mobstudio.jakjest.miejsce.Komentarze;
import mobstudio.jakjest.miejsce.Miejsce;
import mobstudio.jakjest.pomocnik.MiejscaPlace;
import mobstudio.jakjest.pomocnik.MiejscaPlaceAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Miejsca extends Fragment {

   ArrayList<MiejscaPlace> miejscaList;
    MiejscaPlaceAdapter adapter;
        ListView listView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_miejsca, container, false);
       miejscaList = new ArrayList<MiejscaPlace>();

        new JSONAsyncTask().execute("http://liwiuszweb.cba.pl/getallklubs.php");

        adapter = new MiejscaPlaceAdapter(getActivity().getApplicationContext(), R.layout.miejsca_list, miejscaList);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
adapter.notifyDataSetChanged();


        Log.d("Test 123:",String.valueOf(miejscaList.size()));

        return rootView;
    }

    public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog2;
        JSONObject object;


        //
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(getActivity());
            dialog2.setMessage("Loading, please wait");
            dialog2.setTitle("Connecting server");
            dialog2.show();
            dialog2.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currentDateandTime = sdf.format(new Date());
            try {


                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);


                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("klub");
 String idFacebook ="";


                    for (int i = 0; i < jarray.length(); i++) {
                        object = jarray.getJSONObject(i);
                        idFacebook="/"+object.get("idFacebook").toString();

                        GraphRequest request = GraphRequest.newGraphPathRequest(
                                AccessToken.getCurrentAccessToken(),
                                idFacebook,
                                new GraphRequest.Callback() {
                                    @Override
                                    public void onCompleted(GraphResponse response) {

                                        JSONObject jsonObject2 = response.getJSONObject();
                                        MiejscaPlace klub = new MiejscaPlace();
                                        try {
                                            JSONArray jarray = jsonObject2.getJSONObject("events").getJSONArray("data");
                                            String min =null;
                                            String idE="";
                                              for (int i = 0; i < jarray.length(); i++) {
                                                  JSONObject object = jarray.getJSONObject(i);
                                                  String start = object.getString("start_time");
                                                //  String end = object.getString("end_time");
                                                  Log.d("TESTY:", object.getString("start_time"));
                                                  if (min == null) {
                                                      min = start;
                                                      Log.d("TESTY:", "min = null");
                                                        klub.setIdEventFirst("BRAK");
                                                  }
                                                  if (currentDateandTime.compareTo(start) < 0 && min.compareTo(start) > 0)
                                                  {
                                                      Log.d("TESTY:","nowe min "+object.getString("start_time") );
                                                      Log.d("TESTY:","nowe min "+object.getString("id") );
                                                      idE = object.getString("id");
                                                      klub.setIdEventFirst(object.getString("id"));
                                                  }

                                              }




                                            klub.setName(jsonObject2.get("name").toString());
                                            klub.setId(jsonObject2.get("id").toString());
                                         //   klub.setImage(jsonObject2.get("name").toString());
                                            klub.setMapX(jsonObject2.getJSONObject("location").getDouble("latitude"));
                                            klub.setMapY(jsonObject2.getJSONObject("location").getDouble("longitude"));
                                           klub.setImage( jsonObject2.getJSONObject("picture").getJSONObject("data").getString("url"));

                                            miejscaList.add(klub);

                                            adapter = new MiejscaPlaceAdapter(getActivity().getApplicationContext(), R.layout.miejsca_list, miejscaList);
                                            listView.setAdapter(adapter);
                                            final String finalIdE = idE;
                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                        long id) {
                                    Intent intent = new Intent(getActivity().getApplicationContext(), Miejsce.class);
                                    // TODO Auto-generated method stub
                                    Toast.makeText(getActivity().getApplicationContext(), miejscaList.get(position).getName(), Toast.LENGTH_LONG).show();
                                    intent.putExtra("IDK", miejscaList.get(position).getId());
                                    intent.putExtra("NAZWA", miejscaList.get(position).getName());
                                    intent.putExtra("IDEVENT",miejscaList.get(position).getIdEventFirst());
Log.d("TESTY id", miejscaList.get(position).getIdEventFirst());
                                   startActivity(intent);
                                }
                            });
                                            Log.d("Test:",String.valueOf(miejscaList.size()));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }



                                    }                        }

                        );


                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "events{start_time,end_time},location,id,name,picture");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }


                    return true;
                }

                //------------------>>
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {


                dialog2.dismiss();
              adapter.notifyDataSetChanged();
if(result==true)
{
    Log.d("Test:",String.valueOf(miejscaList.size()));
}

            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }

    }

}
