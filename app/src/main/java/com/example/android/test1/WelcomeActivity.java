package com.example.android.test1;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager mSlidViewPager;
    private LinearLayout mDotLayout;
    private TextView[] nDots;
    private SliderAdapter sliderAdapter;

    private ImageView mNextBtn;
    private ImageView mPrevBtn;
    private TextView register;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mSlidViewPager = findViewById(R.id.slidViewPager);
        mDotLayout = findViewById(R.id.dots);

        mNextBtn = findViewById(R.id.next_button);
        mPrevBtn = findViewById(R.id.prev_button);
        register = findViewById(R.id.register);


        sliderAdapter = new SliderAdapter(this);

        mSlidViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlidViewPager.addOnPageChangeListener(viewListner);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, ChooseActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public void addDotsIndicator(int position){
        nDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < nDots.length; i++){
            nDots[i] = new TextView(this);
            nDots[i].setText(Html.fromHtml("&#8226;"));
            nDots[i].setTextSize(35);
            nDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(nDots[i]);
        }

        if (nDots.length > 0){
            nDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPage = i;
            if (i == 0){
                mNextBtn.setEnabled(true);
                mPrevBtn.setEnabled(false);
                mPrevBtn.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                mNextBtn.setVisibility(View.VISIBLE);

                mNextBtn.setImageResource(R.drawable.next);
            } else if ( i == nDots.length - 1) {
                mNextBtn.setEnabled(false);
                mPrevBtn.setEnabled(true);
                mNextBtn.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);

                register.setText(R.string.register);
                mPrevBtn.setImageResource(R.drawable.back);
            } else  {
                mNextBtn.setEnabled(true);
                mPrevBtn.setEnabled(true);
                mPrevBtn.setVisibility(View.VISIBLE);
                register.setVisibility(View.INVISIBLE);
                mNextBtn.setVisibility(View.VISIBLE);

                mNextBtn.setImageResource(R.drawable.next);
                mPrevBtn.setImageResource(R.drawable.back);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
