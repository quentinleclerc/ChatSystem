package machineState;

import java.awt.*;

public abstract class State {

    public abstract State handleEvent(Event e);
}
