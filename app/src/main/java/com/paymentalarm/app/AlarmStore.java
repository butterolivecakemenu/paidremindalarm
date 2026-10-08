package com.paymentalarm.app;

import android.app.*;
import android.content.*;
import android.os.Build;
import android.provider.Settings;
import org.json.*;
import java.util.*;
import java.text.*;

public class AlarmStore {
 static JSONArray all(Context c){try{return new JSONArray(c.getSharedPreferences("alarms",0).getString("items","[]"));}catch(Exception e){return new JSONArray();}}
 static void save(Context c,JSONArray a){c.getSharedPreferences("alarms",0).edit().putString("items",a.toString()).apply();}
 static JSONObject find(Context c,int id){JSONArray a=all(c);for(int i=0;i<a.length();i++){JSONObject x=a.optJSONObject(i);if(x!=null&&x.optInt("id")==id)return x;}return null;}
 static void put(Context c,JSONObject x){JSONArray a=all(c),b=new JSONArray();for(int i=0;i<a.length();i++){JSONObject q=a.optJSONObject(i);if(q!=null&&q.optInt("id")!=x.optInt("id"))b.put(q);}b.put(x);save(c,b);schedule(c,x);}
 static void remove(Context c,int id){JSONArray a=all(c),b=new JSONArray();for(int i=0;i<a.length();i++){JSONObject q=a.optJSONObject(i);if(q!=null&&q.optInt("id")!=id)b.put(q);}save(c,b);AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);am.cancel(intent(c,id));}
 static PendingIntent intent(Context c,int id){Intent i=new Intent(c,AlarmReceiver.class).putExtra("id",id);return PendingIntent.getBroadcast(c,id,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);}
 static long next(JSONObject x){Calendar now=Calendar.getInstance(),best=null;int h=x.optInt("hour"),m=x.optInt("minute");String mode=x.optString("mode","monthly");if(mode.equals("once")){try{Date d=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.US).parse(x.optString("date")+String.format(Locale.US," %02d:%02d",h,m));return d!=null&&d.getTime()>System.currentTimeMillis()?d.getTime():-1;}catch(Exception e){return -1;}}
 String[] days=x.optString("days","1").split(",");for(int offset=0;offset<25;offset++){Calendar c=(Calendar)now.clone();c.set(Calendar.DAY_OF_MONTH,1);c.add(Calendar.MONTH,offset);for(String s:days){try{int day=Integer.parseInt(s.trim());if(day<1||day>c.getActualMaximum(Calendar.DAY_OF_MONTH))continue;Calendar t=(Calendar)c.clone();t.set(Calendar.DAY_OF_MONTH,day);t.set(Calendar.HOUR_OF_DAY,h);t.set(Calendar.MINUTE,m);t.set(Calendar.SECOND,0);t.set(Calendar.MILLISECOND,0);if(t.after(now)&&(best==null||t.before(best)))best=t;}catch(Exception ignored){}}}return best==null?-1:best.getTimeInMillis();}
 static void schedule(Context c,JSONObject x){int id=x.optInt("id");AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);PendingIntent pi=intent(c,id);am.cancel(pi);if(!x.optBoolean("enabled",true))return;long when=next(x);if(when<0)return;if(Build.VERSION.SDK_INT>=31&&!am.canScheduleExactAlarms())return;am.setAlarmClock(new AlarmManager.AlarmClockInfo(when,PendingIntent.getActivity(c,id,new Intent(c,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE)),pi);}
 static void rescheduleAll(Context c){JSONArray a=all(c);for(int i=0;i<a.length();i++){JSONObject x=a.optJSONObject(i);if(x!=null)schedule(c,x);}}
}
