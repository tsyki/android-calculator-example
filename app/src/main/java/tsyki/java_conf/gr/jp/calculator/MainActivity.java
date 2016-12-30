package tsyki.java_conf.gr.jp.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private static final int REQUEST_CODE_ANOTHER_CALC_1 = 1;

    private static final int REQUEST_CODE_ANOTHER_CALC_2 = 2;

    private EditText numberInput1;
    private EditText numberInput2;

    private Spinner operatorSelector;
    private TextView calcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput1 = (EditText) findViewById(R.id.numberInput1);
        numberInput1.addTextChangedListener(this);
        numberInput2 = (EditText) findViewById(R.id.numberInput2);
        numberInput2.addTextChangedListener(this);

        operatorSelector = (Spinner) findViewById(R.id.operatorSelector);
        operatorSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refreshResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        calcResult = (TextView) findViewById(R.id.calcResult);

        findViewById(R.id.calcButton1).setOnClickListener(this);
        findViewById(R.id.calcButton2).setOnClickListener(this);
        findViewById(R.id.nextButton).setOnClickListener(this);
    }

    private boolean isInputted() {
        return !TextUtils.isEmpty(numberInput1.getText().toString()) && !TextUtils.isEmpty(numberInput2.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        refreshResult();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calcButton1:
                Intent intent1 = new Intent(this, AnotherCalcActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_ANOTHER_CALC_1);
                break;
            case R.id.calcButton2:
                Intent intent2 = new Intent(this, AnotherCalcActivity.class);
                startActivityForResult(intent2, REQUEST_CODE_ANOTHER_CALC_2);
                break;
            case R.id.nextButton:
                if(isInputted()){
                    int result = calc();
                    numberInput1.setText(String.valueOf(result));
                    refreshResult();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        Bundle resultBundle = data.getExtras();
        if (!resultBundle.containsKey("result")) {
            return;
        }
        int result = resultBundle.getInt("result");

        if (requestCode == REQUEST_CODE_ANOTHER_CALC_1) {
            numberInput1.setText(String.valueOf(result));
        } else if (requestCode == REQUEST_CODE_ANOTHER_CALC_2) {
            numberInput2.setText(String.valueOf(result));
        } else {
            throw new IllegalStateException(String.valueOf(resultCode));
        }
        refreshResult();
    }

    private void refreshResult() {
        if (isInputted()) {
            int result = calc();
            calcResult.setText(String.valueOf(result));
        } else {
            calcResult.setText("計算結果");
        }
    }

    private int calc() {
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();
        int number1 = Integer.parseInt(input1);
        int number2 = Integer.parseInt(input2);
        // XXX positionで見るのイマイチ。getSelectedItemでStringが取れる？
        int operator = operatorSelector.getSelectedItemPosition();
        switch (operator) {
            case 0:
                return number1 + number2;
            case 1:
                return number1 - number2;
            case 2:
                return number1 * number2;
            case 3:
                return number1 / number2;
            default:
                throw new IllegalStateException();
        }
    }
}
