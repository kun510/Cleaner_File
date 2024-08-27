@file:Suppress("DEPRECATION")

package com.kun510.cleaner.data.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.kun510.cleaner.data.datastore.DataStore
import com.kun510.cleaner.notifications.managers.AppUsageNotificationsManager

@Suppress("SameParameterValue")
class AppCoreManager : MultiDexApplication(), Application.ActivityLifecycleCallbacks,
    LifecycleObserver {
    private lateinit var dataStore: DataStore
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        dataStore = DataStore.getInstance(this@AppCoreManager)
        val notificationsManager = AppUsageNotificationsManager(this)
        notificationsManager.scheduleAppUsageCheck()
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

}