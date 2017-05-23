package mobstudio.jakjest.miejsce;


import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.Profile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mobstudio.jakjest.R;
import mobstudio.jakjest.baza.JSONParser;
import mobstudio.jakjest.main.Profil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DodajKomentarz extends DialogFragment {


private String idK = null;
    private static String idUserFB = null;
    private static String user=null;
    private static String tekst = null;
    private static String gwiazda = null;
    private static String tlok = null;
    private static String inKlub = null;
    private static String parkiet = null;

    public DodajKomentarz() {
        // Required empty public constructor
    }
    public static DodajKomentarz newInstance() {
        DodajKomentarz frag = new DodajKomentarz();
        Bundle args = new Bundle();
      //  args.putInt("title", title);
       // frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
final View rootView = inflater.inflate(R.layout.fragment_dodaj_komentarz,null);

        final CheckBox checkBoxPrzestrzen = (CheckBox) rootView.findViewById(R.id.checkBoxPrzestrzen);
        final CheckBox checkBoxParkiet = (CheckBox) rootView.findViewById(R.id.checkBoxParkiet);
        final CheckBox checkBoxOcena = (CheckBox) rootView.findViewById(R.id.checkBoxOcena);
        final EditText editTextKomentarz = (EditText) rootView.findViewById(R.id.komentarzText);


        final SeekBar seekBarPrzestrzen = (SeekBar)rootView.findViewById(R.id.seekBarPrzestrzen);
        final SeekBar seekBarParkiet = (SeekBar)rootView.findViewById(R.id.seekBarParkiet);
        final RatingBar ratingBarOcena = (RatingBar)rootView.findViewById(R.id.ratingBarOcena);

        Button buttonZapisz = (Button)rootView.findViewById(R.id.saveKomentarz);
        Button buttonAnuluj = (Button)rootView.findViewById(R.id.anulujKomentarz);

        checkBoxPrzestrzen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxPrzestrzen.isChecked()) {
                    seekBarPrzestrzen.setVisibility(View.GONE);
                }
                else seekBarPrzestrzen.setVisibility(View.VISIBLE);
            }
        });
checkBoxParkiet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(checkBoxParkiet.isChecked())
        {
            seekBarParkiet.setVisibility(View.GONE);
        }
        else seekBarParkiet.setVisibility(View.VISIBLE);
    }
});
        checkBoxOcena.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
if(checkBoxOcena.isChecked())
{
    ratingBarOcena.setVisibility(View.GONE);
}
                else ratingBarOcena.setVisibility(View.VISIBLE);
            }
        });

        buttonZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextKomentarz.length() > 1) {
                    //validacja ok
                    tekst =editTextKomentarz.getText().toString();
                    gwiazda = String.valueOf(ratingBarOcena.getNumStars());


                            new JSONAsyncTask().execute();
                    getActivity().finish();
                } else {
                    //zlawalidacja
                    editTextKomentarz.setError("Wprowadź komentarz");
                }


            }
        });

        buttonAnuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return rootView;
    }
//TODO: poprawić działanie dodawanie komentarzy -> psuje się przy dodawaniu oraz brak wartości z okienka

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
            dialog2.setCancelable(true);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idK", "367825673405672"));
            params.add(new BasicNameValuePair("idUserFB", Profile.getCurrentProfile().getId()));
            params.add(new BasicNameValuePair("user", Profile.getCurrentProfile().getFirstName()));
            params.add(new BasicNameValuePair("tekst", tekst));
            params.add(new BasicNameValuePair("gwiazda", gwiazda));
            params.add(new BasicNameValuePair("parkiet", "1"));
            params.add(new BasicNameValuePair("tlok", "2"));
            params.add(new BasicNameValuePair("inKlub", "0"));

            try {
                //------------------>>
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.urlInsert));
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
               // HttpEntity httpEntity = httpResponse.getEntity();

                int status  = httpResponse.getStatusLine().getStatusCode();
                Log.d("Komentarz status: ",String.valueOf(status));
                if(status ==200) {

                    return true;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog2.cancel();
//
            if(result == false) {
                Toast.makeText(getActivity().getApplicationContext(), "Nie dodano komentarza", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "Dodano komentarz", Toast.LENGTH_LONG).show();

            }

        }
        }

}
