package in.blogspot.theiitiansway.carelightstrial1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by Satyam Kumar on 23-01-2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_carelights);

    }
}
