package com.androidapp.demafayz.aberoy.features.lecturerlist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.interfaces.OnRecyclerClickListener;
import com.androidapp.demafayz.aberoy.network.entitys.Lecture;
import com.androidapp.demafayz.aberoy.network.entitys.Lecturer;
import com.androidapp.demafayz.aberoy.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class LecturersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Lecturer> mLecturers;
    private OnRecyclerClickListener mOnClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvName)
        public TextView tvName;

        @BindView(R.id.tvSurname)
        public TextView tvSurname;

        @BindView(R.id.tvLectures)
        public TextView tvLectures;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener == null) return;
            mOnClickListener.onRecyclerClickListener(v);
        }
    }

    public LecturersAdapter(List<Lecturer> lecturers) {
        mLecturers = lecturers == null ? new ArrayList<Lecturer>() : lecturers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        View itemView = mLayoutInflater.inflate(R.layout.activity_lecturer_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        String name = mLecturers.get(position).getFirstName();
        String lastName = mLecturers.get(position).getLastName();
        List<String> lectureStringList = new ArrayList<>();
        for (Lecture lecture : mLecturers.get(position).getLectures()) {
            lectureStringList.add(lecture.getTitle());
        }
        String lectures = StringUtils.stringTransfer(lectureStringList);

        vh.tvName.setText(name);
        vh.tvSurname.setText(lastName);
        vh.tvLectures.setText(lectures);
    }

    @Override
    public int getItemCount() {
        return mLecturers.size();
    }

    public Lecturer getItemByPosition(int position) {
        return mLecturers.get(position);
    }


    public void setOnClickListener(OnRecyclerClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }
}
