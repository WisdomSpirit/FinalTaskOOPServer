package commands;

import packets.ErrorPacket;
import packets.FilePacket;
import packets.clone.InputPacket;
import packets.clone.OutputPacket;
import providers.DataProvider;
import providers.ProviderException;
import serializator.ISerializable;
import server.Config;
import server.Logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static providers.DataProvider.getLatestVersionFilesById;

public class Clone implements ICommand {
    private String repoName;
    private int id;

    public Clone(InputPacket packet) {
        this.repoName = packet.repoName;
        this.id = packet.id;
    }

    @Override
    public ISerializable execute() {
        try {
            Config config = Config.getInstance();
            File file = DataProvider.getRepo(id, repoName);

            if (!config.repo.containsKey(id)){
                ErrorPacket error = new ErrorPacket();
                error.errorNumber = 0;
                error.error = "Authorization failed! Please, authorise!";
                return error;
            }

            if (!file.isDirectory()){
                ErrorPacket error = new ErrorPacket();
                error.errorNumber = 3;
                error.error = "The file You query is not a Directory! You can only query repositories!";
                return error;
            }

            if (config.currentRepo.containsKey(id))
                config.currentRepo.replace(id, file.getPath());
            else config.currentRepo.put(id, file.getPath());

            FilePacket[] result = getLatestVersionFilesById(id);


            OutputPacket packet = new OutputPacket();
            packet.files = result;
            return packet;
        } catch (FileNotFoundException e) {
            ErrorPacket error = new ErrorPacket();
            error.errorNumber = 1;
            error.error = "Your file was not found while handling Your query!";
            return error;
        } catch (IOException e) {
            ErrorPacket error = new ErrorPacket();
            error.error = "Some IOException was occurred while handling Your query!";
            error.errorNumber = 2;
            return error;
        } catch (ProviderException e) {
            ErrorPacket error = new ErrorPacket();
            error.errorNumber = 6;
            error.error = "Some ProviderException was occurred while handling Your query!";
            return error;
        }
    }
}
