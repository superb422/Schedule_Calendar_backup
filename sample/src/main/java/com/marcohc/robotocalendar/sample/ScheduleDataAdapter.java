package com.marcohc.robotocalendar.sample;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.marcohc.robotocalendarsample.R;

import java.util.List;

public class ScheduleDataAdapter extends RecyclerView.Adapter<ScheduleDataAdapter.ScheduleViewHolder> {
    public List<Schedule> schedules;

    public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,des,start_time,end_time;

        public ScheduleViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            des = (TextView) view.findViewById(R.id.des);
            start_time = (TextView)view.findViewById(R.id.t_start);
            end_time = (TextView)view.findViewById(R.id.t_end);
            //Image = (ImageView)view.findViewById(R.id.User_image);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext() , MainActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    public ScheduleDataAdapter(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰 홀더를 생성하고 뷰를 붙여주는 부분입니다.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_data, parent, false);

        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) { // 재활용 되는 뷰가 호출하여 실행되는 메소드, 뷰 홀더를 전달하고 어댑터는 position 의 데이터를 결합시킵니다.

        Schedule schedule = schedules.get(position);
        holder.title.setText(schedule.getTitle());
        holder.des.setText(schedule.getDes());
        holder.start_time.setText(schedule.getStart_time());
        holder.end_time.setText(schedule.getEnd_time());
       // holder.Image.setImageResource(Ticket.getImage());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    } // 데이터의 개수 반환
}
