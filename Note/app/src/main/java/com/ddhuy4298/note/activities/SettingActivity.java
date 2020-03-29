package com.ddhuy4298.note.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.databinding.ActivitySettingBinding;
import com.ddhuy4298.note.fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    public static SharedPreferences shared;
    public static final String APP_PREFERENCES = "note_setting";
    public static final String THEME_KEY = "app_theme";

    int theme = 2131755016;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SettingFragment())
                .commit();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        shared = PreferenceManager.getDefaultSharedPreferences(this);

        setTheme(theme);
    }

    @Override
    protected void onResume() {
        super.onResume();
        theme = shared.getInt(THEME_KEY, 0);
        Toast.makeText(this, theme + "", Toast.LENGTH_SHORT).show();

    }
}
