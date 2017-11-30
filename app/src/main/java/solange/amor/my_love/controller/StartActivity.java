package solange.amor.my_love.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solange.amor.my_love.R;
import solange.amor.my_love.activity.MainActivity;

public class StartActivity extends AppCompatActivity {

    private StartApp startApp;
    private StartAppAdapter startAppAdapter;
    private ViewPager viewPager;

    private LinearLayout designPoints;
    private TextView[] dots;
    private Button  btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);


        startApp = new StartApp(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        designPoints = (LinearLayout) findViewById(R.id.designPoints);
         btnNext = (Button) findViewById(R.id.btn_next);

        addBottomDots(0);
        startAppAdapter = new StartAppAdapter(this, getLayouts());
        viewPager.setAdapter(startAppAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);




        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < getLayouts().size()) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });


    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startApp.setFirstTimeLaunch(false);
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();
    }

    private List<Integer> getLayouts() {
        List<Integer> layouts = new ArrayList<>();
        layouts.add(R.layout.layout_screen1);
        layouts.add(R.layout.layout_screen2);
        layouts.add(R.layout.layout_screen3);
        return layouts;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[getLayouts().size()];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        designPoints.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(colorsInactive[currentPage]);
            designPoints.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == getLayouts().size() - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
             } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
             }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

}
