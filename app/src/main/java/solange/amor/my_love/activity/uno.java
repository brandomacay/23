package solange.amor.my_love.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import solange.amor.my_love.R;
import solange.amor.my_love.checks.CookieThumperSample;
import su.levenetc.android.textsurface.Debug;
import su.levenetc.android.textsurface.TextSurface;

/**
 * Created by Eugene Levenetc.
 */
public class uno extends AppCompatActivity {

	private TextSurface textSurface;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_activity);
		textSurface = (TextSurface) findViewById(R.id.text_surface);

		textSurface.postDelayed(new Runnable() {
			@Override public void run() {
				show();
			}
		}, 1000);


		findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				show();
			}
		});

		CheckBox checkDebug = (CheckBox) findViewById(R.id.check_debug);
		checkDebug.setChecked(Debug.ENABLED);
		checkDebug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Debug.ENABLED = isChecked;
				textSurface.invalidate();
			}
		});
	}

	private void show() {
		textSurface.reset();
		//textSurface.play();
		CookieThumperSample.play(textSurface, getAssets());
	}

}