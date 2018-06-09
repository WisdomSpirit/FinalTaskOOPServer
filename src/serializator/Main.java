package serializator;

import commands.ICommand;
import factory.Factory;
import packets.ErrorPacket;
import packets.FilePacket;
import packets.clone.*;
import server.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
//        AnotherPackage p = new AnotherPackage();
//        String serialised = Serializator.serialize(p);
//        System.out.println(serialised);
//        AnotherPackage res = Serializator.deserialize(serialised.getBytes("UTF-8"));
//        System.out.println(Arrays.toString(res.getClass().getDeclaredFields()));
//        System.out.println(res.command);
//        System.out.println(res.p.pBoolean);
//        System.out.println(res.p.pByte);
//        System.out.println(res.p.pChar);
//        System.out.println(res.p.pDouble);
//        System.out.println(res.p.pInt);
//        System.out.println(res.p.pLong);
//        System.out.println(res.p.pString);
//        System.out.println(res.version);
//        System.out.println(res.masha);

//        FilePacket[] files = new FilePacket[2];
//
//        System.out.println(files.getClass());
        System.out.println(String.format("Couldn't create File with name %s", "Adadasd"));
        InputPacket p = new InputPacket();
        p.repoName = "Masha";
        p.id = 558855;
//        p.login = "Masha";
//        p.password = "Smorodina";
        ICommand command = Factory.recogniseCommand(p);
        ISerializable response = command.execute();
        String serialised = Serializator.serialize(response);
        System.out.println(serialised);
        ISerializable packet = Serializator.deserialize(serialised.getBytes("UTF-8"));
        Class clazz = packet.getClass();
        if (clazz == OutputPacket.class) {
            OutputPacket pack = (OutputPacket) packet;
            System.out.println(packet.getCommand());
            System.out.println(Arrays.toString(pack.files));
            for (FilePacket f : pack.files) {
                System.out.println(Arrays.toString(f.rawFile));
                System.out.println(f.fileName);
            }
        }
        else {
            ErrorPacket error = (ErrorPacket) packet;
            System.out.println(error.error);
            System.out.println(error.errorNumber);
        }
    }
}