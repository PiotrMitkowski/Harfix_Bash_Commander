package pl.miecinka.harfixbashcommander;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pl.miecinka.harfixbashcommander.commander.CommandExecutionInfo;
import pl.miecinka.harfixbashcommander.commander.CommandHandler;
import pl.miecinka.harfixbashcommander.mods.Governor;

public class MainActivity extends AppCompatActivity {

    private Spinner governorSpinner;
    private Button runCmdButton;
    private TextView commandLabel;
    private TextView kernelInfoLabel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        governorSpinner = (Spinner)findViewById(R.id.governor_select);
        runCmdButton = (Button)findViewById(R.id.run_command);
        commandLabel = (TextView)findViewById(R.id.command_label);
        kernelInfoLabel = (TextView)findViewById(R.id.kernel_info);
        context = this;

        kernelInfoLabel.setText("Kernel: " + CommandHandler.getKernelInfo());

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Governor.names);
        governorSpinner.setAdapter(stringAdapter);

        runCmdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedValue = governorSpinner.getSelectedItem().toString();
                CommandExecutionInfo executionInfo = CommandHandler.changeGovernor(selectedValue, context);
                commandLabel.setText("Executing Command: " + executionInfo.getExecMessage());
                Boolean isSuccess = executionInfo.getIsSuccess();
                if(isSuccess)
                {
                    Toast.makeText(context, "Pomyślnie zmieniono Governor na " + selectedValue, Toast.LENGTH_SHORT);
                }
                else
                {
                    Toast.makeText(context, "Błąd zmiany Governora", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
