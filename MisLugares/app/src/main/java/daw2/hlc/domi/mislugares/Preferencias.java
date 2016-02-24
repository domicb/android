package daw2.hlc.domi.mislugares;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by 2DAW on 09/12/2015.
 */
public class Preferencias extends PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}