package com.elasticbeanstalk.honey.pingpong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int count;
    private ImageType imageType;

    public enum ImageType{SWEEP, LUNCH}

    public ImageAdapter() {
    }

    public ImageAdapter(Context c, int count, ImageType imageType) {
        mContext = c;
        this.count = count;
        this.imageType = imageType;
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(65, 65));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5,5,5,5);
        } else {
            imageView = (ImageView) convertView;
        }

        if (imageType == ImageType.LUNCH) {
            imageView.setImageResource(R.drawable.burger);
        } else {
            imageView.setImageResource(R.drawable.broom);
        }
        return imageView;
    }
}
