package funsooyenuga.numericalanalysis.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import funsooyenuga.numericalanalysis.R;
import funsooyenuga.numericalanalysis.data.LinearEquation;
import funsooyenuga.numericalanalysis.linearEquation.jacobianAndGaussSeidel.JacobianAndGaussSeidelActivity;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button jacobian;
    private Button gaussSiedel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        jacobian = (Button) v.findViewById(R.id.jacobian);
        jacobian.setOnClickListener(this);

        gaussSiedel = (Button) v.findViewById(R.id.gauss_siedel);
        gaussSiedel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        String method = null;

        switch(buttonId) {
            case R.id.jacobian:
                method = LinearEquation.JACOBIAN_METHOD;
                break;
            case R.id.gauss_siedel:
                method = LinearEquation.GAUSS_SEIDEL_METHOD;
                break;
        }

        Intent intent = JacobianAndGaussSeidelActivity.newIntent(getActivity(), method);
        startActivity(intent);
    }
}
