package com.elasticbeanstalk.honey.pingpong;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by jitse on 9/11/13.
 */
public class BaseFragment extends Fragment {
    protected ProgressDialog pd;
    protected SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    protected SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");


    protected void showToastError(){
        if (pd != null && pd.isShowing()){pd.dismiss();}
        Toast toast = Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT);
        toast.show();
    }
}