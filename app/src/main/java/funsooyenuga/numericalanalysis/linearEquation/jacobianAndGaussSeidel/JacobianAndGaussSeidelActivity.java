package funsooyenuga.numericalanalysis.linearEquation.jacobianAndGaussSeidel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import funsooyenuga.numericalanalysis.R;
import funsooyenuga.numericalanalysis.data.LinearEquation;
import funsooyenuga.numericalanalysis.util.Util;

public class JacobianAndGaussSeidelActivity extends AppCompatActivity
        implements JacobianAndGaussSeidelFragment.Listener {

    private static final String EXTRA_METHOD = "extra_method";

    private String method = null;

    public static Intent newIntent(Context context, String method) {
        Intent intent = new Intent(context, JacobianAndGaussSeidelActivity.class);
        intent.putExtra(EXTRA_METHOD, method);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jacobian_gauss_siedel);

        method = getIntent().getStringExtra(EXTRA_METHOD);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (method.equals(LinearEquation.JACOBIAN_METHOD)) {
            actionBar.setTitle(getResources().getString(R.string.jacobian_method));
        } else if (method.equals(LinearEquation.GAUSS_SEIDEL_METHOD)) {
            actionBar.setTitle(getResources().getString(R.string.gauss_seidel));
        }

        if (savedInstanceState == null) {
            Util.hostFragment(getSupportFragmentManager(), R.id.content_frame, new JacobianAndGaussSeidelFragment());
        }
    }

    @Override
    public void onCalculateClick(LinearEquation equation) {
        Util.hostFragment(getSupportFragmentManager(), R.id.content_frame,
                CalculateFragment.newInstance(equation, method));
    }
}

