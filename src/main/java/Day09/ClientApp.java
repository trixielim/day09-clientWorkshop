package Day09;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp {
    private ClientApp() {

    }

    public static void main(String[] args) throws UnknownHostException, IOException{

        //variable to store keyboard input
        String keyInput = "";
        //variable to store socket return value
        String msgRecv = "";

        // using console to receive input from keyboard
        Console console = System.console();

        // opening a socket to connect to the server on port XXXX
        Socket socket = new Socket("localhost", 1234);

        try(OutputStream os = socket.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            try (InputStream is = socket.getInputStream()){
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                while(!keyInput.equalsIgnoreCase("quit")) {
                    keyInput = console.readLine("Guess a number 0 - 99 (guess XX):");
                    dos.writeUTF(keyInput);
                    dos.flush();

                    msgRecv = dis.readUTF();
                    System.out.println("From server:" + msgRecv);
                }

                bos.close();
                dos.close();
                socket.close();

            } catch(EOFException ex){
                ex.printStackTrace();
                socket.close();
            }
            
        } catch (EOFException e) {
            e.printStackTrace();
            socket.close();
        }
    }
}
