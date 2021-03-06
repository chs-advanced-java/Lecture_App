package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.LectureCheckbox;
import ajlyfe.lectureapp.Adapters.LectureCheckboxAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentUpload extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_upload, container, false);

        // Edit the layout
        doThings();

        // Inflate the layout
        return view;
    }

    public void doThings() {
        ArrayList<LectureCheckbox> lecturesChecked = parseTemporaryArray(getArguments().getStringArrayList("lecturesCheckedOff"));

        RecyclerView recyclerViewLectures = (RecyclerView) view.findViewById(R.id.checkboxRecycler);
        LectureCheckboxAdapter adapter = new LectureCheckboxAdapter(lecturesChecked);
        recyclerViewLectures.setAdapter(adapter);
        recyclerViewLectures.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private ArrayList<LectureCheckbox> parseTemporaryArray(ArrayList<String> tArray) {
        ArrayList<LectureCheckbox> returnMe = new ArrayList<>();

        for (int i = 0; i < tArray.size(); i++) {
            returnMe.add(i, new LectureCheckbox(tArray.get(i)));
        }

        return returnMe;
    }
}