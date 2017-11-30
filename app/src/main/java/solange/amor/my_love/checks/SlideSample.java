package solange.amor.my_love.checks;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.AnimationsSet;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.contants.TYPE;

/**
 * Created by Eugene Levenetc.
 */
public class SlideSample {
	public static void play(TextSurface textSurface) {

		Text textA = TextBuilder.create(" ¿Como te encuentras hoy?").setSize(25).build();
		Text textB = TextBuilder.create("y ").setSize(25).setPosition(Align.LEFT_OF, textA).build();
		Text textC = TextBuilder.create("Estas bien?").setSize(25).setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textB).build();
		Text textD = TextBuilder.create("Espero que lo estes siempre!").setSize(25).setPadding(10, 10, 10, 10).setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textC).build();
		int duration = 2000;

		textSurface.play(
				TYPE.SEQUENTIAL,
				new AnimationsSet(TYPE.PARALLEL,
						new AnimationsSet(TYPE.SEQUENTIAL,
								new AnimationsSet(TYPE.PARALLEL, Slide.showFrom(Side.LEFT, textA, duration), Slide.showFrom(Side.RIGHT, textB, duration)),
								Slide.showFrom(Side.TOP, textC, duration),
								Slide.showFrom(Side.BOTTOM, textD, duration)
						),
						new TransSurface(duration * 3, textD, Pivot.CENTER)
				),
				new AnimationsSet(TYPE.PARALLEL,
						new AnimationsSet(TYPE.SEQUENTIAL,
								new AnimationsSet(TYPE.PARALLEL, Slide.hideFrom(Side.LEFT, textD, duration), Slide.hideFrom(Side.RIGHT, textC, duration)),
								Slide.hideFrom(Side.TOP, textB, duration),
								Slide.hideFrom(Side.BOTTOM, textA, duration)
						),
						new TransSurface(duration * 5, textA, Pivot.CENTER)
				)

		);
	}
}
