package com.elasticbeanstalk.honey.pingpong;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by jitse on 9/11/13.
 */
public class Util {

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }


    public static <T> T fromJSON(Object object, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object o = mapper.readValue(object.toString(), clazz);
            return (T)o;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject toJsonObject(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(o);
            return new JSONObject(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show, final View progressView, final View mainView, int animationTime) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            progressView.setVisibility(View.VISIBLE);
            progressView.animate()
                    .setDuration(animationTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mainView.setVisibility(View.VISIBLE);
            mainView.animate()
                    .setDuration(animationTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mainView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mainView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public static String getMonthName(int i) {
        if (i == 1 || i == 13) {
            return "January";
        } else if (i == 2) {
            return "February";
        } else if (i == 3) {
            return "March";
        } else if (i == 4) {
            return "April";
        } else if (i == 5) {
            return "May";
        } else if (i == 6) {
            return "June";
        } else if (i == 7) {
            return "July";
        } else if (i == 8) {
            return "August";
        } else if (i == 9) {
            return "September";
        } else if (i == 10) {
            return "October";
        } else if (i == 11) {
            return "November";
        } else if (i == 12 || i == 0) {
            return "December";
        }

        return "???";
    }
}
