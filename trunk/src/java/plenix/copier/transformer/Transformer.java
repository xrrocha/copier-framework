package plenix.copier.transformer;


public interface Transformer<E> {
	public E transform(E element) throws Exception;
}
