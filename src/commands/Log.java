package commands;

import org.javatuples.Quartet;
import org.javatuples.Triplet;
import packets.ErrorPacket;
import serializator.ISerializable;
import packets.log.*;
import server.Logs;

import java.util.ArrayList;
import java.util.Date;

public class Log implements ICommand {
    private int id;

    public Log(InputPacket packet){
        this.id = packet.id;
    }

    @Override
    public ISerializable execute() {
        try {
            StringBuilder res = new StringBuilder();
            OutputPacket packet = new OutputPacket();
            ArrayList<Triplet<Date, ArrayList<String>, ArrayList<String>>> history = Logs.logs.get(id);
            for (Triplet<Date, ArrayList<String>, ArrayList<String>> el : history){
                res.append("at ").append(el.getValue0().toString()).append(" ").append("\n");
                for (String s: el.getValue1())
                    res.append("\tAdded: ").append(s).append("\n");
                for (String s: el.getValue2())
                    res.append("\tDeleted: ").append(s).append("\n");
            }

            Logs.history.put(id, "Log");
            packet.result = res.toString();
            return packet;
        } catch (Exception e){
            ErrorPacket error = new ErrorPacket();
            error.error = "Some unexpected Exception was occurred while handling Your query!";
            error.errorNumber = 4;
            return error;
        }
    }
}
