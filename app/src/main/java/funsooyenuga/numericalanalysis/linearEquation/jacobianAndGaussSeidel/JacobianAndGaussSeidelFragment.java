package funsooyenuga.numericalanalysis.linearEquation.jacobianAndGaussSeidel;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import funsooyenuga.numericalanalysis.R;
import funsooyenuga.numericalanalysis.data.LinearEquation;

import static funsooyenuga.numericalanalysis.util.Util.getDouble;
import static funsooyenuga.numericalanalysis.util.Util.getInt;
import static funsooyenuga.numericalanalysis.util.Util.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class JacobianAndGaussSeidelFragment extends Fragment {

    public static final String TAG = "jacobianAndGaussSeidelFragment";

    private EditText x0, x1, x2, x3;
    private EditText y0, y1, y2, y3;
    private EditText z0, z1, z2, z3;
    private EditText a, b, c;
    private EditText decimalPlaces;

    private Listener listener;


    public JacobianAndGaussSeidelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            try {
                listener = (Listener) activity;
            } catch (ClassCastException ex) {
                throw new ClassCastException(activity.toString() +
                        " must implement JacobianAndGaussSeidelFragment.Listener");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_jacobian_gauss_seidel, container, false);

        x0 = (EditText) v.findViewById(R.id.x0);
        x1 = (EditText) v.findViewById(R.id.x1);
        x2 = (EditText) v.findViewById(R.id.x2);
        x3 = (EditText) v.findViewById(R.id.x3);

        y0 = (EditText) v.findViewById(R.id.y0);
        y1 = (EditText) v.findViewById(R.id.y1);
        y2 = (EditText) v.findViewById(R.id.y2);
        y3 = (EditText) v.findViewById(R.id.y3);

        z0 = (EditText) v.findViewById(R.id.z0);
        z1 = (EditText) v.findViewById(R.id.z1);
        z2 = (EditText) v.findViewById(R.id.z2);
        z3 = (EditText) v.findViewById(R.id.z3);

        a = (EditText) v.findViewById(R.id.a);
        b = (EditText) v.findViewById(R.id.b);
        c = (EditText) v.findViewById(R.id.c);

        decimalPlaces = (EditText) v.findViewById(R.id.decimal_places);

        FloatingActionButton calculate = (FloatingActionButton) getActivity().findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!coefficientsEmpty()) {
                    LinearEquation equation = getEquationCoefficients();
                    listener.onCalculateClick(equation);
                } else {
                    Snackbar.make(getView(), "All fields are required", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    /**
     * Checks if any of the coefficients is empty as all fields are required.
     * @return
     */
    private boolean coefficientsEmpty() {
        return (isEmpty(x0) || isEmpty(x1) || isEmpty(x2) || isEmpty(x3)
                || isEmpty(y0) || isEmpty(y1) || isEmpty(y2) || isEmpty(y3)
                || isEmpty(z0) || isEmpty(z1) || isEmpty(z2) || isEmpty(z3)
                || isEmpty(a)  || isEmpty(b)  || isEmpty(c)
                || isEmpty(decimalPlaces));
    }

    /**
     * Gets all the coefficients from all EditText widgets and adds it to a LinearEquation object.
     * @return a equation object with variables set
     */
    private LinearEquation getEquationCoefficients() {
        LinearEquation equation = new LinearEquation();

        equation.setX0(getDouble(x0));
        equation.setX1(getDouble(x1));
        equation.setX2(getDouble(x2));
        equation.setX3(getDouble(x3));

        equation.setY0(getDouble(y0));
        equation.setY1(getDouble(y1));
        equation.setY2(getDouble(y2));
        equation.setY3(getDouble(y3));

        equation.setZ0(getDouble(z0));
        equation.setZ1(getDouble(z1));
        equation.setZ2(getDouble(z2));
        equation.setZ3(getDouble(z3));

        equation.setA(getDouble(a));
        equation.setB(getDouble(b));
        equation.setC(getDouble(c));

        equation.setDecimalPlaces(getInt(decimalPlaces));

        return equation;
    }

    public interface Listener {

        void onCalculateClick(LinearEquation equation);
    }

}
