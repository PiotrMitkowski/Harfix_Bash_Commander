package pl.miecinka.harfixbashcommander.commander;

/**
 * Created by emsi on 08.05.2016.
 */
public class CommandExecutionInfo {
    private Boolean isSuccess;
    private String execMessage;


    public CommandExecutionInfo(Boolean isSuccess, String command)
    {
        this.isSuccess = isSuccess;
        this.execMessage = command;
    }

    public String getExecMessage() {
        return execMessage;
    }

    public void setExecMessage(String execMessage) {
        this.execMessage = execMessage;
    }

    public Boolean getIsSuccess() {

        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
