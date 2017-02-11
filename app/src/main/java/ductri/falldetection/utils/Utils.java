package ductri.falldetection.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ductr on 12/21/2016.
 */
public class Utils {
    public static String TAG = "FALL_DETECTION";
    private static MediaPlayer mp;

    public static void playSound(Context context, int soundID){
        if (mp == null) {
            mp = MediaPlayer.create(context, soundID);
        }
        mp.setLooping(true);
        mp.start();
    }

    public static void stopSound() {
        if (mp != null) {
            mp.stop();
            mp = null;
        }
    }

    public static void sendSMS(String phoneNo, String messageText) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, messageText, null, null);
        Log.i(Utils.TAG, "Message sent");
    }
}
