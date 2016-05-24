package pl.miecinka.harfixbashcommander.commander;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pl.miecinka.harfixbashcommander.mods.CPUFrequency;
import pl.miecinka.harfixbashcommander.mods.GovernorValues;

/**
 * Created by emsi on 07.05.2016.
 */
public class CommandHandler {

    public static CommandExecutionInfo changeGovernor(String value, Context context)
    {
        CommandExecutionInfo executionInfo = changeValueInPath(value, GovernorValues.path, context);
        return executionInfo;
    }

    public static String getValueFromFile(String path, Context context)
    {
        String command = "cat " + path; String resultStr = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            int result = process.waitFor();
            if(result == 0)
            {

                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = br.readLine()) != null){
                    resultStr = resultStr + line;
                    Log.d("execCmd", line);
                }
            }
        } catch (IOException e) {
            new AlertDialog.Builder(context)
                    .setTitle("Error executing command")
                    .setMessage(e.getMessage())
                    .show();
            return "error";
        } catch (InterruptedException e) {
            new AlertDialog.Builder(context)
                    .setTitle("Error executing command")
                    .setMessage(e.getMessage())
                    .show();
            return "error";
        }
        return resultStr;
    }

    public static String getKernelInfo()
    {
        String kernelInfo;
        try
        {
            Process child = Runtime.getRuntime().exec("uname -r");
            InputStream is;
            if(child.waitFor() == 0)
            {
                is = child.getInputStream();
            }
            else
            {
                is = child.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is), 100);
            kernelInfo = br.readLine();
            br.close();

        }
        catch(IOException e)
        {
            return e.getMessage();
        }
        catch(InterruptedException e)
        {
            return e.getMessage();
        }
        return kernelInfo;
    }

    private static CommandExecutionInfo changeValueInPath(String value, String path, Context context)
    {
        String command = "echo " + value + " > " + path;
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (IOException e) {
            new AlertDialog.Builder(context)
                    .setTitle("Error executing command")
                    .setMessage(e.getMessage())
                    .show();
            return new CommandExecutionInfo(false, e.getMessage());
        } catch (InterruptedException e) {
            new AlertDialog.Builder(context)
                    .setTitle("Error executing command")
                    .setMessage(e.getMessage())
                    .show();
            return new CommandExecutionInfo(false, e.getMessage());
        }
        return new CommandExecutionInfo(true, command);
    }

    public static CommandExecutionInfo changeMinFrequency(String value, Context context) {
        CommandExecutionInfo executionInfo = changeValueInPath(value, CPUFrequency.minFrequencyPath, context);
        return executionInfo;
    }

    public static CommandExecutionInfo changeMaxFrequency(String value, Context context)
    {
        CommandExecutionInfo executionInfo = changeValueInPath(value, CPUFrequency.maxFrequencyPath, context);
        return executionInfo;
    }

}
