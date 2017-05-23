package mobstudio.jakjest.miejsce;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobstudio.jakjest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoMiejsce extends Fragment {


    public InfoMiejsce() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_miejsce, container, false);
    }

}
