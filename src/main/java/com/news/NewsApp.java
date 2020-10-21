package com.news;

import com.news.view.ListenerScreen;
import com.news.view.Screen;
import com.news.view.WriterScreen;

public class NewsApp {

    private static Screen screen;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("The program needs two arguments.\n" +
                    "For example:\n" +
                    "java -jar newsletter.jar listener 8888\n" +
                    "where listener is the your role (writer or listener), " +
                    "and 8888 is the port on which the program will be run.\n" +
                    "Also you can add third parameter - broadcast address." +
                    "If it is not present messages will be send (or listen) to the address 255.255.255.255");
            emergencyExit();
        }
        switch (args[0].toLowerCase()) {
            case "writer":
                try {
                    int port = Integer.parseInt(args[1]);
                    screen = args.length > 2 ?
                            new WriterScreen("Рассылка новостей", port, args[2]) :
                            new WriterScreen("Рассылка новостей", port);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    emergencyExit();
                }
            case "listener":
                try {
                    int port = Integer.parseInt(args[1]);
                    screen = new ListenerScreen("Читать рассылку", port);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    emergencyExit();
                }
            default: {
                emergencyExit();
            }
        }
        screen.start();
    }

    private static void emergencyExit() {
        System.out.println("Incorrect parameters");
        System.exit(-1);
    }
}