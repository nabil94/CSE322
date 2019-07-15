/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class SMTPClient {

    private static Socket s = null;
    private static BufferedReader br = null;
    private static PrintWriter pr = null;

    public static void main(String args[]) throws UnknownHostException, IOException {
        String mailServer = "smtp.sendgrid.net";
        InetAddress mailHost = InetAddress.getByName(mailServer);
        //InetAddress mailHost = InetAddress.getLocalHost();
        InetAddress localHost = InetAddress.getLocalHost();
        s = new Socket(mailHost, 25);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pr = new PrintWriter(s.getOutputStream(), true);
        String ID = br.readLine();
        System.out.println(ID);
        int state = 0;
        String g = "HELO " + localHost.getHostName();
        pr.println(g);
        state = 1;
        //from here
        //k = 1;
        System.out.println(br.readLine());
        pr.println("AUTH LOGIN");
        System.out.println(br.readLine());
        pr.println(new String(Base64.getEncoder().encode("spondon".getBytes())));
        System.out.println(br.readLine());
        pr.println(new String(Base64.getEncoder().encode("Spondon77".getBytes())));
        System.out.println(br.readLine());
        while (true) {
            switch (state) {
                case 0:
                /*   Scanner gg = new Scanner(System.in);
                    String g = gg.nextLine();
                    if(g.equalsIgnoreCase("HELO")){
                    //String g = "HELO " + localHost.getHostName();
                    pr.println(g + " " + localHost.getHostName());
                    state = 1;*/
                //}

                case 1:
                    Scanner input1 = new Scanner(System.in);
                    String str1 = input1.nextLine();
                    if (str1.startsWith("MAIL FROM:<") && str1.endsWith(">") && str1.contains("@")) {
                        pr.println(str1);
                        String w1 = br.readLine();
                        System.out.println(w1);
                        state = 2;
                    } else {
                        pr.println(str1);
                        String ww3 = br.readLine();
                        System.out.println(ww3);
                        state = 1;
                    }

                case 2:
                    Scanner inp = new Scanner(System.in);
                    int k = inp.nextInt();
                    for (int i = 0; i < k; i++) {
                        Scanner input2 = new Scanner(System.in);
                        String str2 = input2.nextLine();
                        if (str2.startsWith("RCPT TO:<") && str2.endsWith(">") && str2.contains("@")) {
                            pr.println(str2);
                            String w2 = br.readLine();
                            System.out.println(w2);
                            state = 3;
                        } else if (str2.equalsIgnoreCase("RSET")) {
                            pr.println(str2);
                            String ww2 = br.readLine();
                            System.out.println(ww2);
                            state = 1;
                        } else {
                            pr.println(str2);
                            String ww22 = br.readLine();
                            System.out.println(ww22);
                            System.out.println("hi");
                            state = 2;
                        }
                    }

                case 3:
                    Scanner input3 = new Scanner(System.in);
                    String str3 = input3.nextLine();
                    if (str3.equalsIgnoreCase("DATA")) {
                        pr.println(str3);
                        String w3 = br.readLine();
                        System.out.println(w3);
                        state = 4;
                    } else if (str3.equalsIgnoreCase("RSET")) {
                        pr.println(str3);
                        String ww3 = br.readLine();
                        System.out.println(ww3);
                        state = 1;
                    } else {
                        pr.println(str3);
                        String ww3 = br.readLine();
                        System.out.println(ww3);
                        state = 3;
                    }

                case 4:
                    String st = "";
                    while (true) {
                        Scanner input4 = new Scanner(System.in);
                        String str4 = input4.nextLine();
                        st = st + str4;
                        if (str4.equalsIgnoreCase("Goodbye.")) {
                            break;
                        }

                    }
                    pr.println(st);
                    Scanner input5 = new Scanner(System.in);
                    String str5 = input5.nextLine();
                    pr.println(str5);
                    String ww4 = br.readLine();
                    System.out.println(ww4);
                    state = 5;

                case 5:
                    Scanner input6 = new Scanner(System.in);
                    String str6 = input6.nextLine();
                    if (str6.equalsIgnoreCase("QUIT")) {
                        pr.println(str6);
                        String ww5 = br.readLine();
                        System.out.println(ww5);
                        pr.close();
                        br.close();
                        s.close();
                        //state = 0;
                        break;
                    }
            }

        }

        /*System.out.print("MAIL FROM:<");
        Scanner input1 = new Scanner(System.in);
        String str1 = input1.nextLine();
        if (str1.endsWith("@gmail.com>") || str1.endsWith("@yahoo.com>")) {
            pr.println("MAIL FROM:<" + str1);
            String w1 = br.readLine();
            System.out.println(w1);

            System.out.print("RCPT TO:<");
            Scanner input2 = new Scanner(System.in);
            String str2 = input2.nextLine();
            pr.println("RCPT TO:<" + str2);
            String w2 = br.readLine();
            System.out.println(w2);

            Scanner input3 = new Scanner(System.in);
            String str3 = input3.nextLine();

            if (str3.equalsIgnoreCase("DATA")) {
                pr.println("DATA");
                String w3 = br.readLine();
                System.out.println(w3);
                /*String s = "";
                while (true) {
                    Scanner input4 = new Scanner(System.in);
                    String str4 = input4.nextLine();
                    s = s + str4;
                    if (str4.equalsIgnoreCase(".")) {
                        pr.println(s);
                        System.out.println(s);
                        String w4 = br.readLine();
                        System.out.println(w4);
                        break;
                    }
                }

            } else {

            }

        }*/
    }
}
