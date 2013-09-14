package com.elasticbeanstalk.honey.pingpong;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pingpong.Game;

import java.util.ArrayList;

/**
 * Created by jitse on 8/27/13.
 */
public class PingPongGamesListAdapter  extends BaseAdapter {

    private Activity activity;
    private ArrayList<Game> data;
    private static LayoutInflater inflater=null;

    public PingPongGamesListAdapter(Activity a, ArrayList<Game> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.pingpong_game_row, null);

        Game g = data.get(position);

        ((TextView)vi.findViewById(R.id.txtDate)).setText(g.getDateString());
        ((TextView)vi.findViewById(R.id.txtP1Score)).setText(Integer.toString(g.getPlayer1Score()));
        ((TextView)vi.findViewById(R.id.txtP2Score)).setText(Integer.toString(g.getPlayer2Score()));
        ((TextView)vi.findViewById(R.id.txtWinner)).setText(g.getWinner());

        return vi;
    }
}
