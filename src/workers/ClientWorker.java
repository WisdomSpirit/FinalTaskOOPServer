package workers;

import commands.ICommand;
import serializator.ISerializable;
import serializator.Serializator;
import factory.Factory;
import server.Connection;
import thread_dispatcher.ThreadedTask;


public class ClientWorker extends ThreadedTask {
    private Connection client;

    public ClientWorker(Connection client){
        this.client = client;
    }

    @Override
    public void runTask() throws WorkerException {
        try {
            ISerializable packet = Serializator.deserialize(client.read());
            ICommand command = Factory.recogniseCommand(packet);
            ISerializable response = command.execute();
            client.send(Serializator.serialize(response).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fell");
//            throw new WorkerException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public String toString() {
        return "Client";
    }
}
