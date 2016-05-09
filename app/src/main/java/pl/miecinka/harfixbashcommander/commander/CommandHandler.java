package pl.miecinka.harfixbashcommander.commander;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pl.miecinka.harfixbashcommander.mods.Governor;

/**
 * Created by emsi on 07.05.2016.
 */
public class CommandHandler {

    public static CommandExecutionInfo changeGovernor(String value, Context context)
    {
        CommandExecutionInfo executionInfo = execCommand(value, Governor.path, context);
        return executionInfo;
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

    private static CommandExecutionInfo execCommand(String value, String path, Context context)
    {
        String command = "echo " + value + " >> " + path;
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
}
