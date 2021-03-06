package org.deltaproject.manager.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AppAgentManager {

    /* AppAgent */
    private Socket appSocket;
    private DataOutputStream dos;
    private DataInputStream dis;

    /* Activator for OpenDaylight */
    private Socket actSocket;
    private DataOutputStream dos2;
    private DataInputStream dis2;

    private String targetController;

    private Configuration cfg = Configuration.getInstance();

    public AppAgentManager() {
        this.targetController = cfg.getTargetController();
    }

    public void closeSocket() {
        if (actSocket != null)
            try {
                actSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        if (appSocket != null)
            try {
                appSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public void setActSocket(Socket in, DataOutputStream w, DataInputStream r) {
        this.actSocket = in;
        this.dos2 = w;
        this.dis2 = r;
    }

    public void setAppSocket(Socket in, DataOutputStream w, DataInputStream r) {
        this.appSocket = in;
        this.dos = w;
        this.dis = r;
    }

    public boolean attempt(String code) {
        return true;
    }

    public void setFuzzingInputs() {

    }

    public boolean write(String code) {
        if (targetController.contains("OpenDaylight")) {
            if (code.contains("3.1.090") || code.contains("3.1.100")) {
                try {
                    dos2.writeUTF(code);
                    dos2.flush();
                    return true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            }
        }

        try {
            dos.writeUTF(code);
            dos.flush();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            return false;
        }
    }


    public void startFuzzing() {

    }

    public void setTargetType(int target) {
        write(Integer.toString(target));
    }

    public String read() {
        String result = "";

        try {
            if (dis.available() > 0)
                result = dis.readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String read2() {
        String result = "";
        try {
            result = dis2.readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
