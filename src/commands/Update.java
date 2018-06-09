package commands;

import packets.ErrorPacket;
import packets.FilePacket;
import packets.update.InputPacket;
import packets.update.OutputPacket;
import providers.DataProvider;
import providers.ProviderException;
import serializator.ISerializable;

import java.io.File;
import java.io.IOException;

public class Update implements ICommand {
    private int id;

    Update(InputPacket packet){
        this.id = packet.id;
    }
    @Override
    public ISerializable execute() {
        try {
            FilePacket[] files = DataProvider.getLatestVersionFilesById(id);
            OutputPacket packet = new OutputPacket();
            packet.files = files;
            return packet;
        } catch (IOException e) {
            ErrorPacket error = new ErrorPacket();
            error.error = "Some IOException was occurred while handling Your query!";
            error.errorNumber = 2;
            return error;
        } catch (providers.ProviderException e) {
            ErrorPacket error = new ErrorPacket();
            error.errorNumber = 6;
            error.error = "Some ProviderException was occurred while handling Your query!";
            return error;
        }
    }
}
