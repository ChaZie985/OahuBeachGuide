package com.example.congressionalapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class ProximitySafetyWorker extends Worker {

    private static final String CHANNEL_ID = "BEACH_SAFETY_ALERTS";
    private static final float PROXIMITY_RADIUS_MILES = 1.0f;

    public ProximitySafetyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    evaluateNearbyBeaches(location);
                }
            });
        } catch (SecurityException e) {
            return Result.failure();
        }

        return Result.success();
    }

    private void evaluateNearbyBeaches(Location userLocation) {
        List<BeachLocation> beaches = BeachRepository.getBeaches();
        WaveDataCoordinator waveCoordinator = new WaveDataCoordinator();

        for (BeachLocation beach : beaches) {
            float[] results = new float[1];
            Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                    beach.getLatitude(), beach.getLongitude(), results);
            float distanceMiles = results[0] / 1609.34f;

            if (distanceMiles <= PROXIMITY_RADIUS_MILES) {
                waveCoordinator.fetchWaveData(beach, new WaveDataProvider.Callback() {
                    @Override
                    public void onSuccess(WaveConditionReport data) {
                        float heightFeet = data.getHeightFeet();
                        float actualShoreHeight = heightFeet;
                        
                        if (beach.isReefProtected()) {
                            actualShoreHeight = heightFeet * 0.1f;
                            if (actualShoreHeight > 1.0f) actualShoreHeight = 1.0f;
                        } else if (beach.isBreaksFarOut()) {
                            actualShoreHeight = heightFeet * 0.35f;
                        }

                        if (!beach.isReefProtected() && !beach.isBreaksFarOut() && actualShoreHeight >= 2.5f) {
                            dispatchNotification(beach.getName(), actualShoreHeight);
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                    }
                });
            }
        }
    }

    private void dispatchNotification(String beachName, float waveHeight) {
        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Beach Safety Alerts", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("DANGER: Heavy Shore Break")
                .setContentText(String.format(Locale.US, "You are nearing %s. Waves are %.1f ft and dangerous for tourists today!", beachName, waveHeight))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(beachName.hashCode(), builder.build());
    }
}
