package com.androidapp.demafayz.aberoy.features.editlecturer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;
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
 * Created by DemaFayz on 09.12.2016.
 */

public class EditLecturerActivity extends BaseActivity {

    private static final String TAG = EditLecturerActivity.class.getSimpleName();
    private static final String ITEM_ID_EXTRA_TAG = TAG + "_item_id_extra_tag";

    private long mItemID = -1;

    private ViewHolder mVh;
    private Lecturer mLecturer;

    class ViewHolder {

        @BindView(R.id.ivUserPhoto)
        ImageView ivUserPhoto;

        @BindView(R.id.etName)
        TextView etName;

        @BindView(R.id.etSurname)
        TextView etSurname;

        @BindView(R.id.rbMale)
        AppCompatRadioButton rbMale;

        @BindView(R.id.rbFemale)
        AppCompatRadioButton rbFemale;

        @BindView(R.id.tvBirthData)
        TextView tvBirthData;

        @BindView(R.id.etDescription)
        EditText etDescription;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
        populateViewHolder();
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
        mVh.etName.setText(mLecturer.getFirstName());
        mVh.etSurname.setText(mLecturer.getLastName());
        mVh.etDescription.setText(mLecturer.getDescription());
    }

    private Observable<RequestResult> populateLecturerObservable() {
        Observable<RequestResult> observable = Observable.defer(new Func0<Observable<RequestResult>>() {
            @Override
            public Observable<RequestResult> call() {
                RequestResult result = SyncAPI.getLecturer(mItemID);
                return Observable.just(result);
            }
        });
        return observable;
    }

    private void populateViewHolder() {
        View layout = LayoutInflater.from(this).inflate(R.layout.activity_lecturer_create, null, true);
        setContentView(layout);
        mVh = new ViewHolder(layout);
    }

    private void initData(Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args == null) args = savedInstanceState;
        if (args != null) {
            mItemID = args.getLong(ITEM_ID_EXTRA_TAG, -1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong(ITEM_ID_EXTRA_TAG, mItemID);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_lecturer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.miAccept:
                saveItem();
                return true;
        }
        return false;
    }

    private void saveItem() {
        Observable<Lecturer> observable = populateUpdateLecturerObservable();
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Lecturer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Lecturer lecturer) {
                        if (lecturer == null) {
                            //TODO show error dialog
                        } else {
                            finish();
                        }
                    }
                });
        addSubscription(subscription);
    }

    private Observable<Lecturer> populateUpdateLecturerObservable() {
        Observable<Lecturer> observable = Observable.defer(new Func0<Observable<Lecturer>>() {
            @Override
            public Observable<Lecturer> call() {
                Lecturer lecturer = populateLecturer();
                RequestResult result = SyncAPI.createNewLecturer(lecturer);
                if (result.getCode() == HttpURLConnection.HTTP_OK) {
                    lecturer = new Gson().fromJson(result.getMessage(), Lecturer.class);
                    return Observable.just(lecturer);
                }
                return null;
            }
        });
        return observable;
    }

    private Lecturer populateLecturer() {
        long id = mItemID;
        String name = mVh.etName.getText().toString();
        String surname = mVh.etSurname.getText().toString();
        String description = mVh.etDescription.getText().toString();

        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);
        lecturer.setFirstName(name);
        lecturer.setLastName(surname);
        lecturer.setDescription(description);
        return lecturer;
    }

    public static void open(Activity activity, long itemID) {
        Intent intent = new Intent(activity, EditLecturerActivity.class);
        Bundle args = new Bundle();
        args.putLong(ITEM_ID_EXTRA_TAG, itemID);
        intent.putExtras(args);
        activity.startActivity(intent);
    }
}
