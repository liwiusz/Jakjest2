package mobstudio.jakjest.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.widget.ProfilePictureView;

import mobstudio.jakjest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profil extends Fragment {


    public Profil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
//        ProfilePictureView profilePictureView;
//        profilePictureView = (ProfilePictureView) getActivity().findViewById(R.id.imageProfile);
//        profilePictureView.setProfileId("1380472078634078");

        return rootView;
    }

}
