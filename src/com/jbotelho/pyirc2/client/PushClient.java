package com.jbotelho.pyirc2.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class PushClient {
    protected Socket socket;
    OutputStream out;
    Gson gson;

    public PushClient(Socket socket) {
        this.socket = socket;
        this.gson = new GsonBuilder().create();
        try {
            this.out = this.socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    // TODO have a queue and a consumer for this
    public synchronized boolean sendObject(Object obj, Class cls) {
        String data = gson.toJson(obj, cls);
        try {
            out.write(ByteBuffer.allocate(4).putInt(data.length()).array());
            out.write(data.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }
}
