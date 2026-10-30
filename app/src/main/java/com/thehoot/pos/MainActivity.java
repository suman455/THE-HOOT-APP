package com.thehoot.pos;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String PREFS = "the_hoot_settings";
    private LinearLayout root;
    private EditText address;
    private WebView web;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        String saved = getSharedPreferences(PREFS, MODE_PRIVATE).getString("server", "http://127.0.0.1:5000");
        showConnectScreen(saved);
    }

    private void showConnectScreen(String current) {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(36, 60, 36, 30);
        root.setBackgroundColor(Color.rgb(16,27,50));
        TextView title = new TextView(this);
        title.setText("🦉  THE HOOT"); title.setTextColor(Color.WHITE); title.setTextSize(30); title.setPadding(0,20,0,12);
        root.addView(title);
        TextView hint = new TextView(this);
        hint.setText("Restaurant POS  •  Connect to your Termux server"); hint.setTextColor(Color.LTGRAY); hint.setTextSize(15);
        root.addView(hint);
        address = new EditText(this);
        address.setSingleLine(true); address.setText(current); address.setHint("http://192.168.1.10:5000");
        address.setTextColor(Color.BLACK); address.setHintTextColor(Color.GRAY); address.setBackgroundColor(Color.WHITE); address.setPadding(18,12,18,12);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2); lp.topMargin=40; root.addView(address, lp);
        Button connect = new Button(this); connect.setText("CONNECT / OPEN POS");
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1,-2); bp.topMargin=18; root.addView(connect,bp);
        TextView note = new TextView(this);
        note.setText("Server Termux में चालू होना चाहिए। दूसरे मोबाइल में यहाँ सर्वर फोन का Wi-Fi IP डालें।");
        note.setTextColor(Color.LTGRAY); note.setPadding(0,24,0,0); root.addView(note);
        connect.setOnClickListener(v -> {
            String url=address.getText().toString().trim();
            if (!url.startsWith("http://") && !url.startsWith("https://")) url="http://"+url;
            if (url.endsWith("/")) url=url.substring(0,url.length()-1);
            getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("server",url).apply();
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(address.getWindowToken(),0);
            openWeb(url);
        });
        setContentView(root);
    }

    private void openWeb(String url) {
        web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl(url);
        setContentView(web);
    }

    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack();
        else showConnectScreen(getSharedPreferences(PREFS,MODE_PRIVATE).getString("server","http://127.0.0.1:5000"));
    }
}
