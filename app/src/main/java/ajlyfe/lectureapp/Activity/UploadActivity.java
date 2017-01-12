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

package ajlyfe.lectureapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.ClassSelectCard;
import ajlyfe.lectureapp.Adapters.LectureSelectCard;
import ajlyfe.lectureapp.Fragment.FragmentClass;
import ajlyfe.lectureapp.Fragment.FragmentFile;
import ajlyfe.lectureapp.Fragment.FragmentResult;
import ajlyfe.lectureapp.Fragment.FragmentStudents;
import ajlyfe.lectureapp.Fragment.FragmentUpload;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class UploadActivity extends AppIntro {

    private Fragment fragmentUpload;
    private Fragment fragmentResult;
    FragmentFile file;
    FragmentClass classes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DO NOT WRITE -> setContentView(R.layout.activity_upload);
        file = new FragmentFile();
        classes = new FragmentClass();
        Fragment fragmentFile = file;
        Fragment fragmentClass = classes;
        Fragment fragmentStudents = new FragmentStudents();
        fragmentUpload = new FragmentUpload();
        fragmentResult = new FragmentResult();

        addSlide(fragmentFile);
        addSlide(fragmentClass);
        addSlide(fragmentStudents);
        addSlide(fragmentUpload);
        addSlide(fragmentResult);

        setSwipeLock(true);

        showSkipButton(false);
        setProgressButtonEnabled(true);     // Reveal the done button and next arrow
        setImageNextButton(null);           // Purge next button
        nextButton.setClickable(false);     // Banish next button
                                            // Done button still stays and works

        setColorDoneText(this.getResources().getColor(R.color.colorAccent));
        setSeparatorColor(Color.TRANSPARENT);
        setIndicatorColor(R.color.colorPrimary50, R.color.colorPrimary20);

        setVibrate(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, TeacherMainActivity.class));
    }

    @Override
    public void onSlideChanged(@Nullable final Fragment oldFragment, @Nullable final Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

        if (newFragment != null) {
            final int slideNumber = newFragment.getTag().charAt(newFragment.getTag().length() - 1) - 47;
            final ArrayList<String> paths = new ArrayList<>();
            Button next;

            switch (slideNumber) {
                case 1:
                    final Activity activity1 = newFragment.getActivity();
                    next = (Button) activity1.findViewById(R.id.uploadFileButton);

                    final ArrayList<LectureSelectCard> lectureCheckboxes = file.getAdapterArrayList();
                    final ArrayList<String> lecturesChecked = new ArrayList<>();


                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkedSomething = false;

                            for (int i = 0; i < lectureCheckboxes.size(); i++) {
                                if (lectureCheckboxes.get(i).getChecked()) {
                                    checkedSomething = true;
                                    lecturesChecked.add(lectureCheckboxes.get(i).getFileName());
                                    paths.add(Utils.getLecturePath() + lectureCheckboxes.get(i).getFileName());
                                }
                            }

                            Bundle args = new Bundle();
                            args.putStringArrayList("lecturesCheckedOff", lecturesChecked);
                            fragmentUpload.setArguments(args);

                            Bundle args2 = new Bundle();
                            args2.putStringArrayList("paths", paths);
                            fragmentResult.setArguments(args2);

                            if (checkedSomething) {
                                pager.setCurrentItem(1);
                            } else {
                                Toast.makeText(activity1, "Please select a lecture", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;


                case 2:
                    final Activity activity2 = newFragment.getActivity();
                    next = (Button) activity2.findViewById(R.id.uploadClassButton);
                    final ArrayList<ClassSelectCard> classCheckboxes = classes.getAdapterArrayList();

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkedSomething = false;

                            for (int i = 0; i < classCheckboxes.size(); i++) {
                                if (classCheckboxes.get(i).getChecked()) {
                                    checkedSomething = true;
                                }
                            }

                            if (checkedSomething) {
                                pager.setCurrentItem(2);
                            } else {
                                Toast.makeText(activity2, "Please select a class.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 3:
                    final Activity activity3 = newFragment.getActivity();
                    next = (Button) activity3.findViewById(R.id.uploadStudentButton);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pager.setCurrentItem(3);
                        }
                    });
                    break;

                case 4:
                    final Activity activity4 = newFragment.getActivity();
                    Button finishButton = (Button) activity4.findViewById(R.id.finishButton);

                    finishButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: Only checks if one of the shits is checked
                            CheckBox checkBox = (CheckBox) activity4.findViewById(R.id.lectureCheckboxCheckbox);

                            if (checkBox.isChecked()) {
                                final ProgressDialog dialog = ProgressDialog.show(UploadActivity.this, "", "Uploading File...", true);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //creating new thread to handle Http Operations
                                        for (String path : fragmentResult.getArguments().getStringArrayList("paths")) {
                                            uploadFile(path, dialog, activity4);
                                        }
                                    }
                                }).start();
                                pager.setCurrentItem(slideNumber);
                            } else {
                                Toast.makeText(activity4, "Please confirm all of the above", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 5:
                    final Activity activity5 = newFragment.getActivity();
                    break;
            }
        }
    }

    private int uploadFile(final String path, final ProgressDialog dialog, final Activity activity) {
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File selectedFile = new File(path);


        String[] parts = path.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(), "Source File Doesn't Exist: " + path, Toast.LENGTH_SHORT).show();
                }
            });

            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL("http://www.chs.mcvsd.org/sandbox/UploadToServer.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true); //Allow Inputs
                connection.setDoOutput(true); //Allow Outputs
                connection.setUseCaches(false); //Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", path);

                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + path + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0){
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(activity.getClass().getSimpleName(), "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "File Upload completed.\n" +
                                    "\n" +
                                    " You can see the uploaded file here: \n" +
                                    "\n" +
                                    "\" + \"http://www.chs.mcvsd.org/sandbox/lectures/\""+ fileName, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
            return serverResponseCode;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
