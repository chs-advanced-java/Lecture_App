package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentTabStudents extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.fragment_tab_students, container, false);
    }
}
