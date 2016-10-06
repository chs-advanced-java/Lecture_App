package ajlyfe.lectureapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.R;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views from the inflated layout
public class LectureCardAdapter extends RecyclerView.Adapter<LectureCardAdapter.ViewHolder> {

    // Store objects to store data
    private List<LectureCard> lectureList;

    // Pass in the predefined Array into the constructor
    public LectureCardAdapter(@NonNull List<LectureCard> lectures) {
        lectureList = lectures;
    }

    @Override
    public LectureCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View card = inflater.inflate(R.layout.lecture_card, parent, false);

        // Return a new holder instance
        return new ViewHolder(card);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(LectureCardAdapter.ViewHolder viewHolder, int position) {
        // Get the object and it's data based on position
        LectureCard lecture = lectureList.get(position);

        // Define the views from ViewHolder up here to play with them and do things! yasss
        TextView title = viewHolder.lectureTitle;
        title.setText(lecture.getLectureName());

        TextView teacher = viewHolder.lectureTeacher;
        teacher.setText(lecture.getTeacherName());
    }

    // Returns the total count of items in the list probably won't use this but @Overrides 4 dayz
    @Override
    public int getItemCount() { return lectureList.size(); }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        // These will be referenced every time up in the BindViewHolder
        TextView lectureTitle;
        TextView lectureTeacher;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.lectureTitle = (TextView) itemView.findViewById(R.id.lectureName);
            this.lectureTeacher = (TextView) itemView.findViewById(R.id.lectureTeacher);
        }
    }
}