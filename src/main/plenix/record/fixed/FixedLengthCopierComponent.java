package plenix.record.fixed;

import java.util.Collections;
import java.util.List;

import plenix.record.util.FixedFieldSpec;

public abstract class FixedLengthCopierComponent {
    private List<FixedFieldSpec> fixedFields;

    public List<FixedFieldSpec> getFixedFields() {
        if (fixedFields == null) {
            return Collections.emptyList();
        }
        return fixedFields;
    }

    public void setFixedFields(List<FixedFieldSpec> fixedFields) {
        this.fixedFields = fixedFields;
    }
}
