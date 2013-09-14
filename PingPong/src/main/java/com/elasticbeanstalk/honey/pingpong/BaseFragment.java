package com.elasticbeanstalk.honey.pingpong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by jitse on 9/11/13.
 */
public class BaseFragment extends FragmentActivity {
    protected ProgressDialog pd;
    protected SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    protected SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");


    protected void showToastError(){
        if (pd != null && pd.isShowing()){pd.dismiss();}
        Toast toast = Toast.makeText(this, "Error.", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_pingpong:
                Intent pingpongIntent = new Intent(this, MainActivity.class);
                startActivity(pingpongIntent);
                return true;
            case R.id.action_ping_pong_stats:
                Intent statsIntent = new Intent(this, PingPongStatsFragment.class);
                startActivity(statsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void saveFailed() {
        Toast toast = Toast.makeText(this, "Failed to save.", Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void saveSuccessful() {
        Toast toast = Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT);
        toast.show();

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void restartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}