package com.d.xdyplusn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static final String RESULT_DETAILS_TEXT = "DETAILS_TEXT";
    static final String RESULT_NUMBER = "ROLL_RESULT";
    static final String INPUT_VISIBILITY = "LINEAR_LAYOUT_VISIBILITY";
    static final String RESULT_DISPLAY_VISIBILITY = "RELATIVE_LAYOUT_VISIBILITY";
    TextView rollDetailsTW;
    TextView rollResultTW;
    RollHelper diceRoller = new RollHelper();
    int xValue = 0;
    int yValue = 0;
    int nValue = 0;
    LinearLayout inputsLinearLayout;
    RelativeLayout resultRelativeLayout;
    Button rollButton;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollDetailsTW = findViewById(R.id.tw_rolldetails);
        inputsLinearLayout = findViewById(R.id.ll_intputs);
        rollButton = findViewById(R.id.roll_button);
        rollResultTW = findViewById(R.id.tw_result);
        resultRelativeLayout = findViewById(R.id.rl_result);
        mContext = getBaseContext();
        initSpinners();
        if (savedInstanceState != null) {
            inputsLinearLayout.setVisibility(savedInstanceState.getInt(INPUT_VISIBILITY));
            resultRelativeLayout.setVisibility(savedInstanceState.getInt(RESULT_DISPLAY_VISIBILITY));
            rollDetailsTW.setText(savedInstanceState.getString(RESULT_DETAILS_TEXT));
            rollResultTW.setText(savedInstanceState.getString(RESULT_NUMBER));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(RESULT_DETAILS_TEXT, rollDetailsTW.getText().toString());
        outState.putString(RESULT_NUMBER, rollResultTW.getText().toString());
        outState.putInt(INPUT_VISIBILITY, inputsLinearLayout.getVisibility());
        outState.putInt(RESULT_DISPLAY_VISIBILITY, resultRelativeLayout.getVisibility());
        super.onSaveInstanceState(outState);
    }

    public void initSpinners() {
        inputsLinearLayout.setVisibility(View.VISIBLE);
        resultRelativeLayout.setVisibility(View.GONE);

        Spinner xSpinner = findViewById(R.id.x_spinner);
        Spinner ySpinner = findViewById(R.id.y_spinner);
        EditText nEditText = findViewById(R.id.n_edit_text);

        ArrayAdapter<CharSequence> xAdapter = ArrayAdapter.createFromResource(this, R.array.number_of_rolls, R.layout.spinner_item);
        xSpinner.setAdapter(xAdapter);
        xSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this, R.array.number_of_sides, R.layout.spinner_item);
        ySpinner.setAdapter(yAdapter);
        ySpinner.setOnItemSelectedListener(this);

        nEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0) {
                    try {
                        nValue = Integer.parseInt(charSequence.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void rollTheDice(View view) {
        int rollResult = diceRoller.xDyPlusN(xValue, yValue, nValue);
        Log.e("rollresult", " " + rollResult);
        displayResult(rollResult);
    }


    public void displayResult(int rollResult) {

        inputsLinearLayout.setVisibility(View.GONE);
        resultRelativeLayout.setVisibility(View.VISIBLE);
        String textToDisplay = String.format(
                xValue + " D " +
                        yValue + " + " +
                        nValue);
        String resultDisplay = " " + rollResult;
        rollDetailsTW.setText(textToDisplay);
        rollResultTW.setText(resultDisplay);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int id = adapterView.getId();

        if (id == R.id.x_spinner) {
            try {
                xValue = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (id == R.id.y_spinner) {
            try {
                yValue = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        xValue = 0;
        yValue = 0;
    }

    public void returnToSelection(View view) {
        inputsLinearLayout.setVisibility(View.VISIBLE);
        resultRelativeLayout.setVisibility(View.GONE);
    }
}
