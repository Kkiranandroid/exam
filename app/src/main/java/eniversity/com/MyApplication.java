package eniversity.com;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.util.SparseArray;

import com.android.volley.toolbox.ImageLoader;

import java.lang.reflect.Field;

import Commmon.Methods.FontsOverride;

/**
 * Created by Administrator on 12/1/2016.
 */

public class MyApplication extends MultiDexApplication {


    private static final String DEFAULT_NORMAL_BOLD_FONT_FILENAME = "fonts/proxima_nova_regular.ttf";


    // Uses the Croscore_fonts: Arimo (sans), Tinos (serif) and Cousine (monospace)
    private static final String DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "fonts/proxima_nova_semi_bold.ttf";
    // Constants found in the Android documentation
    // http://developer.android.com/reference/android/widget/TextView.html#attr_android:typeface
    private static final int normal_idx = 0;
    /*private static final String DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "Arimo-BoldItalic.ttf";
	private static final String DEFAULT_NORMAL_ITALIC_FONT_FILENAME = "Arimo-Italic.ttf";
	private static final String DEFAULT_NORMAL_NORMAL_FONT_FILENAME = "Arimo-Regular.ttf";

	private static final String DEFAULT_SANS_BOLD_FONT_FILENAME = "Arimo-Bold.ttf";
	private static final String DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME = "Arimo-BoldItalic.ttf";
	private static final String DEFAULT_SANS_ITALIC_FONT_FILENAME = "Arimo-Italic.ttf";
	private static final String DEFAULT_SANS_NORMAL_FONT_FILENAME = "Arimo-Regular.ttf";

	private static final String DEFAULT_SERIF_BOLD_FONT_FILENAME = "Cousine-Bold.ttf";
	private static final String DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME = "Cousine-BoldItalic.ttf";
	private static final String DEFAULT_SERIF_ITALIC_FONT_FILENAME = "Cousine-Italic.ttf";
	private static final String DEFAULT_SERIF_NORMAL_FONT_FILENAME = "Cousine-Regular.ttf";

	private static final String DEFAULT_MONOSPACE_BOLD_FONT_FILENAME = "Tinos-Bold.ttf";
	private static final String DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME = "Tinos-BoldItalic.ttf";
	private static final String DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME = "Tinos-Italic.ttf";
	private static final String DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME = "Tinos-Regular.ttf";*/
    private static final int sans_idx = 1;
    private static final int serif_idx = 2;
    private static final int monospace_idx = 3;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
    /*    DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                // Remove for release app
                .build();*/

		/*ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.discCacheSize(50 * 1024 * 1024)
				.imageDownloader(new BaseImageDownloader(getApplicationContext(), 2000, 3000))
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.build();
*/
       // ImageLoader.getInstance().init(config);

        super.onCreate();

        //FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Roboto-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/proxima_nova_regular.ttf");


        try {
            setDefaultFonts();

            // The following code is only necessary if you are using the android:typeface attribute
        //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				/*setDefaultFontForTypeFaceSans();
				setDefaultFontForTypeFaceSansSerif();
				setDefaultFontForTypeFaceMonospace();*/
           // }
        } catch (NoSuchFieldException e) {
            // Field does not exist in this (version of the) class
            logFontError(e);
        } catch (IllegalAccessException e) {
            // Access rights not set correctly on field, i.e. we made a programming error
            logFontError(e);
        } catch (Throwable e) {
            // Must not crash app if there is a failure with overriding fonts!
            logFontError(e);
        }


		/*FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset2.ttf");
		FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
		FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");*/
    }


    private void setDefaultFonts() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_BOLD_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME);
		/*final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_ITALIC_FONT_FILENAME);

		final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_NORMAL_NORMAL_FONT_FILENAME);
*/
		/*Field defaultField = Typeface.class.getDeclaredField("DEFAULT");
		defaultField.setAccessible(true);
		defaultField.set(null, normal);
*/
        Field defaultBoldField = Typeface.class.getDeclaredField("DEFAULT_BOLD");
        defaultBoldField.setAccessible(true);
        defaultBoldField.set(null, bold);

	/*	Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
		sDefaults.setAccessible(true);
		sDefaults.set(null, bold);*/

		/*Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
		sDefaults.setAccessible(true);
		sDefaults.set(null, new Typeface[]{normal, bold, italic, boldItalic});*/
		/*final Typeface normal_sans = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_NORMAL_FONT_FILENAME);
		Field sansSerifDefaultField = Typeface.class.getDeclaredField("SANS_SERIF");
		sansSerifDefaultField.setAccessible(true);
		sansSerifDefaultField.set(null, normal_sans);

		final Typeface normal_serif = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_NORMAL_FONT_FILENAME);
		Field serifDefaultField = Typeface.class.getDeclaredField("SERIF");
		serifDefaultField.setAccessible(true);
		serifDefaultField.set(null, normal_serif);

		final Typeface normal_monospace = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);
		Field monospaceDefaultField = Typeface.class.getDeclaredField("MONOSPACE");
		monospaceDefaultField.setAccessible(true);
		monospaceDefaultField.set(null, normal_monospace);*/
    }

	/*private void setDefaultFontForTypeFaceSans() throws NoSuchFieldException, IllegalAccessException {
		final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_BOLD_FONT_FILENAME);
		final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_ITALIC_FONT_FILENAME);
		final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME);
		final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_SANS_NORMAL_FONT_FILENAME);

		setTypeFaceDefaults(normal, bold, italic, boldItalic, sans_idx);
	}

	private void setDefaultFontForTypeFaceSansSerif() throws NoSuchFieldException, IllegalAccessException {
		final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_BOLD_FONT_FILENAME);
		final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_ITALIC_FONT_FILENAME);
		final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME);
		final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_SERIF_NORMAL_FONT_FILENAME);

		setTypeFaceDefaults(normal, bold, italic, boldItalic, serif_idx);
	}

	private void setDefaultFontForTypeFaceMonospace() throws NoSuchFieldException, IllegalAccessException {
		final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_BOLD_FONT_FILENAME);
		final Typeface italic = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME);
		final Typeface boldItalic = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME);
		final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);

		setTypeFaceDefaults(normal, bold, italic, boldItalic, monospace_idx);
	}*/

    private void setTypeFaceDefaults(Typeface normal, Typeface bold, Typeface italic, Typeface boldItalic, int typefaceIndex) throws NoSuchFieldException, IllegalAccessException {
        Field typeFacesField = Typeface.class.getDeclaredField("sTypefaceCache");
        typeFacesField.setAccessible(true);

        SparseArray<SparseArray<Typeface>> sTypefaceCacheLocal = new SparseArray<SparseArray<Typeface>>(3);
        typeFacesField.get(sTypefaceCacheLocal);

        SparseArray<Typeface> newValues = new SparseArray<Typeface>(4);
        newValues.put(Typeface.NORMAL, normal);
        newValues.put(Typeface.BOLD, bold);
        newValues.put(Typeface.ITALIC, italic);
        newValues.put(Typeface.BOLD_ITALIC, boldItalic);
        sTypefaceCacheLocal.put(typefaceIndex, newValues);

        typeFacesField.set(null, sTypefaceCacheLocal);
    }

    private void logFontError(Throwable e) {
        Log.e("font_override", "Error overriding font", e);
    }

}
