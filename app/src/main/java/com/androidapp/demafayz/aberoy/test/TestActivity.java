package com.androidapp.demafayz.aberoy.test;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.androidapp.demafayz.aberoy.R;
import com.androidapp.demafayz.aberoy.base.BaseActivity;
import com.androidapp.demafayz.aberoy.network.SyncAPI;
import com.androidapp.demafayz.aberoy.network.data.RequestResult;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        uploadData();
    }

    private void uploadData() {
        Observable<RequestResult> observable = populateTestObservable();
        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
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

                    }
                });
        addSubscription(subscription);
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
}
