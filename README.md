# Payment Alarm V1.0
Free Android payment alarm app. No server, no subscription.

## Features
- Monthly alarms on comma-separated dates (for example 5,6,7)
- One-time alarms with date picker
- Payee, amount, notes, time
- Full-screen lock-screen alarm (subject to Android permission)
- Looping system alarm ringtone, STOP button
- Restore future alarms after reboot and time/timezone changes

## Build on GitHub Actions
1. Create a PUBLIC GitHub repository called `payment-alarm` (public Actions standard runners are generally free; repository source will be public).
2. Upload the CONTENTS of this ZIP to the repository ROOT. **Do not upload only the ZIP**. Hidden `.github/workflows/build.yml` must be present.
3. Go to Actions > Build Payment Alarm APK > Run workflow (or push to main).
4. Open completed green run > Artifacts > `PaymentAlarm-v1-debug-APK` > download ZIP.
5. Extract ZIP on Samsung; install `app-debug.apk`. Permit installation from your browser/file manager when prompted.

## Samsung setup
- Settings > Apps > Special access > Alarms & reminders > Payment Alarm: allow.
- Settings > Apps > Special access > Full screen alerts/intents > Payment Alarm: allow if available.
- Settings > Apps > Payment Alarm > Notifications: allow.
- Settings > Apps > Payment Alarm > Battery: Unrestricted.
- Run TEST IN 2 MINUTES, lock screen, confirm full-screen, continuous ringtone, and manual STOP.

## Limitations
- Samsung/Android can restrict background activity; do not use as sole reminder for critical payments until tested.
- App uses local SharedPreferences; uninstalling clears alarms. No backup or cloud sync.
- Dates 29-31 are skipped in months without that date.
- Editing while an alarm is ringing does not change that already-ringing notification.
- GitHub-built debug APK is signed with an ephemeral debug key; a future build may not install as an update. Back up alarm details before reinstalling.
- Do not publish or use this test app for safety-critical alarms without more testing.
