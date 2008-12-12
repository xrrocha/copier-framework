package plenix.record.csv;

import java.io.InputStreamReader;

import plenix.copier.source.Source;
import plenix.record.Record;
import plenix.util.io.InputStreamSource;
import au.com.bytecode.opencsv.CSVReader;

public class CSVRecordSource extends CSVCopierComponent implements Source<Record> {
    private InputStreamSource inputStreamSource;
    private CSVReader reader;

    public void open() throws Exception {
        int lineCount = 0;
        if (isHeaderRecord()) {
            lineCount = 1;
        }
        reader = new CSVReader(new InputStreamReader(inputStreamSource.getInputStream()), getSeparator(), getQuote(), lineCount);
    }

    public Record get() throws Exception {
        String[] fields = reader.readNext();
        if (fields == null) {
            return null;
        }

        Record record = new Record();
        Object[] recordValues = new Object[getFields().size()];
        for (int i = 0; i < getFields().size(); i++) {
            if (fields[i].length() != 0) {
                recordValues[i] = getFields().get(i).parse(fields[i]);
                if (recordValues[i] != null) {
                    record.setField(getFields().get(i).getName(), recordValues[i]);
                }
            }
        }
        return record;
    }

    public void close() throws Exception {
        reader.close();
    }

    public InputStreamSource getInputStreamSource() {
        return inputStreamSource;
    }

    public void setInputStreamSource(InputStreamSource inputStreamSource) {
        this.inputStreamSource = inputStreamSource;
    }
}
