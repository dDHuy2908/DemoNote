package com.ddhuy4298.note.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.databinding.ActivitySettingBinding;
import com.ddhuy4298.note.fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    private SharedPreferences shared;
    public static final String APP_PREFERENCES = "note_setting";
    public static final String THEME_KEY = "app_theme";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        setTheme(shared.getInt(THEME_KEY, 0));

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SettingFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shared = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        setTheme(shared.getInt(THEME_KEY, 0));
    }
}
