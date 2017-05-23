package mobstudio.jakjest.pomocnik;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by mliwi on 22.09.2016.
 */

//TODO: Dokończyć odzyskiwanie id klubu -> pobiera ładnie ale zwraca kijowo
public class Pomocnik {



    public void setA(String a) {
        this.a = a;
    }

    String a;

    public  void getTodayEvent(String idKlub, Intent intent)
{

    new GetEventID(intent).execute(idKlub);


}

    public class GetEventID extends AsyncTask<String, Void, String> {

        Intent intent2;
        String id="";
GetEventID(Intent intent)
{
    intent2 = intent;
}


        @Override
        protected String doInBackground(String... params) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String currentDateandTime = sdf.format(new Date());
Log.d("Pom params",params[0]);

            GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/"+params[0],
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            Log.d("Pomocnik:",String.valueOf(response));
                            try {
                                JSONObject jsonObject = response.getJSONObject();
                                JSONArray jarray = jsonObject.getJSONObject("events").getJSONArray("data");
                                String min ="";
                                id="";
                                for (int i = 0; i < jarray.length(); i++) {


                                    JSONObject object = jarray.getJSONObject(i);

                                    String start = object.getString("start_time");
                                    String end = object.getString("end_time");
                                    if(min==null)
                                    {
                                        min = start;
                                    }
                                    if(currentDateandTime.compareTo(start)<0 && min.compareTo(start)<0)
                                    {
                                      //  Log.d("Pomocnik",start);
                                       // Log.d("Pomocnik",String.valueOf(object.getString("id")));
                                        id=object.getString("id").toString();
                                    }
                                }
                            Log.d("Pom wynik id ok",id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }

            );

            Bundle parameters = new Bundle();
            parameters.putString("fields", "events{start_time,end_time}");
            request.setParameters(parameters);
            request.executeAsync();


            Log.d("Pomocnik:",String.valueOf(currentDateandTime));
            Log.d("Pom params id",id);
            return id;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Pomocnik wynik:","co to");
        }

    }

}
