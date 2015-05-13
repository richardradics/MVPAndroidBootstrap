package com.richardradics.commons.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
/**
 * Created by Richard Radics on 2015.02.09..
 */
/**
 * Created by Rcsk on 15/10/2014.
 */
public class ImageUtil {

    /**
     * Convets dp to px.
     * @param context
     * @param dp
     * @return
     */
    private static int dpToPx(Context context, int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    /**
     * Smooth scaler algorithm.
     * @param view
     * @param context
     */
    public static void scaleImage(ImageView view, Context context){
        try {
            Drawable drawing = view.getDrawable();
            if (drawing == null) {
                return; // Checking for null & return, as suggested in comments
            }
            Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

            // Get current dimensions AND the desired bounding box
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int bounding = dpToPx(context, 250);
            Log.i("ImageResizer", "original width = " + Integer.toString(width));
            Log.i("ImageResizer", "original height = " + Integer.toString(height));
            Log.i("ImageResizer", "bounding = " + Integer.toString(bounding));

            // Determine how much to scale: the dimension requiring less scaling is
            // closer to the its side. This way the image always stays inside your
            // bounding box AND either x/y axis touches it.
            float xScale = ((float) bounding) / width;
            float yScale = ((float) bounding) / height;
            float scale = (xScale <= yScale) ? xScale : yScale;
            Log.i("ImageResizer", "xScale = " + Float.toString(xScale));
            Log.i("ImageResizer", "yScale = " + Float.toString(yScale));
            Log.i("ImageResizer", "scale = " + Float.toString(scale));

            // Create a matrix for the scaling and add the scaling data
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            // Create a new bitmap and convert it to a format understood by the ImageView
            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            width = scaledBitmap.getWidth(); // re-use
            height = scaledBitmap.getHeight(); // re-use
            BitmapDrawable result = new BitmapDrawable(scaledBitmap);
            Log.i("ImageResizer", "scaled width = " + Integer.toString(width));
            Log.i("ImageResizer", "scaled height = " + Integer.toString(height));

            // Apply the scaled bitmap
            view.setImageDrawable(result);

            //scaledBitmap.recycle();
            //bitmap.recycle();
            // Now change ImageView's dimensions to match the scaled image
            // FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            //params.width = width;
            //params.height = height;
            //view.setLayoutParams(params);

            Log.i("ImageResizer", "done");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
