package plenix.copier.source;

import plenix.util.io.InputStreamSource;

public abstract class InputStreamCopierSource<E> implements Source<E> {
    private InputStreamSource inputStreamSource;

    public InputStreamSource getInputStreamSource() {
        return inputStreamSource;
    }

    public void setInputStreamSource(InputStreamSource inputStreamSource) {
        this.inputStreamSource = inputStreamSource;
    }
}
