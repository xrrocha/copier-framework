package plenix.record.csv;

import java.io.OutputStreamWriter;

import plenix.copier.destination.Destination;
import plenix.record.Record;
import plenix.util.io.OutputStreamSource;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVRecordDestination extends CSVCopierComponent implements Destination<Record> {
    private OutputStreamSource outputStreamSource;
	private CSVWriter writer;

    public void open() throws Exception {
	     writer = new CSVWriter(new OutputStreamWriter(outputStreamSource.getOutputStream()), getSeparator(), getQuote());
	     if (isHeaderRecord()) {
	    	 String[] headers = new String[getFields().size()];
	    	 for (int i = 0; i < getFields().size(); i++) {
	    		 headers[i] = getFields().get(i).getName();
	    	 }
	    	 writer.writeNext(headers);
	     }
	}

	public void put(Record record) throws Exception {
		String[] csvValues = new String[getFields().size()];
		Object[] recordValues = new Object[getFields().size()];
		
		for (int i = 0; i < getFields().size(); i++) {
			recordValues[i] = getFields().get(i).format(record.getField(getFields().get(i).getName()));

			if (recordValues[i] == null) {
				recordValues[i] = "";
			}
		}

		writer.writeNext(csvValues);
	}

	public void close() throws Exception {
		writer.close();
	}

    public OutputStreamSource getOutputStreamSource() {
        return outputStreamSource;
    }

    public void setOutputStreamSource(OutputStreamSource outputStreamSource) {
        this.outputStreamSource = outputStreamSource;
    }
}
