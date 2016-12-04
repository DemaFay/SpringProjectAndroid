package com.androidapp.demafayz.aberoy.features.lecturerlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;
import com.androidapp.demafayz.aberoy.features.createlecturer.CreateLecturerActivity;
import com.androidapp.demafayz.aberoy.features.lectureritem.LecturerItemActivity;
import com.androidapp.demafayz.aberoy.features.lecturerlist.adapters.LecturersAdapter;
import com.androidapp.demafayz.aberoy.interfaces.OnRecyclerClickListener;
import com.androidapp.demafayz.aberoy.network.SyncAPI;
import com.androidapp.demafayz.aberoy.network.data.RequestResult;
import com.androidapp.demafayz.aberoy.network.entitys.Lecturer;
import com.androidapp.demafayz.aberoy.utils.AppUtils;
import com.androidapp.demafayz.aberoy.utils.Dummy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
 * Created by DemaFayz on 27.11.2016.
 */

public class LecturerListActivity extends BaseActivity implements OnRecyclerClickListener, View.OnClickListener {

    private List<Lecturer> mLecturers;
    private LecturersAdapter adapter;
    private ViewHolder vh;

    class ViewHolder {

        @BindView(R.id.btnAddLecturer)
        public Button btnAddLecturer;

        @BindView(R.id.rvLecturers)
        public RecyclerView rvLecturers;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateViewHolder();
        loadLecturers();
        //testAdapter();
    }

    private void testAdapter() {
        mLecturers = new ArrayList<>();
        mLecturers.add(Dummy.getLecturer());
        mLecturers.add(Dummy.getLecturer());
        createNewAdapter(mLecturers);
    }

    private void loadLecturers() {
        Observable<RequestResult> observable = populateTestObservable();
        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RequestResult>() {
                    @Override
                    public void onCompleted() {
                        if (mLecturers != null) {
                            createNewAdapter(mLecturers);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        AppUtils.eLog(LecturerListActivity.class, "lecturer request result: " + e.getMessage());
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        if (requestResult != null || requestResult.getMessage() != null) {
                            Type listType = new TypeToken<ArrayList<Lecturer>>(){}.getType();
                            mLecturers = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(requestResult.getMessage(), listType);
                            AppUtils.dLog(LecturerListActivity.class, "lecturer request result: " + requestResult.getMessage());
                        }
                    }
                });
        addSubscription(subscription);
    }

    private void createNewAdapter(List<Lecturer> lecturers) {
        adapter = new LecturersAdapter(lecturers);
        adapter.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        vh.rvLecturers.setLayoutManager(manager);
        vh.rvLecturers.setAdapter(adapter);
    }

    @Override
    public void onRecyclerClickListener(View itemView) {
        int position = vh.rvLecturers.getChildAdapterPosition(itemView);
        Lecturer lecturer = adapter.getItemByPosition(position);
        showLecturer(lecturer.getId());
    }

    private void showLecturer(Long id) {
        LecturerItemActivity.open(this, id);
    }

    private Observable<RequestResult> populateTestObservable() {
        Observable<RequestResult> observable = Observable.defer(new Func0<Observable<RequestResult>>() {
            @Override
            public Observable<RequestResult> call() {
                RequestResult result = SyncAPI.getAllLecturers();
                return Observable.just(result);
            }
        });
        return observable;
    }

    private void populateViewHolder() {
        LayoutInflater inflater = LayoutInflater.from(this);

        View layout = inflater.inflate(R.layout.activity_lecturer_list, null, true);
        vh = new ViewHolder(layout);
        setContentView(layout);
        vh.btnAddLecturer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnAddLecturer:
                showCreateLecturer();
                break;
        }
    }

    private void showCreateLecturer() {
        CreateLecturerActivity.open(this);
    }
}
