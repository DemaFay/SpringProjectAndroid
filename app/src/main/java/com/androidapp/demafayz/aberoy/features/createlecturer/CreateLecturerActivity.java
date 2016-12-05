package com.androidapp.demafayz.aberoy.features.createlecturer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;
import com.androidapp.demafayz.aberoy.network.SyncAPI;
import com.androidapp.demafayz.aberoy.network.data.RequestResult;
import com.androidapp.demafayz.aberoy.network.entitys.Lecturer;
import com.androidapp.demafayz.aberoy.utils.AppUtils;
import com.androidapp.demafayz.aberoy.utils.DateUtils;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by DemaFayz on 04.12.2016.
 */

public class CreateLecturerActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = CreateLecturerActivity.class.getSimpleName();

    private Calendar mDateAmdTime = Calendar.getInstance();
    private Date mBirthDate;

    private ViewHolder mVh;

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

        public ViewHolder(View layout) {
            ButterKnife.bind(this, layout);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateViewHolder();
        showTestData();
    }

    private void showTestData() {
        if (AppUtils.DEBUG) {
            mVh.etName.setText("Test Name");
            mVh.etSurname.setText("Test Surname");
            mVh.rbFemale.setChecked(true);
            mBirthDate = DateUtils.getLongByIntDate(1990, 11, 22);
            mVh.tvBirthData.setText(DateUtils.getBaseUIDate(mBirthDate));
            mVh.etDescription.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        }
    }

    private void populateViewHolder() {
        View layout = LayoutInflater.from(this).inflate(R.layout.activity_lecturer_create, null, true);
        setContentView(layout);
        mVh = new ViewHolder(layout);
        mVh.tvBirthData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvBirthData:
                showDataPickerDialog();
                break;
        }
    }

    private void showDataPickerDialog() {
        new DatePickerDialog(this, this,
                mDateAmdTime.get(Calendar.YEAR),
                mDateAmdTime.get(Calendar.MONTH),
                mDateAmdTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mBirthDate = DateUtils.getLongByIntDate(year, month, dayOfMonth);
        if (mBirthDate != null) {
            showData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_lecturer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.miAccept:
            uploadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadData() {
        Observable<RequestResult> observable = populateUploadLecturerDataObservable();
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
                        AppUtils.dLog(CreateLecturerActivity.class, "create new  lecturer result: " + requestResult.getMessage());
                        if (requestResult.getCode() == HttpURLConnection.HTTP_OK) {
                            finish();
                        }
                    }
                });
        addSubscription(subscription);
    }

    private Observable<RequestResult> populateUploadLecturerDataObservable() {
        Observable<RequestResult> observable = Observable.defer(new Func0<Observable<RequestResult>>() {
            @Override
            public Observable<RequestResult> call() {
                Lecturer lecturer = populateLecturer();
                RequestResult result = SyncAPI.createNewLecturer(lecturer);
                return Observable.just(result);
            }
        });
        return observable;
    }

    private Lecturer populateLecturer() {
        Lecturer lecturer = new Lecturer();
        String name = mVh.etName.getText().toString();
        String surname = mVh.etSurname.getText().toString();
        String sex = null;
        if (mVh.rbFemale.isChecked()) {
            sex = "female";
        } else if (mVh.rbMale.isChecked()) {
            sex = "male";
        }
        String description = mVh.etDescription.getText().toString();
        lecturer.setFirstName(name);
        lecturer.setLastName(surname);
        lecturer.setSex(sex);
        lecturer.setDateOfBirth(mBirthDate);
        lecturer.setDescription(description);
        return lecturer;
    }

    private void showData() {
        mVh.tvBirthData.setText(DateUtils.getBaseUIDate(mBirthDate));
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, CreateLecturerActivity.class);
        context.startActivity(intent);
    }
}
