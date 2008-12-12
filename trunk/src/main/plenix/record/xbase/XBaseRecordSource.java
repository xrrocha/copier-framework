package plenix.record.xbase;

import java.io.FileInputStream;
import java.io.InputStream;

import plenix.copier.source.Source;
import plenix.record.Record;

import com.linuxense.javadbf.DBFReader;

public class XBaseRecordSource extends XBaseCopierComponent implements Source<Record> {
	private InputStream is;
	private DBFReader reader;

	public void open() throws Exception {
		is = new FileInputStream(getFilename());
		reader = new DBFReader(is);
	}
	
	public Record get() throws Exception {
		Object[] fields = reader.nextRecord();
		if (fields == null) {
			return null;
		}
		Record record = new Record();
		for (int i = 0; i < fields.length; i++) {
			record.setField(reader.getField(i).getName(), fields[i]);
		}
		return record;
	}

	public void close() throws Exception {
		is.close();
	}
}
