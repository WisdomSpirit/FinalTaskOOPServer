package commands;

import serializator.ISerializable;

public interface ICommand {
    ISerializable execute();
}
