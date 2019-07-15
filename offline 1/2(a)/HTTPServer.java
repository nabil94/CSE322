/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpserver;

/**
 *
 * @author User
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPServer {

    public static int workerThreadCount = 0;

    public static void main(String args[]) {
        int id = 1;

        try {
            ServerSocket ss = new ServerSocket(6789);
            System.out.println("Server has been started successfully.");

            while (true) {
                Socket s = ss.accept();		//TCP Connection
                WorkerThread wt = new WorkerThread(s, id);
                Thread t = new Thread(wt);
                t.start();
                workerThreadCount++;
                System.out.println("Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);
                id++;
            }
        } catch (Exception e) {
            System.err.println("Problem in ServerSocket operation. Exiting main.");
        }
    }
}

class WorkerThread implements Runnable {

    private Socket socket;
    //String msgss = "";
    private int id = 0;

    public WorkerThread(Socket s, int id) {
        this.socket = s;
        this.id = id;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            ArrayList<String> msgs = new ArrayList<>();
            String input, input1;
            String msg1 = "";
            String msg = "";
            while ((input = br.readLine()) != null) {
                msg = msg + input;
                msgs.add(input);
                if (input.isEmpty()) {
                    break;
                }
            }
            String msgss = "";
            if (msg.startsWith("GET")) {
                System.out.println(msg);
                System.out.println(msgs);
                //String msgss = "";
                FileWriter fr = null;
                try {
                    fr = new FileWriter(new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "index.txt"));
                    msgss = msgss + msg;
                    fr.write(msgss);
                    //ff = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //close resources
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //System.out.println(ms+g + " " + m);
            System.out.println(msgs);

            //if (input != null) {
            StringTokenizer strTok = new StringTokenizer(msg);
            String request = strTok.nextToken();
            String reqFile = strTok.nextToken();
            //String version = strTok.nextToken();
            String MIMEType = null;

            if (reqFile.endsWith("html") || reqFile.endsWith("htm")) {
                MIMEType = "text/html";
            } else if (reqFile.endsWith("pdf")) {
                MIMEType = "application/pdf";
            } else {
                MIMEType = "text/plain";
            }
            File file = new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", reqFile);
            int fileLength = (int) file.length();
            if (request.equalsIgnoreCase("GET")) {
                //System.out.println("i am here");
                //socket.close();
                if (reqFile.equalsIgnoreCase("/")) {
                    File defaultFile = new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "index.html");
                    int defFileLength = (int) file.length();
                    FileInputStream fis = null;
                    byte[] fileData = new byte[defFileLength];

                    try {
                        fis = new FileInputStream(defaultFile);
                        fis.read(fileData);
                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                    }
                    //socket.close();
                    // send HTTP Headers
                    pr.println("HTTP/1.1 200 OK");
                    pr.println("Server: Java HTTP Server from SSaurel : 1.0");
                    pr.println("Date: " + new Date());
                    pr.println("Content-type: text/html");
                    pr.println("Content-length: " + fileLength);
                    pr.println();
                    pr.flush();

                    bos.write(fileData, 0, fileLength);
                    bos.flush();
                } else {
                    if (file.exists() == true) {

                        FileInputStream fis = null;
                        byte[] fileData = new byte[fileLength];

                        try {
                            fis = new FileInputStream(file);
                            fis.read(fileData);
                        } finally {
                            if (fis != null) {
                                fis.close();
                            }
                        }

                        // send HTTP Headers
                        pr.println("HTTP/1.1 200 OK");
                        pr.println("Server: Java HTTP Server from SSaurel : 1.0");
                        pr.println("Date: " + new Date());
                        pr.println("Content-type: " + MIMEType);
                        pr.println("Content-length: " + fileLength);
                        pr.println();
                        pr.flush();

                        bos.write(fileData, 0, fileLength);
                        bos.flush();

                    } else {
                        File errorFile = new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "error.html");
                        int errFileLength = (int) errorFile.length();
                        System.out.println(errorFile.getName());
                        FileInputStream fis = null;
                        byte[] fileData = new byte[errFileLength];

                        try {
                            fis = new FileInputStream(errorFile);
                            fis.read(fileData);
                        } finally {
                            if (fis != null) {
                                fis.close();
                            }
                        }

                        // send HTTP Headers
                        pr.println("HTTP/1.1 200 OK");
                        pr.println("Server: Java HTTP Server from SSaurel : 1.0");
                        pr.println("Date: " + new Date());
                        pr.println("Content-type: text/html");
                        pr.println("Content-length: " + errFileLength);
                        pr.println();
                        pr.flush();

                        bos.write(fileData, 0, errFileLength);
                        bos.flush();

                    }
                }

            } else if (request.equalsIgnoreCase("POST")) {

                StringBuilder payload = new StringBuilder();
                while (br.ready()) {
                    payload.append((char) br.read());
                }
                System.out.println("Payload data is: " + payload.toString());
                FileWriter fr = null;
                try {
                    fr = new FileWriter(new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "index.txt"));
                    msgss = msgss + msg;
                    fr.write(msgss + payload.toString());
                    //ff = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //close resources
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String s = payload.toString();
          
                String[] str = s.split("=");
                String g;
                if(str.length == 1){
                    g = "";
                }
                else{
                    g = str[1];
                }

                File FormFile = new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "form_submited.html");
                int FormFileLength = (int) FormFile.length();

                try {
                    BufferedReader in = new BufferedReader(new FileReader(new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "Post.html")));

                    String mystring;
                    String gg = "";
                    while ((mystring = in.readLine()) != null) {
                        gg = gg + mystring;
                    }
                    //System.out.println(gg);
                    String[] fl = gg.split("Post->");
                    String ff0 = fl[0];
                    String ff1 = fl[1];
                    String ff = ff0 + " Post-> " + g + ff1;
                    System.out.println(ff);

                    File FormFile1 = new File("C:\\Users\\User\\Desktop\\CSE322 Oflines\\HTTPServer\\src\\httpserver", "form_submited.html");
                    //int FormFileLength = (int) FormFile1.length();
                    FileWriter fr1 = null;
                    try {
                        fr1 = new FileWriter(FormFile1);
                        fr1.write(ff);
                        //ff = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        //close resources
                        try {
                            fr1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    FileInputStream fis = null;
                    byte[] fileData = new byte[FormFileLength];

                    try {
                        fis = new FileInputStream(FormFile1);
                        fis.read(fileData);
                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                    }
                    //socket.close();
                    // send HTTP Headers
                    pr.println("HTTP/1.1 200 OK");
                    pr.println("Server: Java HTTP Server from SSaurel : 1.0");
                    pr.println("Date: " + new Date());
                    pr.println("Content-type: text/html");
                    pr.println("Content-length: " + FormFileLength);
                    pr.println();
                    pr.flush();

                    bos.write(fileData, 0, FormFileLength);
                    bos.flush();

                    ff0 = null;
                    ff1 = null;

                } catch (IOException e) {
                    System.out.println("Exception Occurred" + e);
                }

                str = null;
                //System.out.println("Payload data is: " + payload.toString());
                // String s = payload.toString();
                // String[] str = s.split("=");
                // System.out.println(str[1]);
                
            } else {

            }
            br.close();
            pr.close();
            bos.close();
        } catch (Exception e) {

        } finally {
            try {
                socket.close();
                //br.close();
            } catch (IOException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
