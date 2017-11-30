package solange.amor.my_love.checks;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Circle;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Direction;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Eugene Levenetc.
 */
public class CookieThumperSample {

	public static void play(TextSurface textSurface, AssetManager assetManager) {

		final Typeface robotoBlack = Typeface.createFromAsset(assetManager, "fonts/Roboto-Black.ttf");
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTypeface(robotoBlack);

		Text textDaai = TextBuilder
				.create("Solange Arevalo")
				.setPaint(paint)
				.setSize(64)
				.setAlpha(0)
				.setColor(Color.WHITE)
				.setPosition(Align.SURFACE_CENTER).build();

		Text textBraAnies = TextBuilder
				.create("una chica ideal para mi")
				.setPaint(paint)
				.setSize(44)
				.setAlpha(0)
				.setColor(Color.RED)
				.setPosition(Align.BOTTOM_OF, textDaai).build();

		Text textFokkenGamBra = TextBuilder
				.create(" es tan hermosa y tan tierna")
				.setPaint(paint)
				.setSize(44)
				.setAlpha(0)
				.setColor(Color.RED)
				.setPosition(Align.RIGHT_OF, textBraAnies).build();

		Text textHaai = TextBuilder
				.create("una ternurita!")
				.setPaint(paint)
				.setSize(65)
				.setAlpha(0)
				.setColor(Color.RED)
				.setPosition(Align.BOTTOM_OF, textFokkenGamBra).build();

		Text textDaaiAnies = TextBuilder
				.create("y tambien muy peligrosa")
				.setPaint(paint)
				.setSize(44)
				.setAlpha(0)
				.setColor(Color.WHITE)
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textHaai).build();

		Text texThyLamInnie = TextBuilder
				.create("simplemente perfecta para mi")
				.setPaint(paint)
				.setSize(40)
				.setAlpha(0)
				.setColor(Color.WHITE)
				.setPosition(Align.RIGHT_OF, textDaaiAnies).build();

		Text textThrowDamn = TextBuilder
				.create("Te amo")
				.setPaint(paint)
				.setSize(44)
				.setAlpha(0)
				.setColor(Color.RED)
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, texThyLamInnie).build();

		Text textDevilishGang = TextBuilder
				.create("y te seguire amando")
				.setPaint(paint)
				.setSize(44)
				.setAlpha(0)
				.setColor(Color.RED)
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textThrowDamn).build();

		Text textSignsInTheAir = TextBuilder
				.create("gracias por todo :)")
				.setPaint(paint)
				.setSize(44)
				.setAlpha(0)
				.setColor(Color.WHITE)
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textDevilishGang).build();

		textSurface.play(
				new Sequential(
						ShapeReveal.create(textDaai, 1000, SideCut.show(Side.LEFT), false),
						new Parallel(ShapeReveal.create(textDaai, 900, SideCut.hide(Side.LEFT), false), new Sequential(Delay.duration(700), ShapeReveal.create(textDaai, 1000, SideCut.show(Side.LEFT), false))),
						new Parallel(new TransSurface(1000, textBraAnies, Pivot.CENTER), ShapeReveal.create(textBraAnies, 2000, SideCut.show(Side.LEFT), false)),
						Delay.duration(1000),
						new Parallel(new TransSurface(1000, textFokkenGamBra, Pivot.CENTER), Slide.showFrom(Side.LEFT, textFokkenGamBra, 1000), ChangeColor.to(textFokkenGamBra,1000 , Color.WHITE)),
						Delay.duration(1000),
						new Parallel(TransSurface.toCenter(textHaai, 1000), Rotate3D.showFromSide(textHaai, 750, Pivot.TOP)),
						new Parallel(TransSurface.toCenter(textDaaiAnies, 1000), Slide.showFrom(Side.TOP, textDaaiAnies,1000)),
						new Parallel(TransSurface.toCenter(texThyLamInnie, 1000), Slide.showFrom(Side.LEFT, texThyLamInnie, 1000)),
						Delay.duration(1000),
						new Parallel(
								new TransSurface(1000, textSignsInTheAir, Pivot.CENTER),
								new Sequential(
										new Sequential(ShapeReveal.create(textThrowDamn, 1000, Circle.show(Side.CENTER, Direction.OUT), false)),
										new Sequential(ShapeReveal.create(textDevilishGang, 1000, Circle.show(Side.CENTER, Direction.OUT), false)),
										new Sequential(ShapeReveal.create(textSignsInTheAir, 1000, Circle.show(Side.CENTER, Direction.OUT), false))
								)
						),
						Delay.duration(2000),
						new Parallel(
								ShapeReveal.create(textThrowDamn, 1000, SideCut.hide(Side.LEFT), true),
								new Sequential(Delay.duration(1000), ShapeReveal.create(textDevilishGang, 700, SideCut.hide(Side.LEFT), true)),
								new Sequential(Delay.duration(1000), ShapeReveal.create(textSignsInTheAir, 700, SideCut.hide(Side.LEFT), true)),
								Alpha.hide(texThyLamInnie, 2000),
								Alpha.hide(textDaaiAnies, 3000)
						)
				)
		);
	}
}
