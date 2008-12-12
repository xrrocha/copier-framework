package plenix.record.xbase;

import java.io.File;

import plenix.copier.destination.Destination;
import plenix.record.Record;

import com.linuxense.javadbf.DBFWriter;

public class XBaseRecordDestination extends XBaseCopierComponent implements Destination<Record> {
	private String[] fieldNames;
	
	private DBFWriter writer;
	
	public void open() throws Exception {
		writer = new DBFWriter(new File(getFilename()));
	}

	public void put(Record record) throws Exception {
		Object[] fields = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			fields[i] = record.getField(fieldNames[i]);
		}
		writer.addRecord(fields);
	}

	public void close() throws Exception {
		writer.write();
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}
}
