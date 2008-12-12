package plenix.copier.destination;

import plenix.util.io.OutputStreamSource;

public abstract class OutputStreamCopierDestination<E> implements Destination<E> {
    private OutputStreamSource outputStreamSource;

    public OutputStreamSource getOutputStreamSource() {
        return outputStreamSource;
    }

    public void setOutputStreamSource(OutputStreamSource outputStreamSource) {
        this.outputStreamSource = outputStreamSource;
    }
}
