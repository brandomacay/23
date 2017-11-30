package solange.amor.my_love.checks;

import android.graphics.Color;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.TYPE;

/**
 * Created by Eugene Levenetc.
 */
public class ColorSample {
	public static void play(TextSurface textSurface) {

		Text textA = TextBuilder
				.create("Sonrie te ves hermosa ^_^ ")
				.setPosition(Align.SURFACE_CENTER)
				.setSize(35)
				.setAlpha(0)
				.build();

		textSurface.play(TYPE.SEQUENTIAL,
				Alpha.show(textA, 4000),
				ChangeColor.to(textA, 4000, Color.BLUE),
				ChangeColor.to(textA,4000, Color.CYAN),
				ChangeColor.to(textA,4000, Color.WHITE),
				ChangeColor.to(textA, 4000, Color.BLUE),
				ChangeColor.to(textA,4000, Color.CYAN),
				ChangeColor.to(textA, 4000, Color.RED),
				ChangeColor.to(textA,4000, Color.WHITE),
				ChangeColor.to(textA, 4000, Color.RED),
				ChangeColor.to(textA, 4000, Color.BLUE),
				ChangeColor.to(textA,4000, Color.CYAN),
				ChangeColor.to(textA,4000, Color.WHITE),
				ChangeColor.to(textA, 4000, Color.BLUE),
				ChangeColor.to(textA,4000, Color.CYAN),
				ChangeColor.to(textA, 4000, Color.RED),
				ChangeColor.to(textA,4000, Color.WHITE)
				//Alpha.hide(textA, 5000)
		);
	}
}
