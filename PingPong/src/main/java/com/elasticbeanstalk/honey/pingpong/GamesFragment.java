package com.elasticbeanstalk.honey.pingpong;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.pingpong.Game;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by jitse on 8/27/13.
 */
public class GamesFragment extends Fragment {

    private ArrayList<Game> games;
    private TextView txtTitle;
    private boolean isLifeTime = false;

    private ListView lvGames;

    protected SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    protected SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

    private SeriesEventListener listener;

    public interface SeriesEventListener {
        public void onDataLoaded(ArrayList<Game> games);
        public void error();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ping_pong_games, container, false);

        lvGames = (ListView)view.findViewById(R.id.lvGames);
        if (savedInstanceState != null) {
            games = (ArrayList<Game>)savedInstanceState.getSerializable("games");
        }

        txtTitle = (TextView)view.findViewById(R.id.txtTitle);
        if (!isLifeTime){
            txtTitle.setText("Recent Games");
        } else {
            txtTitle.setText("Lifetime Games");
        }

        if (games != null) {
            lvGames.setAdapter(new PingPongGamesListAdapter(this.getActivity(), games));
        }
        return view;
    }


    public void fetchGames() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url;
        if (!isLifeTime){
            url = getResources().getString(R.string.url_get_recent_games);
        } else {
            url = getResources().getString(R.string.url_get_games);
        }
        games = null;
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //if it comes back here, that means this is a valid user
                Game[] payeesArray = Util.fromJSON(response, Game[].class);

                games = new ArrayList<Game>();
                for (Game g : payeesArray) {
                    try {
                        g.setDateString(formatter.format(parser.parse(g.getDateString())));
                    } catch (ParseException e) {}

                    if (g.getPlayer1Score() > g.getPlayer2Score()) {
                        g.setWinner(g.getPlayer1().getName());
                    } else if (g.getPlayer1Score() < g.getPlayer2Score()) {
                        g.setWinner(g.getPlayer2().getName());
                    } else {
                        g.setWinner("TIE");
                    }
                    games.add(g);
                }
                succeedFetchData();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error();
            }
        });

        queue.add(request);
    }

    private void succeedFetchData() {
        lvGames.setAdapter(new PingPongGamesListAdapter(this.getActivity(), games));
        listener.onDataLoaded(games);
    }

    /**
     * Updates the list of games by removing the last game and inserting this game at the head
     * @param game
     */
    public void updateGames(Game game) {
        games.remove(games.size()-1);
        games.add(0, game);
        ((PingPongGamesListAdapter)lvGames.getAdapter()).notifyDataSetChanged();
    }

    public void updateLifetimeGames(Game game) {
        games.add(0, game);
        ((PingPongGamesListAdapter)lvGames.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("games", games);
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

    public void setLifeTime(boolean lifeTime) {
        isLifeTime = lifeTime;
    }
}
