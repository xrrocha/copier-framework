package plenix.copier.transformer;

public interface InSituTransformer<E> {
    public void transform(E element) throws Exception;
}
