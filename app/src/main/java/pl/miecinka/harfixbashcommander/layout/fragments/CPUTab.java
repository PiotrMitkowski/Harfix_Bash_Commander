package pl.miecinka.harfixbashcommander.layout.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.PriorityQueue;

import pl.miecinka.harfixbashcommander.R;
import pl.miecinka.harfixbashcommander.adapters.FrequencyArrayAdapter;
import pl.miecinka.harfixbashcommander.commander.CommandExecutionInfo;
import pl.miecinka.harfixbashcommander.commander.CommandHandler;
import pl.miecinka.harfixbashcommander.mods.CPUFrequency;
import pl.miecinka.harfixbashcommander.mods.GovernorValues;

public class CPUTab extends Fragment {
    private Spinner minFreqSpinner;
    private Spinner maxFreqSpinner;
    private Spinner governorSpinner;
    private TextView kernelInfoLabel;
    private Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public CPUTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cputab, container, false);
        minFreqSpinner = (Spinner) view.findViewById(R.id.min_frequency);
        maxFreqSpinner = (Spinner) view.findViewById(R.id.max_frequency);
        governorSpinner = (Spinner) view.findViewById(R.id.governor_select);
        kernelInfoLabel = (TextView) view.findViewById(R.id.kernel_info);
        context = getActivity();

        kernelInfoLabel.setText("Kernel: " + CommandHandler.getKernelInfo());
        initializeMaxFreqSpinner();
        initializeGovernorSpinner();
        initializeMinFreqSpinner();
        return view;
    }

    private void initializeMaxFreqSpinner() {
        ArrayList<CPUFrequency> frequencyList = CPUFrequency.buildFrequenciesList();
        final FrequencyArrayAdapter frequencyAdapter = new FrequencyArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, frequencyList);
        maxFreqSpinner.setAdapter(frequencyAdapter);
        try
        {
            String currentValue = CommandHandler.getValueFromFile(CPUFrequency.maxFrequencyPath, context);
            for (CPUFrequency frequency:frequencyList)
            {
                if(currentValue.equals(frequency.getRawValue()))
                {
                    int position = frequencyAdapter.getPosition(frequency);
                    maxFreqSpinner.setSelection(position);
                    break;
                }
            }
        }
        catch(Exception e)
        {

        }
        maxFreqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Boolean appLoaded = false;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(appLoaded)
                {
                    CPUFrequency frequencyObject = (CPUFrequency)parent.getItemAtPosition(position);
                    Log.i("frequency", frequencyObject.getFormattedValue());
                    String selectedValue = frequencyObject.getRawValue();
                    CommandExecutionInfo executionInfo = CommandHandler.changeMaxFrequency(selectedValue, context);
                    Boolean isSuccess = executionInfo.getIsSuccess();
                    if (isSuccess) {
                        Toast.makeText(context, "Max Frequency changed to " + selectedValue, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error changing max frequency", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    appLoaded = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeMinFreqSpinner() {
        ArrayList<CPUFrequency> frequencyList = CPUFrequency.buildFrequenciesList();
        FrequencyArrayAdapter frequencyAdapter = new FrequencyArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, frequencyList);
        minFreqSpinner.setAdapter(frequencyAdapter);
        try
        {
            String currentValue = CommandHandler.getValueFromFile(CPUFrequency.minFrequencyPath, context);
            for (CPUFrequency frequency:frequencyList)
            {
                if(currentValue.equals(frequency.getRawValue()))
                {
                    int position = frequencyAdapter.getPosition(frequency);
                    minFreqSpinner.setSelection(position);
                    break;
                }
            }
        }
        catch(Exception e)
        {

        }
        minFreqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Boolean appLoaded = false;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (appLoaded) {
                    CPUFrequency frequencyObject = (CPUFrequency) parent.getItemAtPosition(position);
                    Log.i("frequency", frequencyObject.getFormattedValue());
                    String selectedValue = frequencyObject.getRawValue();
                    CommandExecutionInfo executionInfo = CommandHandler.changeMinFrequency(selectedValue, context);
                    Boolean isSuccess = executionInfo.getIsSuccess();
                    if (isSuccess) {
                        Toast.makeText(context, "Min Frequency changed to " + selectedValue, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error changing min frequency", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    appLoaded = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeGovernorSpinner() {
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, GovernorValues.names);
        governorSpinner.setAdapter(stringAdapter);
        try {
            String currentValue = CommandHandler.getValueFromFile(GovernorValues.path, context);
            if(!currentValue.equals(null))
            {
                int position = stringAdapter.getPosition(currentValue);
                governorSpinner.setSelection(position);
            }
        }
        catch(Exception e)
        {
            Log.e("string from governor", e.getMessage());
        }
        governorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Boolean appLoaded = false;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(appLoaded)
                {
                    String selectedValue = parent.getItemAtPosition(position).toString();
                    CommandExecutionInfo executionInfo = CommandHandler.changeGovernor(selectedValue, context);
                    Boolean isSuccess = executionInfo.getIsSuccess();
                    if (isSuccess) {
                        Toast.makeText(context, "Pomyślnie zmieniono GovernorValues na " + selectedValue, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Błąd zmiany Governora", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    appLoaded = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
