package ajlyfe.lectureapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.ClassSelectCard;
import ajlyfe.lectureapp.Adapters.ClassSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentClass extends Fragment {
    private View view;
    private ClassSelectCardAdapter adapter;
    public ArrayList<ClassSelectCard> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_class, container, false);

        method(getActivity());

        return view;
    }
    public void method(Activity activity) {

        /**pull classes here, use for loop to convert names to classList**/
        ArrayList<TeacherClassCard> temp = Utils.getClassList(activity);
        ArrayList<ClassSelectCard> classes = new ArrayList<>();
        try{
            for (int x = 1; x < temp.size(); x++){
                classes.add(new ClassSelectCard(temp.get(x).getName()));
            }
        }
        catch (IndexOutOfBoundsException exc){

        }

        RecyclerView recyclerViewStudents = (RecyclerView) view.findViewById(R.id.recyclerViewClassSelect);
        try {
            adapter = new ClassSelectCardAdapter(classes, view.getContext());
            list = adapter.getArrayList();
            recyclerViewStudents.setAdapter(adapter);
            recyclerViewStudents.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        catch (NullPointerException exc) {
            Toast.makeText(getContext(), "Error, User has no classes.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<ClassSelectCard> getAdapterArrayList(){
        try {
            return list;
        }
        catch(NullPointerException exc) {
            return new ArrayList<>();
        }
    }
}