package plenix.record.util;

import java.util.Collections;
import java.util.List;

public abstract class FieldSpecHolder {
        private List<FieldSpec> fields;

        public List<FieldSpec> getFields() {
            if (fields == null) {
                return Collections.emptyList();
            }
            return fields;
        }

        public void setFields(List<FieldSpec> fields) {
            this.fields = fields;
        }
}
