package plenix.record.util;

import plenix.copier.source.Source;
import plenix.record.Record;
import plenix.util.io.InputStreamSource;

public abstract class InputStreamRecordSource extends FieldSpecHolder implements Source<Record> {
    private InputStreamSource inputStreamSource;

    public InputStreamSource getInputStreamSource() {
        return inputStreamSource;
    }

    public void setInputStreamSource(InputStreamSource inputStreamSource) {
        this.inputStreamSource = inputStreamSource;
    }
}
