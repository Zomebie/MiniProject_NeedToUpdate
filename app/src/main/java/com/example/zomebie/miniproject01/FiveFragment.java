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

final class Tag
{
    public static final String _TAG="debug";

}

class Thr extends AsyncTask <Void, Void, Void> {

    private SubTitle st;      //SubTitle 클래스
    private ButtonClass bc;  // ButtonClass 클래스
    private FiveFragment.YouTube mt;  // innerclass Youtube 클래스

    Thr(SubTitle st, ButtonClass bc, FiveFragment.YouTube mt)
    {

        this.st = st;
        this.bc = bc;
        this.mt = mt;


    } // Thr 생성자


    @Override
    //Background Thread 에서 작업하기
    protected Void doInBackground(Void... voids)
    {

        // 현재 돌고 있는 스레드가 asyncTask 스레드 일까요? 메인 스레드 일까요?
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            Log.v(Tag._TAG, "Main Thread running");

        }else{

            Log.v(Tag._TAG,"asyncTask Thread running");
        }

        while (true)
        {

            // 해당 시간(해쉬맵의 키)에 해당 가사가 나올 시간(유튜브 영상의 시간)이 되면
            if (st.hm.containsKey(mt.myYouTubePlayer.getCurrentTimeMillis() / 1000))
            {
                // 해당 시간에 맞는 가사 띄우기
                publishProgress();  // onProgressUpdate() 메소드 호출

            }// if 문

        }// while 문

    }

    @Override
    // 메인 메소드에게 UI 작업을 시킴 from publishProgress() in doInBackground()
    protected void onProgressUpdate(Void... values)
    {

        super.onProgressUpdate(values);
        bc.tv.setText(st.hm.get(mt.myYouTubePlayer.getCurrentTimeMillis() / 1000));

    }


} // Thr class

class ButtonClass {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String song;  // 노래 제목
    String singer;  // 가수 이름
    int image;             // 앨범이미지
    boolean flaglist;


    private Button addButton;     //  영상을 my play list 에 저장할 버튼
    private Button deleteButton;    // 영상을 my play list에서 삭제할 버튼

    public TextView tv;  // 가사를 띄울 TextView


    ButtonClass(final Activity myActivity) {

        sharedPreferences = myActivity.getSharedPreferences("data",0); //데이터가 저장될 xml 이름 , 읽기 쓰기 가능 모드 0
        editor=sharedPreferences.edit(); // 에디터객체를 받아오기> 데이터를 저장하는 역할

        song="Maps";
        singer="Maroon5";
        image=R.drawable.maps;

        deleteButton = myActivity.findViewById(R.id.button2);
        addButton = myActivity.findViewById(R.id.button3);
        tv = myActivity.findViewById(R.id.textView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.remove("song0");
                editor.remove("singer0");
                editor.remove("image0");
                editor.commit();


                Toast.makeText(myActivity, "영상이 즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_LONG).show(); // 넘겨줬으면 확인 메세지 띄워주고


            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                flaglist=true;
                editor.putString("song0",song);
                editor.putString("singer0",singer);
                editor.putInt("image0",image);
                editor.putBoolean("flaglist",flaglist); // true 상태로 저장
                editor.commit();

                Toast.makeText(myActivity, "영상이 즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show();

                addButton.setBackgroundColor(Color.argb(0xff, 132, 132, 130)); // 버튼의 색깔 회색으로 바뀌고

                addButton.setClickable(false);  // 더 이상누를수없게




            }

        });




    } // ButtonClass 생성자


} // ButtonClass 클래스

class SubTitle {

    private Spinner sp;     // 스피너 타입 변수
    private String[] str;   // 스피너 목록 문자열
    private ArrayAdapter<String> adapter; // 스피너 어댑터

    private String[] eng; // 영어가사 문자열 배열 리소스를 받을 문자열 배열
    private String[] kor; // 한국어가사 문자열 배열 리소스를 받을 문자열 배열
    private String[] sub;  // 자막 container

    public HashMap<Integer, String> hm; // key [가사가 나오는 시간(초)], value[가사] 를 담을 해쉬맵

    SubTitle(final Activity ac) {

        hm = new HashMap<>(); // 해쉬맵 생성

        eng = ac.getResources().getStringArray(R.array.map_eng); // 영어가사 문자열 배열 리소스 담기
        kor = ac.getResources().getStringArray(R.array.map_kor); // 한국어가사 문자열 배열 리소스 담기

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

                hm.put(0, sub[0]);
                hm.put(4, sub[1]);
                hm.put(8, sub[2]);
                hm.put(12, sub[3]);
                hm.put(16, sub[4]);
                hm.put(19, sub[5]);
                hm.put(24, sub[6]);
                hm.put(27, sub[7]);
                hm.put(31, sub[8]);
                hm.put(35, sub[9]);
                hm.put(39, sub[10]);
                hm.put(42, sub[11]);
                hm.put(45, sub[12]);
                hm.put(47, sub[13]);
                hm.put(50, sub[14]);
                hm.put(53, sub[15]);
                hm.put(56, sub[16]);
                hm.put(59, sub[17]);
                hm.put(61, sub[18]);
                hm.put(62, sub[19]);
                hm.put(65, sub[20]);
                hm.put(66, sub[21]);
                hm.put(69, sub[22]);
                hm.put(70, sub[23]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        }); // setOnItemSelected 함수 끝


    } // SubTitle constructor

}// SubTitle class


public class FiveFragment extends YouTubeBaseActivity  {


    private YouTube youTube = null;
    private ButtonClass bc = null;
    private SubTitle st = null;
    private Thr thr = null;
    private Button button=null; // home button


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.five_fragment);


        new Intent(this.getIntent()); // 현재 액티비티으로 전환됨

        youTube = new YouTube(); // 유튜브 영상 클래스 ( innerClass 로 생성 )
        bc = new ButtonClass(this); // 레이아웃 기능( 텍스트뷰와 버튼2개) 클래스
        st = new SubTitle(this); // 스피너, 자막 관리 클래스
        thr = new Thr(st, bc, youTube); // asynkTask


        // home button
        button = findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thr.cancel(true); // home으로 가기 전에 스레드 중지

                finish(); // 불렀던 곳으로 돌아가기

            }
        });


    } // OnCreate 함수 끝


    // innerClass YouTube
    class YouTube implements YouTubePlayer.Provider {

        public static final String API_KEY = "AIzaSyCsaiAc4liJ9if0n1N_xAMUygpav0OB3YM ";
        public static final String VIDEO_ID = "aOJAWl12kII";    // 넣고자 하는 영상 주소

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

                if (!flag) {  // execute 는 한번만 불러져야 한다

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


