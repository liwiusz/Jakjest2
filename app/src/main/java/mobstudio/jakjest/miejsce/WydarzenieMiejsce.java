package mobstudio.jakjest.miejsce;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import mobstudio.jakjest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WydarzenieMiejsce extends Fragment {

String id ="";
    public WydarzenieMiejsce() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
Log.d("TESTY:",intent.getStringExtra("IDK"));

            id = "/"+intent.getStringExtra("IDEVENT")+"/";//intent.getStringExtra("IDK");;
if(!id.equals("/BRAK/"))
                new JSONAsyncTask().execute(id);


        View rootView= inflater.inflate(R.layout.fragment_wydarzenie_miejsce, container, false);
return rootView;
    }

    public class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        String dateString="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String formatedDate="";
        Date date = null;

        String dateString2="";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String formatedDate2="";
        Date date2 = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            Log.d("FACEBOOK TESTY KURWA", urls[0].toString());
            GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    id,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {

                            TextView eventName = (TextView) getActivity().findViewById(R.id.eventName);
                            TextView eventStart = (TextView) getActivity().findViewById(R.id.eventTimeStart);
                            TextView eventEnd = (TextView) getActivity().findViewById(R.id.eventTimeEnd);
                            TextView eventDescription = (TextView) getActivity().findViewById(R.id.eventOpis);
                            TextView eventZaproszono = (TextView) getActivity().findViewById(R.id.eventZaproszono);
                            TextView eventWezme = (TextView) getActivity().findViewById(R.id.eventWezmeUdzial);
                            ImageView imageView = (ImageView) getActivity().findViewById(R.id.eventImage);
                            TextView eventData = (TextView) getActivity().findViewById(R.id.eventTimeDate);

                            String nazwaE = "", startE = "", endE = "", opisE = "", wezmeE = "", zaproszonoE = "", fotoE = "";


                            Bitmap mIcon11 = null;
                            try {
                                JSONObject object = response.getJSONObject();
                                new DownloadImageTask(imageView).execute(object.getJSONObject("cover").getString("source"));


                                    dateString = object.getString("start_time");
                                    date = dateFormat.parse(dateString);
                                    dateFormat = new SimpleDateFormat("HH:mm");
                                    formatedDate = dateFormat.format(date);
                                eventStart.setText("od " + formatedDate);
                                    dateFormat = new SimpleDateFormat("dd MMMM");
                                    formatedDate = dateFormat.format(date);
                                eventData.setText(formatedDate);

                                    dateString2 = object.getString("end_time");
                                    date2 = dateFormat2.parse(dateString2);
                                    dateFormat2 = new SimpleDateFormat("HH:mm");
                                    formatedDate2 = dateFormat2.format(date2);






                                    eventZaproszono.setText(object.getString("noreply_count"));
                                    eventWezme.setText(object.getString("attending_count"));

                                    eventEnd.setText("do " + formatedDate2);

                                    eventName.setText(object.getString("name"));
                                    eventDescription.setText(object.getString("description"));



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }

                        });


            Bundle parameters = new Bundle();
            parameters.putString("fields", "cover,attending_count,declined_count,guest_list_enabled,name,end_time,start_time,description,interested_count,maybe_count,noreply_count,ticket_uri,timezone");
            request.setParameters(parameters);
            request.executeAsync();
            return true;

        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();

            if(result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
}}
