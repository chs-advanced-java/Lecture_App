package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ajlyfe.lectureapp.R;

public class FragmentTabFiles extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_files, container, false);

        // Edit the layout
        editLayout();

        // Inflate the layout
        return view;
    }

    private void editLayout() {
        //Yay
    }
}