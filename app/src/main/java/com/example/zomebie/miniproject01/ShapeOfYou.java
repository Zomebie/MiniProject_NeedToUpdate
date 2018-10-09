package com.example.zomebie.miniproject01;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;

class Thr2 extends AsyncTask <Void, Void, Void> {

    private SubTitle2 st;
    private ButtonClass2 bc;
    private ShapeOfYou.YouTube mt;

    Thr2(SubTitle2 st, ButtonClass2 bc, ShapeOfYou.YouTube mt)
    {

        this.st = st;
        this.bc = bc;
        this.mt = mt;


    }



    protected Void doInBackground(Void... voids)
    {

        while (true)
        {

            if (st.hm.containsKey(mt.myYouTubePlayer.getCurrentTimeMillis() / 1000))
            {

                publishProgress();

            }

        }

    }

    protected void onProgressUpdate(Void... values)
    {

        super.onProgressUpdate(values);
        bc.tv.setText(st.hm.get(mt.myYouTubePlayer.getCurrentTimeMillis() / 1000));

    }


} // Thr class

class ButtonClass2 {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String song;
    String singer;
    int image;
    boolean flaglist;

    private Button addButton;
    private Button deleteButton;
    public TextView tv;

    ButtonClass2(final Activity myActivity) {

        sharedPreferences = myActivity.getSharedPreferences("data",0);
        editor=sharedPreferences.edit();

        song="Shape of you";
        singer="Ed sheeran";
        image=R.drawable.shapeofyou;

        deleteButton = myActivity.findViewById(R.id.button2);
        addButton = myActivity.findViewById(R.id.button3);
        tv = myActivity.findViewById(R.id.textView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.remove("song1");
                editor.remove("singer1");
                editor.remove("image1");
                editor.commit();


                Toast.makeText(myActivity, "영상이 즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_LONG).show(); // 넘겨줬으면 확인 메세지 띄워주고


            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                flaglist=true;
                editor.putString("song1",song);
                editor.putString("singer1",singer);
                editor.putInt("image1",image);
                editor.putBoolean("flaglist",flaglist); // true 상태로 저장
                editor.commit();

                Toast.makeText(myActivity, "영상이 즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show();

                addButton.setBackgroundColor(Color.argb(0xff, 132, 132, 130)); // 버튼의 색깔 회색으로 바뀌고

                addButton.setClickable(false);  // 더 이상누를수없게




            }

        });




    } // ButtonClass 생성자


} // ButtonClass 클래스

class SubTitle2 {

    private Spinner sp;
    private String[] str;
    private ArrayAdapter<String> adapter;

    private String[] eng;
    private String[] kor;
    private String[] sub;

    public HashMap<Integer, String> hm;

    SubTitle2(final Activity ac) {

        hm = new HashMap<>();

        eng = ac.getResources().getStringArray(R.array.shape_eng);
         kor = ac.getResources().getStringArray(R.array.shape_kor);

        sp = ac.findViewById(R.id.spinner);
        str = new String[]{"Setting > English", "Setting > Korean ", "Setting > English,Korean"}; // 스피너에 넣고 싶은 목록

        adapter = new ArrayAdapter<>(ac, android.R.layout.simple_spinner_item, str); // 스퍼너와 스피너 목록을 연결시켜 줄 어댑터
        sp.setAdapter(adapter); // 스피너에 만들어준 어댑터 채우기

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        sub = eng;

                        break; // 영어 가사

                    case 1:
                        sub = kor;
                        break; // 한국어 가사

                    case 2:
                        break; // 영어와 한국어 같이

                    default:
                        break;

                } // switch

                hm.put(10, sub[0]);
                hm.put(12, sub[1]);
                hm.put(14, sub[2]);
                hm.put(17, sub[3]);
                hm.put(19, sub[4]);
                hm.put(22, sub[5]);
                hm.put(24, sub[6]);
                hm.put(27, sub[7]);
                hm.put(29, sub[8]);
                hm.put(32, sub[9]);
                hm.put(35, sub[10]);
                hm.put(37, sub[11]);
                hm.put(39, sub[12]);
                hm.put(42, sub[13]);
                hm.put(45, sub[14]);
                hm.put(47, sub[15]);
                hm.put(49, sub[16]);
                hm.put(52, sub[17]);
                hm.put(55, sub[18]);
                hm.put(58, sub[19]);
                hm.put(60, sub[20]);
                hm.put(62, sub[21]);
                hm.put(65, sub[22]);
                hm.put(68, sub[23]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        }); // setOnItemSelected 함수 끝


    } // SubTitle constructor

}// SubTitle class


public class ShapeOfYou extends YouTubeBaseActivity {

    private YouTube youTube = null;
    private ButtonClass2 bc = null;
    private SubTitle2 st = null;
    private Thr2 thr = null;

    private Button button=null; // home button
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shapeofyou);

        youTube = new YouTube(); // 유튜브 영상 클래스 ( innerClass 로 생성 )

        bc = new ButtonClass2(this); // 레이아웃( 텍스트뷰와 버튼2개) 클래스
        st = new SubTitle2(this); // 스피너, 자막 관리 클래스
        thr = new Thr2(st, bc, youTube); // asynkTask

       new Intent(this.getIntent());

        button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thr.cancel(true);
                finish();
            }
        });

    } // OnCreate 함수 끝


    // innerClass YouTube
    class YouTube implements YouTubePlayer.Provider {

        public static final String API_KEY = "AIzaSyCsaiAc4liJ9if0n1N_xAMUygpav0OB3YM ";
        public static final String VIDEO_ID = "dZpVNLOzAZo";    // 넣고자 하는 영상 주소

        public YouTubePlayerView youTubePlayerView;
        public YouTubePlayer myYouTubePlayer = null;

        boolean flag;

        public YouTube() {
            flag = false;

            youTubePlayerView = findViewById(R.id.youtube_player); // 영상 띄울 곳
            youTubePlayerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() { // 영상 초기화
                @Override
                public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                    youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                    youTubePlayer.setPlaybackEventListener(playbackEventListener);

                    if (!b) {

                        youTubePlayer.cueVideo(VIDEO_ID); // 넣고자 하는 영상 넣기
                    }

                    myYouTubePlayer = youTubePlayer; // YouTubePlayer interface 안에 우리가 쓰고자 하는 getCurrentTimeMills()가 있음, 메소드의
                    // 매개변수로 YouTubePlayer 인스턴스 생성


                }

                @Override
                public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(Tag._TAG, "영상 초기화 실패");

                }

            });


        } // youTubePlayerView.initialize


        public PlaybackEventListener playbackEventListener = new PlaybackEventListener() {

            @Override
            public void onPlaying() {

                Log.v(Tag._TAG, "onPlaying");


            }

            @Override
            public void onPaused() {


                Log.v(Tag._TAG, "onPaused");

            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }


        }; // PlaybackEventListener interface


        private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

                if (!flag) {// execute 는 한번만 불러져야 한다

                    flag = true;

                    thr.execute();

                }

            }

            @Override
            public void onVideoEnded() {

                thr.cancel(true);
                //단점:AsyncTask를 execute한 Activity가 종료되었을 때 별도의 지시가 없다면 AsyncTask는 종료되지 않고 계속 돌아간다.
            }

            @Override
            public void onError(ErrorReason errorReason) {

            }

        }; //PlayerStateChangeListener interface


        @Override
        public void initialize(String s, YouTubePlayer.OnInitializedListener onInitializedListener) {

        }

    } // YouTube innerclass


} // MainActivity class

