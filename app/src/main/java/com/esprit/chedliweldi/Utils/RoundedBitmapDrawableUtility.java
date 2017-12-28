package com.esprit.chedliweldi.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;



/**
 * Created by oussama_2 on 12/5/2017.
 */

public class RoundedBitmapDrawableUtility {

    public static RoundedBitmapDrawable getRoundedSquareBitmapDrawable(Context context, Bitmap originalBitmap, int cornerRadius){
        return getRoundedSquareBitmapDrawable(context, originalBitmap, cornerRadius, -1, -1);
    }


    public static RoundedBitmapDrawable getRoundedSquareBitmapDrawable(Context context, Bitmap originalBitmap, int cornerRadius, int borderWidth, int borderColor){
        int originalBitmapWidth = originalBitmap.getWidth();
        int originalBitmapHeight = originalBitmap.getHeight();

        if(borderWidth != -1 && borderColor != -1){
            Canvas canvas = new Canvas(originalBitmap);
            canvas.drawBitmap(originalBitmap, 0, 0, null);

            Paint borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderWidth);
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);

            int roundedRectDelta = (borderWidth/3);
            RectF rectF = new RectF(0 + roundedRectDelta, 0 + roundedRectDelta, originalBitmapWidth - roundedRectDelta, originalBitmapHeight - roundedRectDelta);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, borderPaint);
        }

        RoundedBitmapDrawable roundedImageBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
        roundedImageBitmapDrawable.setCornerRadius(cornerRadius);
        roundedImageBitmapDrawable.setAntiAlias(true);
        return roundedImageBitmapDrawable;
    }

    public static RoundedBitmapDrawable getCircleBitmapDrawable(Context context, Bitmap   originalBitmap){
        return getCircleBitmapDrawable(context, originalBitmap, -1, -1);
    }

    public static RoundedBitmapDrawable getCircleBitmapDrawable(Context context, Bitmap originalBitmap, int borderWidth, int borderColor){
        if(borderWidth != -1 && borderColor != -1) {
            Canvas canvas = new Canvas(originalBitmap);
            canvas.drawBitmap(originalBitmap, 0, 0, null);

            Paint borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderWidth);
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);

            int circleDelta = (borderWidth / 2) - DisplayUtility.dp2px(context, 1);
            int radius = (canvas.getWidth() / 2) - circleDelta;
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius, borderPaint);
        }

        RoundedBitmapDrawable roundedImageBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
        roundedImageBitmapDrawable.setCircular(true);
        roundedImageBitmapDrawable.setAntiAlias(true);
        return roundedImageBitmapDrawable;
    }
}