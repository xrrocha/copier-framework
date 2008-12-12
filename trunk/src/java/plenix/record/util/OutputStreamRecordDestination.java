package plenix.record.util;

import plenix.copier.destination.Destination;
import plenix.record.Record;
import plenix.util.io.OutputStreamSource;

public abstract class OutputStreamRecordDestination extends FieldSpecHolder implements Destination<Record> {
    private OutputStreamSource outputStreamSource;

    public OutputStreamSource getOutputStreamSource() {
        return outputStreamSource;
    }

    public void setOutputStreamSource(OutputStreamSource outputStreamSource) {
        this.outputStreamSource = outputStreamSource;
    }
}
