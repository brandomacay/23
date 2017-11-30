package solange.amor.my_love.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import solange.amor.my_love.R;

public class Carta extends AppCompatActivity {
    private Toolbar mToolbar;
    private CardView carta1,carta2,carta3;
    private RelativeLayout circulo1,circulo2,circulo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbar.setTitle("by:B.M.M.R");
        //setSupportActionBar(mToolbar);
        carta1 = (CardView) findViewById(R.id.carta1);
        carta2 = (CardView)findViewById(R.id.carta2);
        carta3 = (CardView)findViewById(R.id.carta3);
        circulo1 = (RelativeLayout)findViewById(R.id.circulo1);
        circulo2 = (RelativeLayout)findViewById(R.id.circulo2);
        circulo3 = (RelativeLayout)findViewById(R.id.circulo3);
        carta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carta.this,Carta1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });
        carta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carta.this,Carta2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });

        carta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carta.this,Carta3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);

            }
        });
        circulo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carta.this,Carta1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });
        circulo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carta.this,Carta2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });
        circulo3.isClickable();
        circulo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Carta.this,Carta3.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
