package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ajlyfe.lectureapp.Activity.TeacherClassView;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class TeacherClassCardAdapter extends RecyclerView.Adapter<TeacherClassCardAdapter.ViewHolder> {
    private List<TeacherClassCard> classList;
    private Context context;
    private Activity parentActivity;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;

    public TeacherClassCardAdapter(List<TeacherClassCard> classes, Context ctx, Activity parentActivity) {
        context = ctx;
        classList = classes;
        this.parentActivity = parentActivity;
        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, parentActivity);
        editor = preferences.edit();
    }

    /** Since the header is at position 0 in the RecyclerView, we must accommodate for it.
     *
     *  TLDR; Just use this method or the code WILL break.
     *
     *  onBindViewHolder loops through based on the size of our ArrayList.
     *  If we don't make our size, one more bigger than it needs to be, we will lose a class.
     */

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public TeacherClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == HEADER) {
            view = inflater.inflate(R.layout.overview_header, parent, false);
        } else {
            view = inflater.inflate(R.layout.class_card, parent, false);
        }
        return new TeacherClassCardAdapter.ViewHolder(view);
    }

    public void setClassList(List<TeacherClassCard> l){
        classList = l;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final TeacherClassCardAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();

        if (getItemViewType(viewHolder.getAdapterPosition()) != HEADER) {
            final TeacherClassCard mClass = classList.get(position);

            TextView title = viewHolder.classTitle;
            title.setText(mClass.getName());

            TextView classCount = viewHolder.classCount;
            classCount.setText(mClass.getDescription());

            ImageView classColor = viewHolder.classCardColor;
            classColor.setBackgroundColor(Utils.generateColor());

            CardView classCard = viewHolder.card;
            classCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeacherClassView.class);
                    intent.putExtra("CLASS_CLICKED", mClass.getName());
                    context.startActivity(intent);
                }
            });

            Toolbar mToolbar = viewHolder.toolbar;
            mToolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeacherClassView.class);
                    intent.putExtra("CLASS_CLICKED", mClass.getName());
                    context.startActivity(intent);
                }
            });

            // Add overflow menu
            mToolbar.inflateMenu(R.menu.teacher_class_card_menu);
            mToolbar.setOverflowIcon(context.getResources().getDrawable(R.drawable.overflow));
            final int finalPosition = position;
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Warning!")
                            .setMessage("You are about to permanently delete a class. Are you sure you " +
                                    "want to do this? This action can NOT be undone.")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.cancel();
                                }
                            })
                            .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int idk) {
                                    removeAt(finalPosition);

                                    Set<String> set = new HashSet<>();
                                    for (int x = 0; x < classList.size(); x++) {
                                        set.add(classList.get(x).getName());
                                    }
                                    editor.putStringSet("Key", set);
                                    editor.apply();

                                    Snackbar.make(parentActivity.findViewById(R.id.classOverviewLayout),
                                            "Deleted '" + mClass.getName() + "' successfully",
                                            Snackbar.LENGTH_LONG)
                                            .setAction("Dandy!", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) { /*ihateusers*/ }
                                            })
                                            .setActionTextColor(Color.parseColor("#FFFFC107"))
                                            .show();
                                }
                            }).show();
                    return true;
                }
            });
        }
    }

    public void removeAt(int position) {
        classList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, classList.size());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classTitle;
        TextView classCount;
        ImageView classCardColor;
        CardView card;
        Toolbar toolbar;

        public ViewHolder(View itemView) {
            super(itemView);

            this.toolbar = (Toolbar) itemView.findViewById(R.id.classCardToolbar);
            this.card = (CardView) itemView.findViewById(R.id.classCard);
            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classCount = (TextView) itemView.findViewById(R.id.classCardStudentCount);
            this.classCardColor = (ImageView) itemView.findViewById(R.id.classCardColor);
        }
    }
}
