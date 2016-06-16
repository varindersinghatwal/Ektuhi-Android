package com.jeifbell.ektuhi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.*;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;


public class ArticleContent extends Activity {
    MediaPlayer player;
    int duration = 0;
    String audioname = "pg001.mp3";
    int audiocounterTxt = 0;
    String stxt1="";
    public ArrayList<String> mThumbIds = new ArrayList<String>();
    public ArrayList<String> timestamp = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.article_content);

        Intent inten = getIntent();
        audiocounterTxt = inten.getIntExtra("audio",0);
        audioname = "Audio/Page "+(audiocounterTxt+1)+".mp3";
        new PrefetchData().execute();
        player = new MediaPlayer();
        player.reset();

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(player!=null && player.getCurrentPosition()<player.getDuration()){

                                final TextView txt = (TextView)findViewById(R.id.textView3);
                                final SpannableString ss = new SpannableString(stxt1);
                                int start = 0;
                                int end;
                                int rigoTime = 10;
                                for(int i=0;i<mThumbIds.size();i++) {
                                    String word = mThumbIds.get(i);
                                    final int time = (int) (parseFloat(timestamp.get(i))) * 1000;
                                    end = start + word.length();
                                    if(time!=0 || i==0) {
                                        ss.setSpan(new NoUnderLine() {
                                            @Override
                                            public void onClick(View widget) {
                                                player.reset();
                                                startSound(audioname, time);
                                                //Toast.makeText(MainActivity.this,ct,Toast.LENGTH_SHORT).show();
                                            }
                                        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        if(i<mThumbIds.size()-1){
                                            int nextTime = (int) (parseFloat(timestamp.get(i+1))) * 1000;
                                            rigoTime = nextTime==0?(time+1000):nextTime;
                                        }
                                        if (time < player.getCurrentPosition() && rigoTime>player.getCurrentPosition()) {
                                            ss.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, 0);
                                            ss.setSpan(new BackgroundColorSpan(Color.CYAN),start,end,0);
                                        }
                                    }
                                    start = start + word.length() +1;
                                }
                                final Typeface tf = Typeface.createFromAsset(getAssets(),"EkTuhiBaani.ttf");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt.setTypeface(tf);
                                        txt.setMovementMethod(LinkMovementMethod.getInstance());
                                        txt.setText(ss, TextView.BufferType.SPANNABLE);
                                    }
                                });

                                try{
                                    Thread.sleep(100);
                                }catch (Exception e){
                                    System.out.println("Not done");
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Something went wrong"+e);
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        player.release();
    }

    private void startSound(String filename, int from){
        try{
            AssetFileDescriptor afd = getAssets().openFd(filename);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            duration = player.getDuration();
            player.seekTo(from);
            player.start();
        }catch (Exception e){
            //do nothing
        }
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ArticleContent.this);
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Reader reader = new InputStreamReader(ArticleContent.this.getAssets().open("newtxt/pg"+(audiocounterTxt+1)+".txt"));
                BufferedReader fin = new BufferedReader(reader);
                String s;
                while ((s = fin.readLine()) != null) {
                    String[] sit = s.split(" ");
                    mThumbIds.add(sit[0]);
                    timestamp.add(sit[1]);

                }
                fin.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            pd.dismiss();
            for (int m = 0; m < mThumbIds.size(); m++) {
                String hex = mThumbIds.get(m);
                ByteBuffer buff = ByteBuffer.allocate(hex.length() / 2);
                for (int i = 0; i < hex.length(); i += 2) {
                    buff.put((byte) Integer.parseInt(hex.substring(i, i + 2), 16));
                }
                buff.rewind();
                Charset cs = Charset.forName("UTF-8");
                CharBuffer cb = cs.decode(buff);
                String enstrcb = cb.toString();
                mThumbIds.set(m,enstrcb);
            }
            for(int i=0;i<mThumbIds.size();i++){
                if(i==0){
                    stxt1 = mThumbIds.get(i);
                }else{
                    stxt1 = stxt1 + " " + mThumbIds.get(i);
                }
            }
            Typeface tf1 = Typeface.createFromAsset(getAssets(),"EkTuhiBaani.ttf");
            TextView txt1 = (TextView)findViewById(R.id.textView3);
            txt1.setTypeface(tf1);
            txt1.setText(stxt1);
            startSound(audioname,0);
        }
    }
}
