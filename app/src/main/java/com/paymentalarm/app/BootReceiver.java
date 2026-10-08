package com.paymentalarm.app;
import android.content.*;
public class BootReceiver extends BroadcastReceiver { @Override public void onReceive(Context c,Intent i){AlarmStore.rescheduleAll(c);} }
