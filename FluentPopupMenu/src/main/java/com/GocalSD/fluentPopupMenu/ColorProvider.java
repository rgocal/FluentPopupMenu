package com.GocalSD.fluentPopupMenu;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import java.util.HashMap;
import java.util.Map;

/*
Class that provides some color palette options for tinting the menu
 */

public class ColorProvider {

    /**
     * Finds the color with the most occurrences inside of a bitmap.
     *
     * @param bitmap the bitmap to get the dominant color of
     * @return the dominant color
     */
    @ColorInt
    public static int getBitmapDominantColor(Bitmap bitmap) {
        Map<Integer, Integer> colors = new HashMap<>();
        int color = Color.TRANSPARENT;
        int occurrences = 0;

        try {
            for (int y = 0; y < bitmap.getWidth(); y++) {
                for (int x = 0; x < bitmap.getHeight(); x++) {
                    if (Color.alpha(bitmap.getPixel(x, y)) == 255) {
                        color = bitmap.getPixel(x, y);
                        colors.put(color, (colors.containsKey(color) ? colors.get(color) : 0) + 1);
                    }
                }
            }

            for (Integer key : colors.keySet()) {
                if (colors.get(key) > occurrences) {
                    occurrences = colors.get(key);
                    color = key;
                }
            }
        }catch (IllegalArgumentException e){
            //fallback
            color = ColorProvider.getDominantColor(bitmap);
        }

        return color;
    }

    public static boolean isDark(int color){
        //        return ColorUtils.calculateLuminance(color) < 0.5;
        return ColorUtils.calculateLuminance(color) < 0.25;
    }

    public static int getDominantColor(Bitmap bitmap){
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getDominantColor(defaultValue);
    }

    public static int getVibrantColor(Bitmap bitmap) {
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getVibrantColor(defaultValue);
    }

    public static int getLightVibrantColor(Bitmap bitmap){
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getLightVibrantColor(defaultValue);
    }

    public static int getDarkVibrantColor(Bitmap bitmap){
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getDarkVibrantColor(defaultValue);
    }

    //ACCENT COLORS

    public static int getMutedColor(Bitmap bitmap){
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getMutedColor(defaultValue);
    }

    public static int getLightMutedColor(Bitmap bitmap){
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getLightMutedColor(defaultValue);
    }

    public static int getDarkMutedColor(Bitmap bitmap){
        int defaultValue = 0x000000;
        Palette palette = Palette.from(bitmap).generate();
        return palette.getDarkMutedColor(defaultValue);
    }

    public static int getLighterShadeColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 1.35f;
        color = Color.HSVToColor(hsv);
        return color;
    }

    public static int getDarkerShadeColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.80f;
        color = Color.HSVToColor(hsv);
        return color;
    }

    public static int getTransparentColor(int color, double intensity){
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Set alpha based on your logic, here I'm making it 25% of it's initial value.
        alpha *= intensity;

        return Color.argb(alpha, red, green, blue);
    }

}