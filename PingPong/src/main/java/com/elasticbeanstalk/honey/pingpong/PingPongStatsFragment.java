package com.elasticbeanstalk.honey.pingpong;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pingpong.Game;
import com.pingpong.Series;

import java.util.ArrayList;

public class PingPongStatsFragment extends Fragment implements SeriesFragment.SeriesEventListener, GamesFragment.SeriesEventListener {

    protected ProgressDialog pd;
    private SeriesFragment frgPPSeries;
    private GamesFragment frgPPGames;

    private ArrayList<Game> games;
    private Series series;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_ping_pong_stats, container, false);
        if (savedInstanceState != null) {
            games = (ArrayList<Game>)savedInstanceState.getSerializable("games");
            series = (Series)savedInstanceState.getSerializable("series");
        }

        frgPPSeries = (SeriesFragment)getFragmentManager().findFragmentById(R.id.frgPPSeriesStats);
        frgPPSeries.setRetainInstance(true);
        frgPPSeries.setListener(this);

        frgPPGames = (GamesFragment)getFragmentManager().findFragmentById(R.id.frgPPGamesStats);
        frgPPGames.setRetainInstance(true);
        frgPPGames.setListener(this);

        if (games == null) {
            //just have to show this while the fragments load
            pd = ProgressDialog.show(getActivity(), "Fetching Games", "Loading");
            frgPPSeries.fetchSeries();
            frgPPGames.fetchGames();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("games", games);
        outState.putSerializable("series", series);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDataLoaded(ArrayList<Game> games) {
        this.games = games;
        if (series == null || this.games == null) {
            //don't reload until both series and games are available
            return;
        }
        if (pd != null && pd.isShowing()){pd.dismiss();}
    }

    @Override
    public void error() {
        showToastError();
    }


    protected void showToastError(){
        if (pd != null && pd.isShowing()){pd.dismiss();}
        Toast toast = Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT);
        toast.show();
    }



    @Override
    public void onDataLoaded(Series series) {
        this.series = series;
        if (this.series == null || games == null) {
            //don't reload until both series and games are available
            return;
        }
        if (pd != null && pd.isShowing()){pd.dismiss();}
    }
}
