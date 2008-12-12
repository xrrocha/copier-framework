package plenix.record.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FieldSpec {

    public enum Type {
      STRING, NUMBER, DATE  
    }

    private String name;
    private Type type = Type.STRING;
    private String formatString;
    private Format format;

    public FieldSpec() {}
    
    public FieldSpec(String name, Type type, String formatString) {
        this.name = name;
        this.type = type;
        this.formatString = formatString;
    }

    public String format(Object object) {
        if (object == null) {
            return null;
        }
        if (formatString == null) {
            return object.toString();
        } else if (format == null) {
            switch (type) {
                case STRING:
                    format = new MessageFormat(formatString);
                    break;
                case NUMBER:
                    format = new DecimalFormat(formatString);
                    break;
                case DATE:
                    format = new SimpleDateFormat(formatString);
                    break;
            }
        }
        return format.format(object);
    }

    public Object parse(String string) throws ParseException {
        if (string == null) {
            return null;
        }
        switch (type) {
            case STRING:
                if (formatString == null) {
                    return string;
                } else if (format == null) {
                    format = new MessageFormat(formatString);
                }
                return ((MessageFormat) format).parse(string)[0];
            case NUMBER:
                if (formatString == null) {
                    string = string.replaceAll("/[^0-9.]/", "");
                    return Double.parseDouble(string);
                } else if (format == null) {
                    format = new DecimalFormat(formatString);
                }
                return format.parseObject(string);
            case DATE:
                if (formatString == null) {
                    formatString = "MM/dd/yyyy";
                    format = new SimpleDateFormat(formatString);
                } else if (format == null) {
                    format = new SimpleDateFormat(formatString);
                }
                // Hack to coexist with certain JDBC drivers
                java.util.Date date = ((DateFormat) format).parse(string);
                return new java.sql.Date(date.getTime());
        }
        throw new IllegalStateException("Invalid data type: " + type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public String getFormatString() {
        return formatString;
    }

}