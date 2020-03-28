package com.ddhuy4298.note.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.ddhuy4298.note.R;
import com.ddhuy4298.note.activities.SettingActivity;

import static com.ddhuy4298.note.activities.SettingActivity.APP_PREFERENCES;
import static com.ddhuy4298.note.activities.SettingActivity.THEME_KEY;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_DARK_THEME = "dark_theme";

    private SharedPreferences shared;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        shared = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREF_DARK_THEME)) {
            final SwitchPreference darkThemeSwitch = findPreference(key);
            darkThemeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (darkThemeSwitch.isChecked()) {
                        shared.edit().putInt(THEME_KEY, R.style.AppTheme).apply();
                        Toast.makeText(getContext(), R.style.AppTheme + "",Toast.LENGTH_SHORT).show();
                        darkThemeSwitch.setChecked((Boolean) newValue);
                    } else {
                        shared.edit().putInt(THEME_KEY, R.style.AppTheme_Dark).apply();
                        Toast.makeText(getContext(), R.style.AppTheme_Dark + "", Toast.LENGTH_SHORT).show();
                        darkThemeSwitch.setChecked((Boolean) newValue);
                    }
                    return false;
                }
            });
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
