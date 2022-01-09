package com.WitchHunter.Internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Client {
    private boolean isStop;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private Map<Integer, List<Command>> commandMap = Collections.synchronizedMap(new HashMap<>());;

    public static class Command {
        private int serialNum;
        private int commandCode;
        private ArrayList<String> strs;
        private String IP;

        public Command(int serialNum, int commandCode,String IP, ArrayList<String> strs) {
            this.commandCode = commandCode;
            this.serialNum = serialNum;
            this.strs = strs;
            this.IP =IP;
        }

        public int getSerialNum() {
            return serialNum;
        }

        public int getCommandCode() {
            return commandCode;
        }

        public ArrayList<String> getStrs() {
            return strs;
        }
    }

    private static Client client;

    private Client() {
        this.isStop = false;
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public String getIP(){
        return socket.getLocalAddress().toString();
//        return "127.0.0.1";
    }

    public static String getLocalAddress() {
        return "127.0.0.1";
    }

    public void start(int port, String serverIP) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean a = true;
//                while (a){
                    try {
                        //建立連線指定Ip和埠的socket
                        socket = new Socket(serverIP, port);
                        //獲取系統標準輸入流
                        out = new PrintWriter(socket.getOutputStream());
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        a = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                }
            }
        }).start();
    }

    public void connect() throws IOException {
        new Thread(() -> {
            try {
                while (!isStop) {
                    String str = "";
//                    System.out.println("無指令");
                    if(in!=null){
                        str = in.readLine();// 逗號分隔字串
                        getCommand(str);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void closeConnect() throws IOException {
        isStop = true;
        out.close();
        in.close();
        socket.close();
    }

    public void sendCommand(Command command) {
        StringBuilder input = new StringBuilder();
        input.append(command.serialNum);
        input.append(",");
        input.append(command.commandCode);
        input.append(",");
        input.append(command.IP);
        for (String string : command.strs) {
            input.append(",");
            input.append(string);
        }
        String msg = input.toString();
        if(out!=null){
            out.println(msg);
            out.flush();
        }
    }

    public void getCommand(String str) {
        synchronized (str) {
            ArrayList<String> parsedData = parse(str);
            int serialNum = Integer.parseInt(parsedData.get(0));
            int commandCode = Integer.parseInt(parsedData.get(1));
            String IP = parsedData.get(2);
            parsedData.remove(0);
            parsedData.remove(0);
            parsedData.remove(0);
            Command cmd = new Command(serialNum, commandCode, IP,parsedData);
            if(commandMap.containsKey(serialNum)){
                commandMap.get(serialNum).add(cmd);
            }else{
                List<Command> ll = Collections.synchronizedList(new LinkedList<>());
                synchronized (ll) {
                    ll.add(cmd);
                    commandMap.put(serialNum, ll);
                }

            }
        }
    }

    public void consume(CommandReceiver cr) {//消化緩衝區裡的指令

        if (commandMap != null) {
            for (int key : commandMap.keySet()) {
                List<Command> ll = commandMap.get(key);
                synchronized (ll) {
                    for (Command command : ll) {
                        cr.run(command);//執行指令
                    }
                    ll.clear();//執行完刪除指令
                }
            }
        }
    }

    public static ArrayList<String> parse(String data) {
        String[] str = data.split(",");
        ArrayList<String> strList = new ArrayList<>(Arrays.asList(str));
        return strList;
    }
}