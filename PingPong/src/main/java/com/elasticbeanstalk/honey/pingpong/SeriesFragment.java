package com.elasticbeanstalk.honey.pingpong;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pingpong.Game;
import com.pingpong.Series;

import org.json.JSONObject;

;

/**
 * Created by jitse on 8/26/13.
 */
public class SeriesFragment extends Fragment {

    private Series series;
    private TextView txtJiaSeries;
    private TextView txtMattSeries;
    private TextView txtJiaGames;
    private TextView txtMattGames;

    private SeriesEventListener listener;


    public interface SeriesEventListener {
        public void onDataLoaded(Series series);
        public void error();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ping_pong_series, container, false);

        txtJiaGames = (TextView)view.findViewById(R.id.txtJiaGames);
        txtJiaSeries = (TextView)view.findViewById(R.id.txtJiaSeries);
        txtMattGames = (TextView)view.findViewById(R.id.txtMattGames);
        txtMattSeries = (TextView)view.findViewById(R.id.txtMattSeries);

        if (savedInstanceState != null) {
            series = (Series)savedInstanceState.getSerializable("series");
        }

        if (series != null) {
            fillSeries();
        }
        return view;
    }

    /**
     * Updates the series with the data from the game
     * @param game
     */
    public void updateSeries(Game game) {
        if (game.getPlayer1Score() > game.getPlayer2Score()) {
            series.setPlayer1SeriesWin(series.getPlayer1SeriesWin()+1);
        } else {
            series.setPlayer2SeriesWin(series.getPlayer2SeriesWin()+1);
        }

        series.setPlayer1TotalWin(series.getPlayer1TotalWin() + game.getPlayer1Score());
        series.setPlayer2TotalWin(series.getPlayer2TotalWin() + game.getPlayer2Score());
        fillSeries();
    }

    private void fillSeries(){
        //load the series table
        txtJiaGames.setText(Integer.toString(series.getPlayer1TotalWin()));
        txtJiaSeries.setText(Integer.toString(series.getPlayer1SeriesWin()));

        txtMattGames.setText(Integer.toString(series.getPlayer2TotalWin()));
        txtMattSeries.setText(Integer.toString(series.getPlayer2SeriesWin()));
    }

    public void fetchSeries(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = getResources().getString(R.string.url_get_series);
        series = null;

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //if it comes back here, that means this is a valid user
                series = Util.fromJSON(response, Series.class);
                listener.onDataLoaded(series);
                fillSeries();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error();
            }
        });

        queue.add(request);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("series", series);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setListener(SeriesEventListener listener) {
        this.listener = listener;
    }
}
