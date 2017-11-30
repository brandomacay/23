package solange.amor.my_love.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import solange.amor.my_love.R;
import solange.amor.my_love.activity.cinco;
import solange.amor.my_love.activity.cuatro;
import solange.amor.my_love.activity.dos;
import solange.amor.my_love.activity.ocho;
import solange.amor.my_love.activity.seis;
import solange.amor.my_love.activity.siete;
import solange.amor.my_love.activity.tres;
import solange.amor.my_love.activity.uno;


/**
 * A simple {@link Fragment} subclass.
 */
public class Casa extends Fragment {

    private RelativeLayout a,b,c,d,e,f,g,h;


    public Casa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.casa, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        a = rootView.findViewById(R.id.a);
        b = rootView.findViewById(R.id.b);
        c = rootView.findViewById(R.id.c);
        d = rootView.findViewById(R.id.d);
        e = rootView.findViewById(R.id.e);
        f = rootView.findViewById(R.id.f);
        g = rootView.findViewById(R.id.g);
        h = rootView.findViewById(R.id.h);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), uno.class);
                startActivity(intent);
            }
        });
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),dos.class);
                startActivity(intent);
            }
        });
        c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),tres.class);
                startActivity(intent);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), cuatro.class);
                startActivity(intent);
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), cinco.class);
                startActivity(intent);
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getContext(),seis.class);
                startActivity(intent);
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), siete.class);
                startActivity(intent);
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ocho.class);
                startActivity(intent);
            }
        });
    }
    public static Casa newInstance() {
        return new Casa();
    }
}
