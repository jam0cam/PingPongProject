package com.elasticbeanstalk.honey.pingpong;

import android.os.Bundle;
import android.view.Menu;

;

public class PPStatsFragment extends BaseFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp_stats);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ppstats, menu);
        return true;
    }
    
}
