package in.blackpaper.beNitinhere;


import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import in.blackpaper.animatorprogress.AnimatorView;

public class MainActivity extends AppCompatActivity {
    AnimatorView animatorView;
    AppCompatButton button;
    TextView textView;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatorView = (AnimatorView) findViewById(R.id.animator_view);
        button = (AppCompatButton) findViewById(R.id.button);

    }

    @Override
    protected void onResume() {
        super.onResume();
        animatorView.setOnClickListener(v -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (animatorView.getProgressReporter().getMax() != animatorView.getProgressReporter().getProgress()) {
                                animatorView.setClickable(false);
                                animatorView.getProgressReporter().incrementProgressBy(1);
                            }
                        }
                    });
                }
            }, 1000, 100);
        });

        button.setOnClickListener(v -> {
            animatorView.getProgressReporter().setProgress(0);
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}


