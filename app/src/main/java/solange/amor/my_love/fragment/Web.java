package solange.amor.my_love.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import solange.amor.my_love.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Web extends Fragment {
   // WebView webView;
    TextView facebook,instagram;
    RelativeLayout llamar;
    FloatingActionButton hola;


    public Web() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web,container,false);
        facebook = (TextView)v.findViewById(R.id.facebook);
        instagram = (TextView)v.findViewById(R.id.instagram);
        hola = (FloatingActionButton)v.findViewById(R.id.hola);
        facebook.setMovementMethod(LinkMovementMethod.getInstance());
        instagram.setMovementMethod(LinkMovementMethod.getInstance());
        llamar = (RelativeLayout)v.findViewById(R.id.llamar);
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0987280450"));
                startActivity(intent);
            }
        });

       /* hola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Hola.class);
                startActivity(intent);
            }
        });
        */

        /* WebView appWeb = v.findViewById(R.id.webview);

        appWeb.getSettings().setJavaScriptEnabled(true);
        appWeb.getSettings().getCacheMode();

        appWeb.loadUrl("http://facebook.com/");
        appWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        */


        return v;


    }

    public static Web newInstance() {
        return new Web();
    }
}
