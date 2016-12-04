package com.androidapp.demafayz.aberoy.base;

import android.database.Observable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    List<Subscription> subscriptions;

    protected void addSubscription(Subscription subscription) {
        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        subscriptions.add(subscription);
    }

    @Override
    protected void onDestroy() {
        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                subscription.unsubscribe();
            }
        }
        super.onDestroy();
    }
}
