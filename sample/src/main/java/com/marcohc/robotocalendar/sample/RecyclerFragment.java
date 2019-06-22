package com.marcohc.robotocalendar.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marcohc.robotocalendarsample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015-12-25.
 */
public class RecyclerFragment extends Fragment {
    ViewGroup rootView;
    private ScheduleDataAdapter mAdapter;
    private String str,str2,start_time,end_time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recycler, container, false);


        if(getArguments() != null) {
            str  = getArguments().getString("title"); // 전달한 key 값 String param2 = getArguments().getString("param2"); // 전달한 key 값
            str2 = getArguments().getString("des");
            start_time = getArguments().getString("start");
            end_time = getArguments().getString("end");

        }
        else {
            Toast.makeText(getContext(),"오류",Toast.LENGTH_SHORT).show();
        }
        setScheduleDataAdapter(str,str2,start_time,end_time); // recyclerview 어댑터에 데이터 전송
        setupRecyclerView(); // recyclerview와 recyclerview어댑터 연결

        return rootView;
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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)); // 리사이클러 뷰에서 가장 많이 쓰이는 레이아웃으로 수평, 수직 스크롤을 제공하는 리스트를 만들 수 있습니다.

        recyclerView.setAdapter(mAdapter); // recyclerview와 recyclerview어댑터 연결
    }

}
