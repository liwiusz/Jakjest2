package mobstudio.jakjest.pomocnik;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import mobstudio.jakjest.R;

/**
 * Created by Michał on 2016-07-25.
 */
public class MiejscaPlaceAdapter extends ArrayAdapter<MiejscaPlace> {
    ArrayList<MiejscaPlace> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public MiejscaPlaceAdapter(Context context, int resource, ArrayList<MiejscaPlace> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvName = (TextView) v.findViewById(R.id.tvNazwa);
            //holder.tvAdres = (TextView) v.findViewById(R.id.tvAdres);
            //holder.tvKodMiasto = (TextView) v.findViewById(R.id.tvKodMiasto);
          //  holder.tvLike = (TextView) v.findViewById(R.id.tvLikes);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //holder.imageview.setImageResource(R.mipmap.ic_launcher); //?- nie wiem czy to jest potrzebne
        new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.tvName.setText(actorList.get(position).getName());
      //  holder.imageview.setImageBitmap(actorList.get(position).getImageView());
        //holder.tvAdres.setText(actorList.get(position).getAdres());
       // holder.tvKodMiasto.setText(actorList.get(position).getKod()+" "+ actorList.get(position).getMiasto());
     //   holder.tvLike.setText("Lubi to: "+actorList.get(position).getLike());


        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvAdres;
        public TextView tvKodMiasto;
        public TextView tvLike;
        public TextView gpsOdleglosc;

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


