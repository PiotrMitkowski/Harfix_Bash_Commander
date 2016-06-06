package pl.miecinka.harfixbashcommander.layout.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import pl.miecinka.harfixbashcommander.R;
import pl.miecinka.harfixbashcommander.commander.CommandHandler;
import pl.miecinka.harfixbashcommander.mods.MiscHandler;


public class MiscTab extends Fragment {

    private Switch touchWakeBt;
    private Switch knockOnBt;
    private Switch chargerModeBt;
    private SeekBar delayValue;
    private SeekBar ACPowerValue;
    private SeekBar USBPowerValue;
    private TextView ACValue;
    private TextView USBValue;
    private TextView delayDisplay;
    private Context context;

    public MiscTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_misc_tab, container, false);
        touchWakeBt = (Switch) view.findViewById(R.id.touchwake);
        delayValue = (SeekBar) view.findViewById(R.id.delayValue);
        ACPowerValue = (SeekBar) view.findViewById(R.id.acpower);
        USBPowerValue = (SeekBar) view.findViewById(R.id.usbpower);
        knockOnBt = (Switch) view.findViewById(R.id.knockOn);
        chargerModeBt = (Switch) view.findViewById(R.id.chargerMode);
        ACValue = (TextView) view.findViewById(R.id.acvalue);
        USBValue = (TextView) view.findViewById(R.id.usbvalue);
        delayDisplay = (TextView) view.findViewById(R.id.delayDisplay);

        ACPowerValue.incrementProgressBy(100);
        USBPowerValue.incrementProgressBy(100);
        loadValues();
        touchWakeBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String valueToInsert = "0";
                if (isChecked) {
                    valueToInsert = "1";
                }
                CommandHandler.toggleTouchWake(valueToInsert, context);
            }
        });

        delayValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newValue = progress * 1000;
                CommandHandler.setTouchWakeDelay(Integer.toString(newValue), context);
                delayDisplay.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ACPowerValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newValue = progress + 200;
                CommandHandler.setACPower(Integer.toString(newValue), context);
                ACValue.setText(Integer.toString(newValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        USBPowerValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newValue = progress + 200;
                CommandHandler.setUSBPower(Integer.toString(newValue), context);
                USBValue.setText(Integer.toString(newValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        knockOnBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String valueToInsert = "0";
                if (isChecked) {
                    valueToInsert = "1";
                }
                CommandHandler.toggleKnockOn(valueToInsert, context);

            }
        });

        chargerModeBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String valueToInsert = "0";
                if (isChecked) {
                    valueToInsert = "1";
                }
                CommandHandler.toggleChargerMode(valueToInsert, context);
            }
        });

        return view;
    }

    private void loadValues()
    {
        String touchWakeState = CommandHandler.getValueFromFile(MiscHandler.TOUCHWAKE_PATH, context);
        String delayState = CommandHandler.getValueFromFile(MiscHandler.DELAY_PATH, context);
        String ACPowerSet = CommandHandler.getValueFromFile(MiscHandler.AC_CHARGER_PATH, context);
        String USBPowerSet = CommandHandler.getValueFromFile(MiscHandler.USB_CHARGER_PATH, context);
        ACPowerSet = ACPowerSet.substring(0, ACPowerSet.indexOf(" "));
        USBPowerSet = USBPowerSet.substring(0, USBPowerSet.indexOf(" "));
        if(touchWakeState.contains("1"))
        {
            touchWakeBt.setChecked(true);
        }
        int delayDisplayValue = Integer.parseInt(delayState)/1000;
        delayValue.setProgress(delayDisplayValue);
        ACPowerValue.setProgress(Integer.parseInt(ACPowerSet) - 200);
        USBPowerValue.setProgress(Integer.parseInt(USBPowerSet) - 200);
        delayDisplay.setText(Integer.toString(delayDisplayValue));
        ACValue.setText(ACPowerSet);
        USBValue.setText(USBPowerSet);
    }

}
