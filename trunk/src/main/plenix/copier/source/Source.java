package plenix.copier.source;

import plenix.copier.CopierComponent;


public interface Source<E> extends CopierComponent {
	public E get() throws Exception;
}
