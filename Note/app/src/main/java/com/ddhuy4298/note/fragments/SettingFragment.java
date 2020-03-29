package com.ddhuy4298.note.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.activities.SettingActivity;

import static com.ddhuy4298.note.activities.SettingActivity.THEME_KEY;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals("dark_theme")) {
//            final SwitchPreference darkThemeSwitch = findPreference(key);
//            if (darkThemeSwitch != null) {
//                darkThemeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                    @Override
//                    public boolean onPreferenceChange(Preference preference, Object newValue) {
//                        if (darkThemeSwitch.isChecked()) {
//                            SettingActivity.shared.edit().putInt(THEME_KEY, R.style.AppTheme_Dark).apply();
//                            Toast.makeText(getContext(), "Dark", Toast.LENGTH_SHORT).show();
////                        darkThemeSwitch.setChecked((Boolean) newValue);
//                        } else {
//                            SettingActivity.shared.edit().putInt(THEME_KEY, R.style.AppTheme).apply();
//                            Toast.makeText(getContext(), "Light",Toast.LENGTH_SHORT).show();
////                        darkThemeSwitch.setChecked((Boolean) newValue);
//                        }
//                        return true;
//                    }
//                });
//            }
//        }
        Boolean b = SettingActivity.shared.getBoolean("dark_theme", false);
        if (b) {
            SettingActivity.shared.edit().putInt(THEME_KEY, R.style.AppTheme_Dark).apply();
            Toast.makeText(getContext(), R.style.AppTheme_Dark + "", Toast.LENGTH_SHORT).show();
        } else {
            SettingActivity.shared.edit().putInt(THEME_KEY, R.style.AppTheme).apply();
            Toast.makeText(getContext(), R.style.AppTheme + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
