package com.androidapp.demafayz.aberoy.features.lectureritem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;
import com.androidapp.demafayz.aberoy.network.SyncAPI;
import com.androidapp.demafayz.aberoy.network.data.RequestResult;
import com.androidapp.demafayz.aberoy.network.entitys.Experience;
import com.androidapp.demafayz.aberoy.network.entitys.Lecture;
import com.androidapp.demafayz.aberoy.network.entitys.Lecturer;
import com.androidapp.demafayz.aberoy.utils.DateUtils;
import com.androidapp.demafayz.aberoy.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by DemaFayz on 30.11.2016.
 */

public class LecturerItemActivity extends BaseActivity {

    private static final String TAG = LecturerItemActivity.class.getSimpleName();
    private static final String EXTRA_TAG_ID = TAG + "_lecturer_id";

    private long mItemId = -1;

    private ViewHolder mVh;
    private Lecturer mLecturer;

    class ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.tvSurname)
        TextView tvSurname;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        @BindView(R.id.tvDate)
        TextView tvDate;

        public ViewHolder(View layout) {
            ButterKnife.bind(this, layout);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateViewHolder();
        initData(savedInstanceState);
        loadLecturerData();
    }

    private void loadLecturerData() {
        Observable<RequestResult> observable = populateLecturerObservable();
        Subscription subscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RequestResult>() {
            @Override
            public void onCompleted() {
                if (mLecturer != null) {
                    showLecturerData();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RequestResult requestResult) {
                if (requestResult.getCode() == HttpURLConnection.HTTP_OK) {
                    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
                    mLecturer = gson.fromJson(requestResult.getMessage(), Lecturer.class);
                }
            }
        });
        addSubscription(subscription);
    }

    private void showLecturerData() {
        String name;
        String surname;
        String age;
        String description;

        name = mLecturer.getFirstName();
        surname = mLecturer.getLastName();
        age = DateUtils.getBaseDate(mLecturer.getDateOfBirth());

        List<String> experiences = new ArrayList<>();
        for (Experience experience : mLecturer.getExperiences()) {
            experiences.add(experience.getSpecialty());
        }
        description = "Опыт рботы: " + StringUtils.stringTransfer(experiences) + "\n";

        description += "Образование: " + mLecturer.getEducation() + "\n";

        List<String> lectures = new ArrayList<>();
        for (Lecture lecture : mLecturer.getLectures()) {
            lectures.add(lecture.getTitle());
        }

        description += "Лекции: " + StringUtils.stringTransfer(lectures);

        mVh.tvName.setText(name);
        mVh.tvSurname.setText(surname);
        mVh.tvDescription.setText(description);
        mVh.tvDate.setText(age);
    }

    private Observable<RequestResult> populateLecturerObservable() {
        Observable<RequestResult> observable = Observable.defer(new Func0<Observable<RequestResult>>() {
            @Override
            public Observable<RequestResult> call() {
                RequestResult result = SyncAPI.getLecturer(mItemId);
                return Observable.just(result);
            }
        });
        return observable;
    }

    private void populateViewHolder() {
        View layout = LayoutInflater.from(this).inflate(R.layout.activity_lecturer_item, null, true);
        setContentView(layout);
        mVh = new ViewHolder(layout);
    }

    private void initData(Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args == null) {
            args = savedInstanceState;
        }
        if (args != null) {
            mItemId = args.getLong(EXTRA_TAG_ID, -1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong(EXTRA_TAG_ID, mItemId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mItemId = savedInstanceState.getLong(EXTRA_TAG_ID, -1);
    }

    public static void open(Context context, long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_TAG_ID, id);
        Intent intent = new Intent(context, LecturerItemActivity.class);
        context.startActivity(intent);
    }
}
