package plenix.record.fixed;

import java.io.OutputStreamWriter;

import plenix.copier.destination.Destination;
import plenix.record.Record;
import plenix.record.util.FixedFieldSpec;
import plenix.util.io.OutputStreamSource;

public class FixedLengthRecordDestination extends FixedLengthCopierComponent implements Destination<Record> {
    private int length;
    private char blankChar = ' ';
    private String recordTerminator = null;
    private OutputStreamSource outputStreamSource;
    
    private OutputStreamWriter out;
    
    @Override
    public void open() throws Exception {
        out = new OutputStreamWriter(outputStreamSource.getOutputStream());
    }

    @Override
    public void put(Record record) throws Exception {
        char[] array = newArray();
        for (int i = 0; i < getFixedFields().size(); i++) {
            FixedFieldSpec fixedFieldSpec = (FixedFieldSpec) getFixedFields().get(i);
            String fieldString = fixedFieldSpec.format(record.getField(fixedFieldSpec.getName()));
            if (fieldString != null) {
                paste(fieldString, array, fixedFieldSpec.getOffset(), fixedFieldSpec.getLength());
            }
        }
        out.write(array);
        if (recordTerminator != null) {
            out.write(recordTerminator);
        }
        out.flush();
    }

    @Override
    public void close() throws Exception {
        out.close();
    }
    
    private char[] newArray() {
        char[] array = new char[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = blankChar;
        }
        return array;
    }
    
    private void paste(String string, char[] array, int offset, int length) {
        if (offset + length > array.length) {
            length = array.length - offset;
        }
        if (string.length() > length) {
            string = string.substring(0, length);
        }
        for (int i = offset, pos = 0; pos < length; i++, pos++) {
            array[i] = string.charAt(pos);
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public char getBlankChar() {
        return blankChar;
    }

    public void setBlankChar(char blankChar) {
        this.blankChar = blankChar;
    }

    public String getRecordTerminator() {
        return recordTerminator;
    }

    public void setRecordTerminator(String recordTerminator) {
        this.recordTerminator = recordTerminator;
    }

    public OutputStreamSource getOutputStreamSource() {
        return outputStreamSource;
    }

    public void setOutputStreamSource(OutputStreamSource outputStreamSource) {
        this.outputStreamSource = outputStreamSource;
    }
}
