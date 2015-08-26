package com.richardradics.core.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.richardradics.core.R;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
public class ErrorActivity extends AppCompatActivity {

    public final static String STACKTRACE_ARG = "strack-arg";
    public final static String EXCEPTION_TYPE_ARG = "extype-arg";
    public final static String CAUSE_ARG = "cause-arg";
    public final static String DEVICE_INFO_ARG = "devinf-arg";

    TextView errorStactTraceTextView;
    Button errorConfirmButton;
    TextView errorDeviceInfoTextView;
    TextView errorCauseTextView;
    TextView errorExceptionTypeTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_error);

        errorStactTraceTextView = (TextView) findViewById(R.id.errorStactTraceTextView);
        errorConfirmButton = (Button) findViewById(R.id.errorConfirmButton);
        errorDeviceInfoTextView = (TextView) findViewById(R.id.errorDeviceInfoTextView);
        errorCauseTextView = (TextView) findViewById(R.id.errorCauseTextView);
        errorExceptionTypeTextView = (TextView) findViewById(R.id.errorExceptionTypeTextView);

        onAfterViewFInish();
    }


    private void onAfterViewFInish() {
        if (getIntent().hasExtra(CAUSE_ARG)) {
            errorCauseTextView.setText(getIntent().getStringExtra(CAUSE_ARG));
        }

        if (getIntent().hasExtra(DEVICE_INFO_ARG)) {
            errorDeviceInfoTextView.setText(getIntent().getStringExtra(DEVICE_INFO_ARG));
        }

        if (getIntent().hasExtra(EXCEPTION_TYPE_ARG)) {
            errorExceptionTypeTextView.setText(getIntent().getStringExtra(EXCEPTION_TYPE_ARG));
        }

        if (getIntent().hasExtra(STACKTRACE_ARG)) {
            errorStactTraceTextView.setText(getIntent().getStringExtra(STACKTRACE_ARG));
        }

        errorConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_error);
    }
}
