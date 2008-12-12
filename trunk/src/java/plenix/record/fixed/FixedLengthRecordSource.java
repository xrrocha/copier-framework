package plenix.record.fixed;

import java.io.InputStreamReader;

import plenix.copier.source.Source;
import plenix.record.Record;
import plenix.record.util.FixedFieldSpec;
import plenix.util.io.InputStreamSource;

public class FixedLengthRecordSource extends FixedLengthCopierComponent implements Source<Record> {
    private int length;
    private boolean trim = true;
    private InputStreamSource inputStreamSource;
    
    private InputStreamReader in;

    @Override
    public void open() throws Exception {
        in = new InputStreamReader(inputStreamSource.getInputStream());
    }

    @Override
    public Record get() throws Exception {
        int charCount;
        char[] buffer = new char[length];
        if ((charCount = in.read(buffer)) > 0) {
            Record record = new Record();
            String line = new String(buffer, 0, charCount);
            for (int i = 0; i < super.getFixedFields().size(); i++) {
                FixedFieldSpec fixedFieldSpec = (FixedFieldSpec) getFixedFields().get(i);
                if (fixedFieldSpec.getOffset() >= line.length()) {
                    break;
                }
                
                int endPos = fixedFieldSpec.getOffset() + fixedFieldSpec.getLength();
                if (endPos > line.length()) {
                    endPos = line.length();
                }
                
                String fieldString = line.substring(fixedFieldSpec.getOffset(), endPos);
                if (trim) {
                    fieldString = fieldString.trim();
                }
                
                record.setField(fixedFieldSpec.getName(), fixedFieldSpec.parse(fieldString));
            }
            return record;
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        in.close();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    public InputStreamSource getInputStreamSource() {
        return inputStreamSource;
    }

    public void setInputStreamSource(InputStreamSource outputStreamSource) {
        this.inputStreamSource = outputStreamSource;
    }
}
