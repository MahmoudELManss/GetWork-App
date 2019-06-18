package com.example.android.test1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public int[] slideImages = {
            R.drawable.wrench,
            R.drawable.loupe,
            R.drawable.phone
    };

    public int[] slideDescriptions = {
            R.string.first_page,
            R.string.second_page,
            R.string.third_page
    };

    public SliderAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return slideDescriptions.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slid_layout, container, false);
        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideDescription = view.findViewById(R.id.slide_desc);
        slideImageView.setImageResource(slideImages[position]);
        slideDescription.setText(slideDescriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
