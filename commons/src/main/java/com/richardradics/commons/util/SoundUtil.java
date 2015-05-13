package com.richardradics.commons.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import java.io.IOException;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class SoundUtil {

    /**
     * Plays sound with the phone sound settings.
     * @param context
     * @param rawSoundId
     * @param vibrateMillies
     */
    public static void playSoundWithUserSettings(Context context, int rawSoundId, int vibrateMillies) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                vibrate(context, vibrateMillies);
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                playRawSound(rawSoundId, context);
                vibrate(context, vibrateMillies);
                break;
        }
    }

    /**
     * Plays a raw sound from assets.
     * @param rawSoundId
     * @param ctx
     */
    public static void playRawSound(int rawSoundId, Context ctx) {
        AssetManager am;
        MediaPlayer player;
        try {
            am = ctx.getAssets();
            String sUri = "android.resource://" + ctx.getPackageName() + "/" + rawSoundId;
            Uri mUri = Uri.parse(sUri);
            ContentResolver mCr = ctx.getContentResolver();
            AssetFileDescriptor afd = mCr.openAssetFileDescriptor(mUri, "r");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.release();
                }

            });
            player.setLooping(false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sets default ringtone.
     * @param rawSoundId
     * @param ctx
     */
    public static void setRawSoundRingTone(int rawSoundId, Context ctx) {
        Uri path = Uri.parse("android.resource://" + ctx.getPackageName() + "/raw/" + rawSoundId);
        RingtoneManager.setActualDefaultRingtoneUri(ctx, RingtoneManager.TYPE_RINGTONE, path);
        RingtoneManager.getRingtone(ctx, path).play();
    }

    /**
     * Vibrates the device.
     * @param context
     * @param vibrateMilliSec
     */
    public static void vibrate(Context context, int vibrateMilliSec) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(vibrateMilliSec);
    }


}
