package com.example.timely;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timely.databinding.ActivityMainBinding;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CountDownTimer cTimer = null;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();
        if(getSupportActionBar()!=null)getSupportActionBar().hide();


        setSupportActionBar(binding.toolbar);


        binding.cancelTimer.setOnClickListener(v -> {

            if(cTimer!=null)cancelTimer();

            Intent intent = new Intent(this,Receiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            if (alarmManager == null)alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);
            binding.timerText.setText("00 :00 :00");
            Toast.makeText(this, "Timer Cancelled", Toast.LENGTH_SHORT).show();

        });

        binding.setTime.setOnClickListener(v -> {

            MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, new MyTimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {

                    long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();

                    if(cTimer!=null)cancelTimer();

                    long totalSec = seconds + minute*60 + (hourOfDay*60*60);
                    long millis = TimeUnit.SECONDS.toMillis(totalSec);
                    long totalTime = currentTimeInMillis + millis;

                    reverseTimer(totalSec,binding.timerText);
                    setTimer(totalTime);

                }
            }, 00, 00, 00, true);
            mTimePicker.show();

        });

    }



    public void reverseTimer(long Seconds,final TextView tv){

        cTimer = new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {

                @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                tv.setText(hms);

            }

            public void onFinish() {
            }
        }.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }


    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    private void setTimer(long totalTime){

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this,Receiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,totalTime,pendingIntent);


    }



    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "timercomplete";
            String description = "notify timercomplete";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Receiver.MY_NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}