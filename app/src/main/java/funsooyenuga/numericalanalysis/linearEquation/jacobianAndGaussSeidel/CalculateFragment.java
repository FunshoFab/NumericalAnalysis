package funsooyenuga.numericalanalysis.linearEquation.jacobianAndGaussSeidel;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import funsooyenuga.numericalanalysis.R;
import funsooyenuga.numericalanalysis.data.LinearEquation;

public class CalculateFragment extends Fragment {

    private static final String ARG_EQUATION = "arg_equation";

    private static final String ARG_METHOD = "arg_method";

    private String method;

    private LinearEquation equation;

    private RecyclerView resultList;

    private ResultAdapter resultAdapter;

    private List<Result> results;

    private DecimalFormat decimal;

    private int n = 0; //number of iterations
    private final int ITERATION_LIMIT = 30; //A correct equation should yield an answer before this limit is reached
    private int decimalPlaces;

    private double xn, x1, x2, x3;
    private double yn, y1, y2, y3;
    private double zn, z1, z2, z3;
    private double a, b, c;

    private double x = -9999, y = -9999, z = -9999;

    private boolean isNew = true;

    private TextView maxIteration;

    public static CalculateFragment newInstance(@NonNull LinearEquation equation,
                                                @NonNull String method) {
        CalculateFragment fragment = new CalculateFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_EQUATION, equation);
        args.putString(ARG_METHOD, method);
        fragment.setArguments(args);

        return fragment;
    }

    public CalculateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        equation = (LinearEquation) getArguments().getSerializable(ARG_EQUATION);
        method = getArguments().getString(ARG_METHOD);

        results = new ArrayList<>();

        getVariables();

        setAccuracy();
    }

    /**
     * set the accuracy according to the number of decimal places specified.
     */
    private void setAccuracy() {
        String format;
        if (decimalPlaces > 0) {
            format = "#.";
            for (int i = 0; i < decimalPlaces; i++) {
                format += "#";
            }
        } else {
            format = "#";
        }
        decimal = new DecimalFormat(format);
    }

    /**
     * Gets all the needed variables from the equation object
     */
    private void getVariables() {
        //For the first iteration, n = 0
        xn = equation.getX0();
        yn = equation.getY0();
        zn = equation.getZ0();

        //Coefficients of x, y, z in the 3 equations
        x1 = equation.getX1();
        x2 = equation.getX2();
        x3 = equation.getX3();

        y1 = equation.getY1();
        y2 = equation.getY2();
        y3 = equation.getY3();

        z1 = equation.getZ1();
        z2 = equation.getZ2();
        z3 = equation.getZ3();

        a = equation.getA();
        b = equation.getB();
        c = equation.getC();

        decimalPlaces = equation.getDecimalPlaces();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calculate, container, false);

        maxIteration = (TextView) v.findViewById(R.id.max_iteration_reached);
        maxIteration.setVisibility(View.GONE);

        resultList = (RecyclerView) v.findViewById(R.id.rv_result_list);
        resultAdapter = new ResultAdapter(new ArrayList<Result>(0));
        resultList.setAdapter(resultAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        resultList.setLayoutManager(manager);

        //add a divider to the RecyclerView
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), manager.getOrientation());
        resultList.addItemDecoration(decoration);

        FloatingActionButton calculate = (FloatingActionButton) getActivity().findViewById(R.id.calculate);
        calculate.setVisibility(View.INVISIBLE);
        //isNew is false when there is already an instance of this Fragment, hence we refresh the
        //adapter with the results.
        if (isNew) {
            calculate();
            isNew = false;
        } else {
            resultAdapter.replaceResults(results);
        }

        return v;
    }

    private void calculate() {
        if (method.equals(LinearEquation.JACOBIAN_METHOD)) {
            jacobian();
        } else if (method.equals(LinearEquation.GAUSS_SEIDEL_METHOD)) {
            gaussSeidel();
        }
        //make RecyclerView scroll to bottom so as to display new results
        resultList.scrollToPosition(results.size() - 1);
    }

    private void gaussSeidel() {
        /*  keep iterating until the answers are the same or the number of iterations exceeds
            the ITERATION_LIMIT (which shows the equation might be incorrect) */
        while (!answerIsTheSame() && n < ITERATION_LIMIT) {
            //save the previous values of xn, yn and zn before the next iteration
            x = xn;
            y = yn;
            z = zn;

            xn = (a - y1 * yn - z1 * zn) / x1;
            yn = (b - x2 * xn - z2 * zn) / y2;
            zn = (c - x3 * xn - y3 * yn) / z3;

            displayResult(n, xn, yn, zn);
            n++;
        }
        if (n == ITERATION_LIMIT) {
            showMaxIterationReached();
        }

    }

    private void jacobian() {
        while (!answerIsTheSame() && n < ITERATION_LIMIT) {
            x = xn;
            y = yn;
            z = zn;

            xn = (a - y1 * y - z1 * z) / x1;
            yn = (b - x2 * x - z2 * z) / y2;
            zn = (c - x3 * x - y3 * y) / z3;

            displayResult(n, xn, yn, zn);
            n++;
        }

        if (n == ITERATION_LIMIT) {
            showMaxIterationReached();
        }
    }

    private boolean answerIsTheSame() {
        return decimal.format(x).equals(decimal.format(xn))
                && decimal.format(y).equals(decimal.format(yn))
                && decimal.format(z).equals(decimal.format(zn));
    }

    private void displayResult(int n, double xn, double yn, double zn) {
        Result result = new Result(n, xn, yn, zn);
        results.add(result);

        resultAdapter.replaceResults(results);
    }

    private void showMaxIterationReached() {
        maxIteration.setVisibility(View.VISIBLE);
    }

    private class Result {
        private double xn, yn, zn;
        private int n;

        Result(int n, double xn, double yn, double zn) {
            this.xn = xn;
            this.yn = yn;
            this.zn = zn;
            this.n = n;
        }

        double getXn() {
            return xn;
        }

        double getYn() {
            return yn;
        }

        double getZn() {
            return zn;
        }

        int getN() {
            return n;
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {

        private List<Result> results;

        ResultAdapter(List<Result> results) {
            this.results = results;
        }

        @Override
        public int getItemCount() {
            return results.size();
        }

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.result_row, parent, false);
            return new ResultHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            Result result = results.get(position);
            int n = result.getN();

            holder.xn.setText("X" + n + " = " + decimal.format(result.getXn()));
            holder.yn.setText("Y" + n + " = " + decimal.format(result.getYn()));
            holder.zn.setText("Z" + n + " = " + decimal.format(result.getZn()));
        }

        void replaceResults(List<Result> results) {
            this.results = results;

            notifyDataSetChanged();
        }

        class ResultHolder extends RecyclerView.ViewHolder {

            TextView xn, yn, zn;

            ResultHolder(View itemView) {
                super(itemView);

                xn = (TextView) itemView.findViewById(R.id.result_xn);
                yn = (TextView) itemView.findViewById(R.id.result_yn);
                zn = (TextView) itemView.findViewById(R.id.result_zn);
            }
        }
    }
}
