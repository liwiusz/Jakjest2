package mobstudio.jakjest.miejsce;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;

import mobstudio.jakjest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KomentarzeMiejsce extends Fragment {

    private static final String ID = "ID";
    ArrayList<Komentarze> komentarzeList;

    KomentarzeAdapter adapter;
    public KomentarzeMiejsce() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View rootView = inflater.inflate(R.layout.fragment_komentarze_miejsce, container, false);
        // Inflate the layout for this fragment
        komentarzeList = new ArrayList<Komentarze>();
String id="367825673405672";
        new JSONAsyncTask().execute(getString(R.string.url) + "get_klubs.php?idK=" + id);
//jak to zrobić na id klubu !! ???
        ListView listview = (ListView)rootView.findViewById(R.id.list2);
        adapter = new KomentarzeAdapter(getActivity().getApplicationContext(), R.layout.komentarz_row, komentarzeList);
        listview.setAdapter(adapter);

        ImageButton buttonDodaj = (ImageButton) rootView.findViewById(R.id.buttonDodajKomentarz);
        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


      return rootView;
    }

    public void showDialog()
    {
        FragmentManager fm = getActivity().getFragmentManager();
        DodajKomentarz dialog = new DodajKomentarz();
        dialog.show(fm, "DIALOG_DATE");



    }

    public class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog2;
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
            try {



                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("komentarze");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Komentarze komentarz = new Komentarze();

                        komentarz.setUser(object.getString("user"));
                        komentarz.setTekst(object.getString("tekst"));
                        komentarz.setCzas(object.getString("czas"));
                        komentarz.setGwiazda(object.getString("gwiazda"));
                        komentarz.setIdUserFB(object.getString("idUserFB"));
                        komentarz.setInKlub(object.getString("inKlub"));
                        komentarz.setTlok(object.getString("tlok"));
                        komentarz.setParkiet(object.getString("parkiet"));
                        komentarzeList.add(komentarz);
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
            dialog2.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
