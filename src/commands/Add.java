package commands;

import packets.ErrorPacket;
import packets.add.*;
import providers.DataProvider;
import serializator.ISerializable;
import server.Config;
import server.Logs;

import java.io.File;


public class Add implements ICommand {
    private String repoName;
    private int id;

    public Add(ISerializable packet){
        InputPacket p = (InputPacket) packet;
        this.repoName = p.repoName;
        this.id = p.id;
    }

    @Override
    public ISerializable execute() {
        try {
            File file = DataProvider.getRepo(id, repoName);

            if (file.exists()) {
                OutputPacket packet = new OutputPacket();
                packet.status = "Already exists!";
                return packet;
            } else {
                Boolean done = file.mkdirs();
                if (done) {
                    Config config = Config.getInstance();
                    Logs.history.put(id, "Add");
                    config.repo.put(id, file.getPath());
                    OutputPacket packet = new OutputPacket();
                    packet.status = "Ok, fine";
                    return packet;
                } else {
                    OutputPacket packet = new OutputPacket();
                    packet.status = "Server couldn't create Your repository!";
                    return packet;
                }
            }
        } catch (Exception e){
            ErrorPacket error = new ErrorPacket();
            error.error = "Some unexpected Exception was occurred while handling Your query!";
            error.errorNumber = 4;
            return error;
        }
    }
}
