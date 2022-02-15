package com.example.fyp_app.preference;

import android.app.Activity;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

//import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*@Metadata(
        mv = {1, 1, 18},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"},
        d2 = {"Lcom/example/android_breath/Util/SharedPref;", "", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "date", "", "getDate", "()Ljava/lang/String;", "preferences", "Landroid/content/SharedPreferences;", "session", "", "sessions", "getSessions", "()I", "setSessions", "(I)V", "setDate", "", "milliseconds", "", "app_debug"}
)

 */
public final class SharedPref {
    private final SharedPreferences preferences;

    @NotNull
    public final String getDate() {
        long milliDate = this.preferences.getLong("seconds", 0L);
        Calendar calendar = Calendar.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(calendar, "calendar");
        calendar.setTimeInMillis(milliDate);
        return "Last Used at " + calendar.get(11) + ": " + calendar.get(12) + "  (24 hours Format)";
    }

    public final void setDate(long milliseconds) {
        this.preferences.edit().putLong("seconds", milliseconds).apply();
    }

    public final int getSessions() {
        return this.preferences.getInt("sessions", 0);
    }

    public final void setSessions(int session) {
        this.preferences.edit().putInt("sessions", session).apply();
    }

    public SharedPref(@NotNull Activity activity) {
        super();
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        SharedPreferences var10001 = activity.getPreferences(0);
        Intrinsics.checkExpressionValueIsNotNull(var10001, "activity.getPreferences(Activity.MODE_PRIVATE)");
        this.preferences = var10001;
    }
}
