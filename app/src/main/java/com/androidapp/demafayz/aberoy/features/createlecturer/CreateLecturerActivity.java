package com.androidapp.demafayz.aberoy.features.createlecturer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by DemaFayz on 04.12.2016.
 */

public class CreateLecturerActivity extends BaseActivity {

    private ViewHolder mVh;

    class ViewHolder {

        public ViewHolder(View layout) {
            ButterKnife.bind(this, layout);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateViewHolder();
    }

    private void populateViewHolder() {
        View layout = LayoutInflater.from(this).inflate(R.layout.activity_lecturer_create, null, true);
        setContentView(layout);
        mVh = new ViewHolder(layout);
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, CreateLecturerActivity.class);
        context.startActivity(intent);
    }
}
