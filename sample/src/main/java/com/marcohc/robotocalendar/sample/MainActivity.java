package com.marcohc.robotocalendar.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.marcohc.robotocalendarsample.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private RobotoCalendarView robotoCalendarView; // 라이브러리 클래스
    private ScheduleDataAdapter mAdapter; // recyclerview 어댑터
    private RecyclerFragment recyclerFragment;
    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;
    private boolean empty_check=true; // 일정이 없을 경우


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        robotoCalendarView = findViewById(R.id.robotoCalendarPicker);

        manager = getSupportFragmentManager();
        recyclerFragment = new RecyclerFragment();
        fragmentTransaction = manager.beginTransaction();

        // Mainactivity에 robotocalendarview 라이브러리 연결
        robotoCalendarView.setRobotoCalendarListener(this);
        robotoCalendarView.setShortWeekDays(false);
        robotoCalendarView.showDateTitle(true);
        robotoCalendarView.setDate(new Date());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDayClick(Date date) {
        Schedule_Dialog dialog = new Schedule_Dialog(this,date);  // 다이얼로그 클래스
        dialog.StyleDialogListener(new Schedule_Dialog.StyleDialogListener() { // 다이얼로그로부터 데이터를 받기 위한 리스너 연결
            @Override
            public void onSelectedStyle(String str, String str2, String start_time, String end_time,boolean com) { // 일정제목, 일정내용 , 시작시간, 종료시간

                Bundle bundle = new Bundle();
                bundle.putString("title", str);
                bundle.putString("des", str2);
                bundle.putString("start", start_time);
                bundle.putString("end", end_time);
                recyclerFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.recyclerFragment, recyclerFragment);
                fragmentTransaction.commit();



                if (com){ // 일정등록한 경우 빨간점 표시
                    empty_check=false; // 일정이 추가 되면
                    robotoCalendarView.markCircleImage1(date);
                }

            }
        });
        dialog.show();
        //Toast.makeText(this, "onDayClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDayLongClick(Date date) {
        //Toast.makeText(this, "onDayLongClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick() {
        //Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick() {
        //Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
    }

    private void setScheduleDataAdapter(String str1,String str2,String str3,String str4) {
        List<Schedule> schedules = new ArrayList<>();

        Schedule data1 = new Schedule(); // recylcerview로 보여줄 Schedule 데이터 클래스 정의
        data1.setTitle(str1);
        data1.setDes(str2);
        data1.setStart_time(str3);
        data1.setEnd_time(str4);
        schedules.add(data1);


        mAdapter = new ScheduleDataAdapter(schedules); // 데이터들을 어댑터에 전송
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)); // 리사이클러 뷰에서 가장 많이 쓰이는 레이아웃으로 수평, 수직 스크롤을 제공하는 리스트를 만들 수 있습니다.

        recyclerView.setAdapter(mAdapter); // recyclerview와 recyclerview어댑터 연결
    }

}
