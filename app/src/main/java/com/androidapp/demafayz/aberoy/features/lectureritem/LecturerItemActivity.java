package com.androidapp.demafayz.aberoy.features.lectureritem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;
import com.androidapp.demafayz.aberoy.features.editlecturer.EditLecturerActivity;
import com.androidapp.demafayz.aberoy.network.SyncAPI;
import com.androidapp.demafayz.aberoy.network.data.RequestResult;
import com.androidapp.demafayz.aberoy.network.entitys.Lecturer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;

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

    @Override
    protected void onRestart() {
        super.onRestart();
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
        String description;

        name = mLecturer.getFirstName();
        surname = mLecturer.getLastName();
        description = mLecturer.getDescription();

        mVh.tvName.setText(name);
        mVh.tvSurname.setText(surname);
        mVh.tvDescription.setText(description);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_lecturer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case R.id.miRemove:
                removeLecturer();
                return true;
            case R.id.miEdit:
                editLecturer();
                return true;
        }
        return false;
    }

    private void editLecturer() {
        EditLecturerActivity.open(this, mItemId);
    }

    private void removeLecturer() {
        Observable<RequestResult> observable = populateRemoveLecturerObservable();
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RequestResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        if (requestResult.getCode() == HttpURLConnection.HTTP_OK) {
                            finish();
                        }
                    }
                });
        addSubscription(subscription);
    }

    private Observable<RequestResult> populateRemoveLecturerObservable() {
        Observable<RequestResult> observable = Observable.defer(new Func0<Observable<RequestResult>>() {
            @Override
            public Observable<RequestResult> call() {
                RequestResult result = SyncAPI.removeLecturer(mItemId);
                return Observable.just(result);
            }
        });
        return observable;
    }

    public static void open(Context context, long id) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_TAG_ID, id);
        Intent intent = new Intent(context, LecturerItemActivity.class);
        intent.putExtras(args);
        context.startActivity(intent);
    }
}
