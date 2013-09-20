package com.elasticbeanstalk.honey.pingpong;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pingpong.Game;
import com.pingpong.Lunches;
import com.pingpong.Sweeps;

import org.json.JSONObject;

public class PerksFragment extends BaseFragment {
    private Sweeps sweeps;
    private Lunches lunches;

    private GridView mattGvSweep;
    private GridView jiaGvSweep;

    private GridView mattGvLunch;
    private GridView jiaGvLunch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perks, container, false);

        setRetainInstance(true);


        mattGvSweep = (GridView)rootView.findViewById(R.id.gvMattSweep);
        jiaGvSweep = (GridView)rootView.findViewById(R.id.gvJiaSweep);

        mattGvLunch = (GridView)rootView.findViewById(R.id.gvMattLunch);
        jiaGvLunch = (GridView)rootView.findViewById(R.id.gvJiaLunch);

        if (savedInstanceState != null) {
            sweeps = (Sweeps)savedInstanceState.getSerializable("sweeps");
            lunches = (Lunches)savedInstanceState.getSerializable("lunches");
        }

        if (sweeps == null) {
            if (pd == null || !pd.isShowing()){
                pd = ProgressDialog.show(getActivity(), "Fetching Perks", "Loading");
            }
            fetchSweeps();
            fetchLunches();
        } else {
            fetchLunchesSuccess();
            fetchSweepsSuccess();
        }

        return rootView;
    }

    private void fetchSweepsSuccess(){

        if (sweeps.getPlayer1().getName().equals("Matt")){
            mattGvSweep.setAdapter(new ImageAdapter(getActivity(),sweeps.getPlayer1Score(), ImageAdapter.ImageType.SWEEP));
            jiaGvSweep.setAdapter(new ImageAdapter(getActivity(), sweeps.getPlayer2Score(), ImageAdapter.ImageType.SWEEP));
        } else {
            mattGvSweep.setAdapter(new ImageAdapter(getActivity(),sweeps.getPlayer2Score(), ImageAdapter.ImageType.SWEEP));
            jiaGvSweep.setAdapter(new ImageAdapter(getActivity(), sweeps.getPlayer1Score(), ImageAdapter.ImageType.SWEEP));
        }

        if (pd != null && pd.isShowing() && lunches != null){pd.dismiss();}
    }

    private void fetchLunchesSuccess() {
        if (lunches.getP1().getName().equals("Matt")){
            mattGvLunch.setAdapter(new ImageAdapter(getActivity(),lunches.getP1Lunches(), ImageAdapter.ImageType.LUNCH));
            jiaGvLunch.setAdapter(new ImageAdapter(getActivity(), lunches.getP2Lunches(), ImageAdapter.ImageType.LUNCH));
        } else {
            mattGvLunch.setAdapter(new ImageAdapter(getActivity(),lunches.getP2Lunches(), ImageAdapter.ImageType.LUNCH));
            jiaGvLunch.setAdapter(new ImageAdapter(getActivity(), lunches.getP1Lunches(), ImageAdapter.ImageType.LUNCH));
        }

        if (pd != null && pd.isShowing() && sweeps != null){pd.dismiss();}
    }

    private void fetchLunches() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = getResources().getString(R.string.url_get_lunches);
        lunches = null;

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //if it comes back here, that means this is a valid user
                lunches = Util.fromJSON(response, Lunches.class);
                fetchLunchesSuccess();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(request);
    }

    private void fetchSweeps(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = getResources().getString(R.string.url_get_sweeps);
        sweeps = null;

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //if it comes back here, that means this is a valid user
                sweeps = Util.fromJSON(response, Sweeps.class);
                fetchSweepsSuccess();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(request);
    }

    /**
     * THis is called from the parent to let us know that things have changed.
     * @param game
     */
    public void updateStats(Game game) {
        if (game.getPlayer1Score() == 5){
            incrementSweep(game.getPlayer1().getName());
            fetchSweepsSuccess();
        } else if (game.getPlayer2Score() == 5) {
            incrementSweep(game.getPlayer2().getName());
            fetchSweepsSuccess();
        }
    }

    private void incrementSweep(String playerName) {
        if (sweeps.getPlayer1().getName().equals(playerName)){
            sweeps.setPlayer1Score(sweeps.getPlayer1Score() + 1);
        } else {
            sweeps.setPlayer2Score(sweeps.getPlayer2Score() + 1);
        }
    }

}
