package com.example.mysocks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView SUMAmount;
    private List<Category> categories;

    private final String adornment = " kr";

    private final String MAT_AMOUNT_CODE = "Exps_Mat";
    private final String DRIKKE_AMOUNT_CODE = "Exps_Drikke";
    private final String UTE_AMOUNT_CODE = "Exps_Ute";
    private final String ANNET_AMOUNT_CODE = "Exps_Annet";

    private final String MAT_INIT_CODE = "Exps_Init_Mat";
    private final String DRIKKE_INIT_CODE = "Exps_Init_Drikke";
    private final String UTE_INIT_CODE = "Exps_Init_Ute";
    private final String ANNET_INIT_CODE = "Exps_Init_Annet";

    /**
     * Helper class Category.
     *
     * Holds buttons and fields for each budget category.
     */
    private class Category {
        String title;
        Integer initValue;
        Integer storeValue;
        TextView amount;
        TextView juster;
        TextView init;
        Button add;
        Button sub;

        Category(
                String title,
                Integer storeValue,
                Integer initValue,
                final TextView amount,
                final TextView juster,
                final TextView init,
                Button add,
                Button sub
        ) {
            this.title = title;
            this.initValue = initValue;
            this.storeValue = storeValue;
            this.amount = amount;
            this.juster = juster;
            this.init = init;
            this.add = add;
            this.sub = sub;

            this.amount.setText(storeValue.toString() + adornment);
            this.amount.setKeyListener(null);
            this.juster.setText("0");
            this.init.setText(initValue.toString());

            this.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer addValue = Integer.parseInt(juster.getText().toString().replaceAll("[^\\d.]", ""));
                    Integer currentValue = Integer.parseInt(amount.getText().toString().replaceAll("[^\\d.]", ""));
                    Integer newValue = currentValue + addValue;
                    amount.setText(newValue.toString() + adornment);
                    juster.setText("0");
                    closeKeyBoard();
                    juster.clearFocus();
                    updateSUMAndText();
                    save();
                }
            });

            this.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer subValue = Integer.parseInt(juster.getText().toString().replaceAll("[^\\d.]", ""));
                    Integer currentValue = Integer.parseInt(amount.getText().toString().replaceAll("[^\\d.]", ""));
                    Integer newValue = currentValue - subValue;
                    amount.setText(newValue.toString() + adornment);
                    juster.setText("0");
                    closeKeyBoard();
                    juster.clearFocus();
                    updateSUMAndText();
                    save();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValues();
    }

    /**
     * Initialize buttons and fields
     *
     *
     */
    private void initValues() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences sharedPref = getSharedPreferences(MAT_AMOUNT_CODE, MODE_PRIVATE);
        String storageValue = sharedPref.getString(MAT_AMOUNT_CODE, null);
        Integer matStored = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        sharedPref = getSharedPreferences(MAT_INIT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(MAT_INIT_CODE, null);
        Integer matInit = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        /* ---------------------------------------------- */

        sharedPref = getSharedPreferences(DRIKKE_AMOUNT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(DRIKKE_AMOUNT_CODE, null);
        Integer drikkeStored = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        sharedPref = getSharedPreferences(DRIKKE_INIT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(DRIKKE_INIT_CODE, null);
        Integer drikkeInit = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        /* ---------------------------------------------- */

        sharedPref = getSharedPreferences(UTE_AMOUNT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(UTE_AMOUNT_CODE, null);
        Integer uteStored = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        sharedPref = getSharedPreferences(UTE_INIT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(UTE_INIT_CODE, null);
        Integer uteInit = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        /* ---------------------------------------------- */

        sharedPref = getSharedPreferences(ANNET_AMOUNT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(ANNET_AMOUNT_CODE, null);
        Integer annetStored = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        sharedPref = getSharedPreferences(ANNET_INIT_CODE, MODE_PRIVATE);
        storageValue = sharedPref.getString(ANNET_INIT_CODE, null);
        Integer annetInit = storageValue == null ? 0 : Integer.parseInt(storageValue.replaceAll("[^\\d.]", ""));

        /* ---------------------------------------------- */

        categories = new ArrayList<>();
        categories.add(
            new Category("Mat", matStored, matInit,
                    (TextView) findViewById(R.id.MatAmount),
                    (TextView) findViewById(R.id.MatJuster),
                    (TextView) findViewById(R.id.MatInit),
                    (Button) findViewById(R.id.MatAdd),
                    (Button) findViewById(R.id.MatSub)
            )
        );
        categories.add(
                new Category("Drikke", drikkeStored, drikkeInit,
                        (TextView) findViewById(R.id.DrikkeAmount),
                        (TextView) findViewById(R.id.DrikkeJuster),
                        (TextView) findViewById(R.id.DrikkeInit),
                        (Button) findViewById(R.id.DrikkeAdd),
                        (Button) findViewById(R.id.DrikkeSub)
                )
        );
        categories.add(
                new Category("Ute", uteStored, uteInit,
                        (TextView) findViewById(R.id.UteAmount),
                        (TextView) findViewById(R.id.UteJuster),
                        (TextView) findViewById(R.id.UteInit),
                        (Button) findViewById(R.id.UteAdd),
                        (Button) findViewById(R.id.UteSub)
                )
        );
        categories.add(
                new Category("Annet", annetStored, annetInit,
                        (TextView) findViewById(R.id.AnnetAmount),
                        (TextView) findViewById(R.id.AnnetJuster),
                        (TextView) findViewById(R.id.AnnetInit),
                        (Button) findViewById(R.id.AnnetAdd),
                        (Button) findViewById(R.id.AnnetSub)
                )
        );

        SUMAmount = findViewById(R.id.SUMAmount);
        SUMAmount.setKeyListener(null);
        updateSUMAndText();
    }

    /**
     * Closes Keyboard
     *
     *
     */
    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Updates SUM field and texts.
     *
     * We want to know how much is left for the remainder
     * of the week for certain categories.
     */
    private void updateSUMAndText() {
        Integer sum = 0;
        for (Category c : categories) {
            sum += Integer.parseInt(c.amount.getText().toString().replaceAll("[^\\d.]", ""));
        }
        SUMAmount.setText(sum.toString() + adornment);

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DATE) > 25) {
            calendar.add(Calendar.MONTH, 1);
        }
        calendar.set(Calendar.DATE, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date payDay = calendar.getTime();
        Date today = new Date();

        Double daysLeftDouble = (double)(payDay.getTime() - today.getTime()) / (1000 * 3600 * 24);
        Integer daysLeftToPay = (int) Math.ceil(daysLeftDouble);

        Calendar calendar2 = Calendar.getInstance();
        Integer daysLeftOfWeek = 7 - ((calendar2.get(Calendar.DAY_OF_WEEK) - 2) % 7);

        TextView SUMText = findViewById(R.id.SUMText);
        SUMText.setText((int) Math.floor(sum / daysLeftToPay) + " kr/dag\ni " + daysLeftToPay + " dager");

        /* ---------------------------------------------- */

        TextView MatInit = findViewById(R.id.MatInit);
        Integer matInit = Integer.parseInt(MatInit.getText().toString().replaceAll("[^\\d.]", ""));

        TextView MatAmount = findViewById(R.id.MatAmount);
        Integer matValue = Integer.parseInt(MatAmount.getText().toString().replaceAll("[^\\d.]", ""));
        TextView MatText = findViewById(R.id.MatText);

        Double matUtUka = (double) matValue - (((double) matInit / calendar2.getActualMaximum(Calendar.DAY_OF_MONTH)) * (daysLeftToPay - daysLeftOfWeek));
        MatText.setText((int)Math.floor(matUtUka) + " kr\nut uken ");

        /* ---------------------------------------------- */

        TextView DrikkeInit = findViewById(R.id.DrikkeInit);
        Integer drikkeInit = Integer.parseInt(DrikkeInit.getText().toString().replaceAll("[^\\d.]", ""));

        TextView DrikkeAmount = findViewById(R.id.DrikkeAmount);
        Integer drikkeValue = Integer.parseInt(DrikkeAmount.getText().toString().replaceAll("[^\\d.]", ""));
        TextView DrikkeText = findViewById(R.id.DrikkeText);

        Double drikkeUtUka = (double) drikkeValue - (((double) drikkeInit / calendar2.getActualMaximum(Calendar.DAY_OF_MONTH)) * (daysLeftToPay - daysLeftOfWeek));
        DrikkeText.setText((int) Math.floor(drikkeUtUka) + " kr\nut uken ");
    }

    /**
     * Store data
     *
     *
     */
    private void save() {
        for (Category c : categories) {
            SharedPreferences sharedPref = getSharedPreferences("Exps_" + c.title, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            String amountString = c.amount.getText().toString();
            editor.putString("Exps_" + c.title, amountString);
            editor.commit();

            sharedPref = getSharedPreferences("Exps_Init_" + c.title, MODE_PRIVATE);
            editor = sharedPref.edit();
            amountString = c.init.getText().toString();
            editor.putString("Exps_Init_" + c.title, amountString);
            editor.commit();
        }
    }

}
