package plenix.copier.destination;

import plenix.copier.CopierComponent;


public interface Destination<E> extends CopierComponent {
	public void put(E element) throws Exception;
}
