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

package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentResult extends Fragment {

    private View view;
    private ArrayList<String> paths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_result, container, false);

        doShit();

        return view;
    }

    private void doShit() {
        paths = getArguments().getStringArrayList("paths");
    }

    public ArrayList<String> getPaths() {
        return paths;
    }
}