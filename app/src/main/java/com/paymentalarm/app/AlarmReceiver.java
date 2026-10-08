package com.paymentalarm.app;
import android.content.*;
public class AlarmReceiver extends BroadcastReceiver {
 @Override public void onReceive(Context c,Intent i){int id=i.getIntExtra("id",-1);if(id<0)return;Intent service=new Intent(c,RingService.class).putExtra("id",id);try{c.startForegroundService(service);}catch(Exception e){android.util.Log.e("PaymentAlarm","Cannot start ringing",e);}org.json.JSONObject x=AlarmStore.find(c,id);if(x!=null){if(x.optString("mode").equals("once")){try{x.put("enabled",false);AlarmStore.put(c,x);}catch(Exception ignored){}}else AlarmStore.schedule(c,x);}}
}
