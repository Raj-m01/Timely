package com.example.timely;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CountDownTimer cTimer = null;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            createNotificationChannel();
            if(getSupportActionBar()!=null) getSupportActionBar().hide();

            setTitle("");
            setSupportActionBar(binding.toolbar);

            sharedPreferences = getSharedPreferences("GET_TIMER",MODE_PRIVATE);
            long timer = sharedPreferences.getLong("timer",0L);

            if(timer!=0) {
                long remTime = timer - Calendar.getInstance().getTimeInMillis();

                long tt = TimeUnit.MILLISECONDS.toSeconds(remTime);

                Log.d("checksec"," "+tt);
                reverseTimer(tt,binding.timerText);
            }


            binding.cancelTimer.setOnClickListener(v -> {

                if(cTimer!=null)cancelTimer();

                binding.timerText.setText("00:00:00");
                Toast.makeText(this, "Timer Cancelled", Toast.LENGTH_SHORT).show();
            });

            binding.setTime.setOnClickListener(v -> {

                MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, new MyTimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {

                        long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();

                        if(cTimer!=null)cancelTimer();

                        long totalSec = seconds + minute*60 + (hourOfDay*60*60);
                        long totalTime = currentTimeInMillis + TimeUnit.SECONDS.toMillis(totalSec);

                        reverseTimer(totalSec,binding.timerText);

                        SharedPreferences sharedPreferences = getSharedPreferences("GET_TIMER",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putLong("timer",totalTime).apply();

                        Log.d("totalTime"," "+totalTime);
                        setTimer(totalTime); // totalTime = 4:15

                    }
                }, 00, 00, 00, true);
                mTimePicker.show();

            });

    }

    private void setTimer(long totalTime){

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this,Receiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,totalTime,pendingIntent);

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
                tv.setText("00:00:00");
            }
        }.start();
    }

    //cancel timer
    void cancelTimer() {
            if(cTimer!=null)
                cTimer.cancel();

            Intent intent = new Intent(this,Receiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            if (alarmManager == null){
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }

            SharedPreferences sharedPreferences = getSharedPreferences("GET_TIMER",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear().apply();
    }



    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
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