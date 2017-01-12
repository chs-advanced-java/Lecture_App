/*
 * Copyright (c) 2017 RecoLecture.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class StudentCardAdapter extends RecyclerView.Adapter<StudentCardAdapter.ViewHolder> {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private ArrayList<? extends StudentCard> studentList;

    private View parentView;

    public StudentCardAdapter(@NonNull ArrayList<? extends StudentCard> studentList, View parentView) {
        this.studentList = studentList;
        this.parentView = parentView;
        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, parentView.getContext());
        editor = preferences.edit();

        if (studentList.size() == 0) {
            parentView.findViewById(R.id.noStudents).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public StudentCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View card = inflater.inflate(R.layout.student_card, parent, false);

        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final StudentCardAdapter.ViewHolder viewHolder, final int position) {
        final StudentCard student = studentList.get(position);

        TextView name = viewHolder.studentName;
        name.setText(student.getName());

        TextView date = viewHolder.dateJoined;
        date.setText("Joined class: " + student.getDateJoined());

        ImageView stalk = viewHolder.stalkStudent;
        stalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parentView.getContext(), "email: " + student.getEmail(), Toast.LENGTH_SHORT).show();
            }
        });

        final ImageView kill = viewHolder.killStudent;
        kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar comeGetYourSnacks = Snackbar.make(parentView.findViewById(R.id.manageStudentsRecyclerView),
                        "Successfully removed " + student.getName() + " from class!",
                        Snackbar.LENGTH_LONG);

                comeGetYourSnacks.show();
                removeAt(position);
            }
        });
    }

    private void removeAt(int position) {
        studentList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, studentList.size());

        if (studentList.size() == 0) {
            parentView.findViewById(R.id.noStudents).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() { return studentList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView dateJoined;
        ImageView stalkStudent;
        ImageView killStudent;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentName = (TextView) itemView.findViewById(R.id.studentName);
            this.dateJoined = (TextView) itemView.findViewById(R.id.dateJoined);
            this.stalkStudent = (ImageView) itemView.findViewById(R.id.stalkStudent);
            this.killStudent = (ImageView) itemView.findViewById(R.id.killStudent);
        }
    }
}