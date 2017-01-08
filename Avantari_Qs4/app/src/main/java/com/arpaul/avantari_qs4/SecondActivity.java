package com.arpaul.avantari_qs4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arpaul.utilitieslib.ColorUtils;

import static com.arpaul.avantari_qs4.R.styleable.AppBarLayout;
import static com.arpaul.avantari_qs4.R.styleable.CollapsingToolbarLayout;

public class SecondActivity extends AppCompatActivity {

    private TextView tvTotalEle, tvAverageEle, tvValue, tvTitle;
//    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ImageView ivBack;
    private View vTopView;
//    private CollapsingToolbarLayout toolbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initialiseControls();

        bindControls();

    }

    private void bindControls() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                runSlideOutAnimation(tvTotalEle, tvAverageEle);
//                runSlideInAnimation(tvAverageEle);
                tvValue.setText("92.3");
                runFadeInAnimation(ivBack);
                tvTitle.setText("Time Power");
                runFadeOutAnimation(fab);
                vTopView.setBackgroundColor(ColorUtils.getColor(SecondActivity.this, R.color.colorAccent));
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runSlideOutAnimation(tvAverageEle, tvTotalEle);
                runFadeOutAnimation(ivBack);
                tvValue.setText("180.9");
                tvTitle.setText("The Current Chart");
                runFadeInAnimation(fab);
                vTopView.setBackgroundColor(ColorUtils.getColor(SecondActivity.this, R.color.colorPrimary));
            }
        });

        vTopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        runSlideInAnimation(tvTotalEle);
        tvValue.setText("180.9");
        vTopView.setBackgroundColor(ColorUtils.getColor(SecondActivity.this, R.color.colorPrimary));
    }

    private void runSlideInAnimation(final TextView tv) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        tv.startAnimation(a);
    }

    private void runSlideOutAnimation(final TextView tvOut, final TextView tvIn) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                tvOut.setVisibility(View.GONE);
                runSlideInAnimation(tvIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        tvOut.startAnimation(a);
    }

    private void runFadeInAnimation(final View iv) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        iv.startAnimation(a);
    }

    private void runFadeOutAnimation(final View iv) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        iv.startAnimation(a);
    }

    private void initialiseControls() {
//        toolbar         = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fab             = (FloatingActionButton) findViewById(R.id.fab);

        tvTotalEle      = (TextView) findViewById(R.id.tvTotalEle);
        tvAverageEle    = (TextView) findViewById(R.id.tvAverageEle);
        tvValue         = (TextView) findViewById(R.id.tvValue);
        tvTitle         = (TextView) findViewById(R.id.tvTitle);

        ivBack          = (ImageView) findViewById(R.id.ivBack);

        vTopView        = findViewById(R.id.vTopView);

//        toolbar_layout  = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        tvAverageEle.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);
    }
}
