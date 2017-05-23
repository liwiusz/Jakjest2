package mobstudio.jakjest.miejsce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

import mobstudio.jakjest.R;


public class KomentarzeAdapter extends ArrayAdapter<Komentarze> {

    ArrayList<Komentarze> komentarzeList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public KomentarzeAdapter(Context context, int resource, ArrayList<Komentarze> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        komentarzeList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvUser = (TextView) v.findViewById(R.id.tvUser);
            holder.tvKomentarz = (TextView) v.findViewById(R.id.tvKomentarz);
            holder.tvData = (TextView) v.findViewById(R.id.tvData);
            holder.profilePictureView = (ProfilePictureView)v.findViewById(R.id.profilFB);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tvUser.setText(komentarzeList.get(position).getUser());
        holder.tvData.setText(komentarzeList.get(position).getCzas());
        holder.tvKomentarz.setText(komentarzeList.get(position).getTekst());
        holder.profilePictureView.setProfileId(komentarzeList.get(position).getIdUserFB());

        return v;

    }

    static class ViewHolder {
        public ImageView imageview;


        public TextView tvUser;
        public TextView tvData;
        public TextView tvKomentarz;
        public ProfilePictureView profilePictureView;


    }
}
