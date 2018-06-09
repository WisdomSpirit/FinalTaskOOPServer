package commands;

import packets.ErrorPacket;
import packets.login.InputPacket;
import packets.login.OutputPacket;
import providers.versionProviders.VersionProvider;
import serializator.ISerializable;
import server.Config;
import server.Logs;

import java.util.ArrayList;


public class Login implements ICommand {
    private String login;
    private String password;

    public Login(InputPacket packet){
        login = packet.login;
        password = packet.password;
    }

    @Override
    public ISerializable execute() {
        try {
            if (login != null && password != null) {
                Config config = Config.getInstance();
                OutputPacket packet = new OutputPacket();
                int id = (login + password).hashCode();
                config.versionCounter.put(id, new VersionProvider());
                packet.id = id;
                Logs.history.put(id, "Login");
                Logs.logs.put(id, new ArrayList<>());
                return packet;
            }
            else throw new ExecutionException();
        } catch (ExecutionException e){
            ErrorPacket error = new ErrorPacket();
            error.errorNumber = 0;
            error.error = "Authorization failed! Please, authorise!";
            return error;
        }
    }
}
