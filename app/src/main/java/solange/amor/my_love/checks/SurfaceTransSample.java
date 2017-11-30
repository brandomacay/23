package solange.amor.my_love.checks;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.AnimationsSet;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.TYPE;

/**
 * Created by Eugene Levenetc.
 */
public class SurfaceTransSample {
	public static void play(TextSurface textSurface) {
		Text textA = TextBuilder.create("S").setSize(40).setPosition(Align.SURFACE_CENTER).build();
		Text textB = TextBuilder.create("M").setSize(40).setPosition(Align.RIGHT_OF | Align.BOTTOM_OF, textA).build();
		Text textC = TextBuilder.create("A").setSize(40).setPosition(Align.LEFT_OF | Align.BOTTOM_OF, textB).build();
		Text textD = TextBuilder.create("J").setSize(40).setPosition(Align.RIGHT_OF | Align.BOTTOM_OF, textC).build();

		int duration = 1500;

		textSurface.play(TYPE.SEQUENTIAL,
				Alpha.show(textA, duration),
				new AnimationsSet(TYPE.PARALLEL, Alpha.show(textB, duration), new TransSurface(duration, textB, Pivot.CENTER)),
				new AnimationsSet(TYPE.PARALLEL, Alpha.show(textC, duration), new TransSurface(duration, textC, Pivot.CENTER)),
				new AnimationsSet(TYPE.PARALLEL, Alpha.show(textD, duration), new TransSurface(duration, textD, Pivot.CENTER)),
				new TransSurface(duration, textC, Pivot.CENTER),
				new TransSurface(duration, textB, Pivot.CENTER),
				new TransSurface(duration, textA, Pivot.CENTER)
		);
	}
}
