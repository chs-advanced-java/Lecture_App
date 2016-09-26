package ajlyfe.lectureapp.Activity;

import ajlyfe.lectureapp.Fragment.*;
import ajlyfe.lectureapp.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class UploadActivity extends AppIntro {

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        // DO NOT WRITE -> setContentView(R.layout.activity_upload);

        Fragment fragmentClass = new FragmentClass();
        Fragment fragmentFile = new FragmentFile();
        Fragment fragmentUpload = new FragmentUpload();
        Fragment fragmentResult = new FragmentResult();

        addSlide(fragmentFile);
        addSlide(fragmentClass);
        addSlide(fragmentUpload);
        addSlide(fragmentResult);

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);
        setColorDoneText(this.getResources().getColor(R.color.colorAccent));
        setImageNextButton(this.getResources().getDrawable(R.drawable.ic_next));
        setSeparatorColor(Color.TRANSPARENT);

        setIndicatorColor(R.color.colorPrimary50, R.color.colorPrimary20);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, TeacherMainScreen.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        if (newFragment != null) {
            int slideNumber = newFragment.getTag().charAt(newFragment.getTag().length() - 1) - 47;
        }
    }
}