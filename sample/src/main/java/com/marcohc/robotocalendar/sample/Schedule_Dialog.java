package com.marcohc.robotocalendar.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.marcohc.robotocalendarsample.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Schedule_Dialog extends Dialog implements View.OnClickListener,TimePickerDialog.OnTimeSetListener{
    public StyleDialogListener styleDialogListener; // Dialog를 통해 Mainactivity로 데이터 전송위한 리스너
    private Context context;
    EditText schedule_title,schedule_des;
    TextView start_time,end_time;
    int hour,minute; // 현재의 시,분을 정의하기 위한 변수
    TimePickerDialog tpd,tpd2;
    boolean dif,com ; // dif : Timepicker 2개를 구분하기 위한 변수 com : 일정등록 후 날짜 밑에 빨간 점을 찍을지의 유무 변수



    public Schedule_Dialog(Context context,Date date) {
        super(context);
        this.context=context;

    }

    public interface StyleDialogListener{ // 인터페이스로 리스너 설정
        void onSelectedStyle(String str, String str2, String start_time, String end_time,boolean com);
    }

    //호출할 리스너 초기화
    public void StyleDialogListener(StyleDialogListener styleDialogListener){
        this.styleDialogListener = styleDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.

        setContentView(R.layout.schedule_dialog);

        schedule_title=findViewById(R.id.schedule_title); schedule_des=findViewById(R.id.schedule_des);
        start_time=findViewById(R.id.start_time); end_time=findViewById(R.id.end_time);

        Calendar getcur_time = Calendar.getInstance(); // 현재 시간을 얻기위한 클래스 Calendar 객체의 인스턴스 얻기
        int hour = getcur_time.get(Calendar.HOUR_OF_DAY); // 현재 시
        int minute = getcur_time.get(Calendar.MINUTE); // 현재 분
        start_time.setText(String.valueOf(hour)+":00");
        end_time.setText(String.valueOf(hour+1)+":00");

        findViewById(R.id.start_time).setOnClickListener(this);
        findViewById(R.id.end_time).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:

                String schedule_str1 = schedule_title.getText().toString().trim();
                String schedule_str2 = schedule_des.getText().toString().trim();
                String s_time = start_time.getText().toString().trim();
                String e_time = end_time.getText().toString().trim();

                if (TextUtils.isEmpty(schedule_str1) || TextUtils.isEmpty(schedule_str2)) {  // dialog textview들의 null 체크
                        Toast.makeText(context, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
                        com=false;
                } else if (!TextUtils.isEmpty(schedule_str1) && !TextUtils.isEmpty(schedule_str2)) {
                        com=true;
                        styleDialogListener.onSelectedStyle(schedule_str1, schedule_str2, s_time, e_time , com); // 리스너로 dialog 데이터들 전송
                        dismiss();
                        break;
                }
                break;

            case R.id.start_time:

                tpd = new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,this,hour,minute,false); // Timepickerdialog
                // Timepickerdialog 파라미터 정의 (현재의 context,시계를 보여주는 형식, 리스너연결장소, 현재시,분,24시간뷰어를 설정할것인가)
                TextView tv = new TextView(context);
                tv.setPadding(5,3,5,3);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                dif=false; // start_time 설정을 완료했으니 end_time의 시간설정을 위해 false로 설정
                tpd.setCustomTitle(tv);
                tpd.show();
                break;
            case R.id.end_time:
                tpd2 = new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,this,hour,minute,false);
                TextView tv2 = new TextView(context);
                tv2.setPadding(5,3,5,3);
                tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                dif=true;
                tpd2.setCustomTitle(tv2);
                tpd2.show();
                break;

        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) { // Timepicker의 시간 설정

        if(!dif) {
            if (i < 10)  // 시간의 형식을 00:00 형식을 맞추기 위해 설정해 놓음
                start_time.setText("0" + String.valueOf(i) + ":" + String.valueOf(i1));
            else
                start_time.setText(String.valueOf(i) + ":" + String.valueOf(i1));

            if (i1 < 10)
                start_time.setText(String.valueOf(i) + ":0" + String.valueOf(i1));
            else
                start_time.setText(String.valueOf(i) + ":" + String.valueOf(i1));
        }else {
            if (i < 10)
                end_time.setText("0" + String.valueOf(i) + ":" + String.valueOf(i1));
            else
                end_time.setText(String.valueOf(i) + ":" + String.valueOf(i1));

            if (i1 < 10)
                end_time.setText(String.valueOf(i) + ":0" + String.valueOf(i1));
            else
                end_time.setText(String.valueOf(i) + ":" + String.valueOf(i1));
        }
    }
}
