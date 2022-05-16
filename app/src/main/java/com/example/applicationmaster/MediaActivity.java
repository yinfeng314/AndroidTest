package com.example.applicationmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MediaActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midia);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(MediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MediaActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            initMediaPlayer();          //初始化MediaPlayer
        }
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());      //设置路径
            mediaPlayer.prepare();     //进入准备状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else {
                    Toast.makeText(this, "拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}