package com.example.fyp_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_app.R.id;
import com.example.fyp_app.preference.SharedPref;
import com.github.florent37.viewanimator.AnimationListener.Start;
import com.github.florent37.viewanimator.AnimationListener.Stop;
import com.github.florent37.viewanimator.ViewAnimator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import kotlin.jvm.internal.Intrinsics;

/*@Metadata(
        mv = {1, 1, 18},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000e"},
        d2 = {"Lcom/example/android_breath/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "pref", "Lcom/example/android_breath/Util/SharedPref;", "getPref", "()Lcom/example/android_breath/Util/SharedPref;", "setPref", "(Lcom/example/android_breath/Util/SharedPref;)V", "animationstart", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)

 */
public final class BreathCompleted extends AppCompatActivity {
    public SharedPref pref;
    private HashMap _$_findViewCache;
    int time = 59;
    @NotNull
    public final SharedPref getPref() {
        SharedPref var10000 = this.pref;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pref");
        }

        return var10000;
    }

    public final void setPref(@NotNull SharedPref var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.pref = var1;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_breath);
        this.pref = new SharedPref((Activity)this);
        TextView var10000 = (TextView)this._$_findCachedViewById(id.breathtoday);
        //Intrinsics.checkExpressionValueIsNotNull(var10000, "breathtoday");
        Object[] var10002 = new Object[1];
        SharedPref var10005 = this.pref;
        if (var10005 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pref");
        }

        var10002[0] = var10005.getSessions();
       // var10000.setText((CharSequence)MessageFormat.format("{0} minutes today", var10002));
        //var10000 = (TextView)this._$_findCachedViewById(R.id.lasttimeused);
       // Intrinsics.checkExpressionValueIsNotNull(var10000, "lasttimeused");
        SharedPref var10001 = this.pref;
        if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pref");
        }

        //var10000.setText((CharSequence)var10001.getDate());
        ((Button)this._$_findCachedViewById(id.startbtn)).setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                BreathCompleted.this.animationstart();
                TextView text = (TextView)findViewById(id.breathtoday);
                new CountDownTimer(60000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        text.setText(Integer.toString(time));
                        time--;
                    }

                    @Override
                    public void onFinish() {
                        startActivity(new Intent((Context) BreathCompleted.this, YogaCompletedActivity.class));
                        finish();
                    }
                }.start();
            }
        }));
    }

    private final void animationstart() {
        ViewAnimator.animate(new View[]{(View)((ImageView)this._$_findCachedViewById(id.flower))}).onStart((Start)(new Start() {
            public final void onStart() {
                TextView var10000 = (TextView) BreathCompleted.this._$_findCachedViewById(id.breathtxt);
                Intrinsics.checkExpressionValueIsNotNull(var10000, "breathtxt");
                var10000.setText((CharSequence)"Inhale........ Exhale");
            }
        })).thenAnimate(new View[]{(View)((ImageView)this._$_findCachedViewById(id.flower))}).scale(new float[]{0, 1.5F, 0}).rotation(new float[]{360.0F}).repeatCount(10).accelerate().duration(5500).onStop((Stop)(new Stop() {
            public final void onStop() {
                TextView var10000 = (TextView) BreathCompleted.this._$_findCachedViewById(id.breathtxt);
                Intrinsics.checkExpressionValueIsNotNull(var10000, "breathtxt");
                var10000.setText((CharSequence)"Great");
                ImageView var3 = (ImageView) BreathCompleted.this._$_findCachedViewById(id.flower);
                Intrinsics.checkExpressionValueIsNotNull(var3, "flower");
                var3.setScaleX(0);
                var3 = (ImageView) BreathCompleted.this._$_findCachedViewById(id.flower);
                Intrinsics.checkExpressionValueIsNotNull(var3, "flower");
                var3.setScaleY(0);
                BreathCompleted.this.getPref().setSessions(BreathCompleted.this.getPref().getSessions() + 1);
                BreathCompleted.this.getPref().setDate(System.currentTimeMillis());
                Handler handler = new Handler();
                Runnable countDownTimer = (Runnable)(new Runnable() {
                    public final void run() {
                        BreathCompleted.this.startActivity(new Intent((Context) BreathCompleted.this, BreathCompleted.class));
                        BreathCompleted.this.finish();
                    }
                });
                //handler.postDelayed(countDownTimer, 2000L);
            }
        })).start();
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}
