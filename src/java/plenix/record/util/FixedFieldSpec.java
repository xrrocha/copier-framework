package plenix.record.util;

public class FixedFieldSpec extends FieldSpec {
    
    public FixedFieldSpec() {
        super();
    }
    
    public FixedFieldSpec(String name, Type type, String formatString) {
        super(name, type, formatString);
    }
    private int offset;
    private int length;
    
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
}
