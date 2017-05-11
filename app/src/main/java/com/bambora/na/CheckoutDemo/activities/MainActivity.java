/*
 * Copyright (c) 2017 Bambora.
 */

package com.bambora.na.CheckoutDemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bambora.na.CheckoutDemo.R;
import com.bambora.na.checkout.activities.CheckoutActivity;
import com.bambora.na.checkout.models.Options;
import com.bambora.na.checkout.models.CheckoutResult;
import com.bambora.na.checkout.models.Purchase;

import org.json.JSONException;

import java.util.Currency;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startCheckout();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CheckoutActivity.REQUEST_CHECKOUT) {
            String error = "";
            CheckoutResult result = null;

            if (resultCode == Activity.RESULT_OK) {
                result = data.getParcelableExtra(CheckoutActivity.EXTRA_CHECKOUT_RESULT);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                error = getResources().getString(R.string.demo_checkout_cancelled);
            } else {
                error = getResources().getString(R.string.demo_checkout_error);
                result = data.getParcelableExtra(CheckoutActivity.EXTRA_CHECKOUT_RESULT);
            }

            showError(error);
            showResults(result);
        }
    }

    private void startCheckout() {

        Options options = getOptionsForThisDemo();
        Purchase purchase = getPurchaseForThisDemo();

        Intent intent = new Intent(CheckoutActivity.ACTION_CHECKOUT_LAUNCH);
        intent.putExtra(CheckoutActivity.EXTRA_OPTIONS, options);
        intent.putExtra(CheckoutActivity.EXTRA_PURCHASE, purchase);

        startActivityForResult(intent, CheckoutActivity.REQUEST_CHECKOUT);
    }

    private Purchase getPurchaseForThisDemo() {

        Currency currency = Currency.getInstance(Purchase.CURRENCY_CODE_CANADA);
        Purchase purchase = new Purchase(123.45, currency); // Required fields: amount, currency

        purchase.setDescription("Item 1, Item 2, Item 3, Item 4"); // default: ""

        return purchase;
    }

    private Options getOptionsForThisDemo() {

        Options options = new Options();

        if (!((CheckBox) findViewById(R.id.demo_checkbox_image)).isChecked()) {
            options.setCompanyLogoResourceId(R.drawable.custom_company_logo); // default: null
        }
        options.setCompanyName("Cabinet of Curiosities"); // default: ""

        if (!((CheckBox) findViewById(R.id.demo_checkbox_billing)).isChecked()) {
            options.setIsBillingAddressRequired(false); // default: true
        }
        if (!((CheckBox) findViewById(R.id.demo_checkbox_shipping)).isChecked()) {
            options.setIsShippingAddressRequired(false); // default: true
        }

        if (((CheckBox) findViewById(R.id.demo_checkbox_theme)).isChecked()) {
            options.setThemeResourceId(R.style.Theme_Checkout_Custom); // default: Theme.Checkout
        }

        if (!((CheckBox) findViewById(R.id.demo_checkbox_timeout)).isChecked()) {
            options.setTokenRequestTimeoutInSeconds(7); // default: 6
        }

        return options;
    }

    private void showError(String error) {
        TextView text = (TextView) findViewById(R.id.demo_checkout_error);
        text.setText(error);
    }

    private void showResults(CheckoutResult checkoutResult) {
        String result = "";
        try {
            if (checkoutResult != null) {
                result = checkoutResult.toJsonObject().toString(4);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("showResults", result);

        TextView text = (TextView) findViewById(R.id.demo_checkout_results);
        text.setText(result);
        text.setVisibility(View.VISIBLE);
        text.setMovementMethod(new ScrollingMovementMethod());
    }
}
