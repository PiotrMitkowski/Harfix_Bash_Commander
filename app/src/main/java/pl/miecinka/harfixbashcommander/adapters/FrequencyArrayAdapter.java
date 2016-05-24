package pl.miecinka.harfixbashcommander.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.miecinka.harfixbashcommander.mods.CPUFrequency;

/**
 * Created by emsi on 10.05.2016.
 */
public class FrequencyArrayAdapter extends ArrayAdapter {

    public FrequencyArrayAdapter(Context context, int textViewResourceId, ArrayList<CPUFrequency> values)
    {
        super(context, textViewResourceId, values);
    }



}
