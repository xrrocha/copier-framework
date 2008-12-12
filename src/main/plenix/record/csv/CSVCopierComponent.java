package plenix.record.csv;

import plenix.record.util.FieldSpecHolder;
import au.com.bytecode.opencsv.CSVWriter;

public abstract class CSVCopierComponent extends FieldSpecHolder {
	private char separator = '\t';
	private boolean headerRecord = false;
	private char quote = CSVWriter.NO_QUOTE_CHARACTER;

	public boolean isHeaderRecord() {
		return headerRecord;
	}
	public void setHeaderRecord(boolean headerRecord) {
		this.headerRecord = headerRecord;
	}
	public char getQuote() {
		return quote;
	}
	public void setQuote(char quote) {
		this.quote = quote;
	}
	public char getSeparator() {
		return separator;
	}
	public void setSeparator(char separator) {
		this.separator = separator;
	}

}
