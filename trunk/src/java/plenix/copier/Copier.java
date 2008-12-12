package plenix.copier;

import plenix.copier.destination.Destination;
import plenix.copier.source.Source;
import plenix.copier.transformer.Transformer;

public class Copier<E> {
	private Source<E> source;
	private Destination<E> destination;
	private Transformer<E> transformer;

	public void copy() throws Exception {
		source.open();
		try {
			destination.open();
		} catch (Exception e) {
			source.close();
			throw e;
		}

		try {
			E element;
			while ((element = source.get()) != null) {
				if (transformer != null) {
					element = transformer.transform(element);
				}
				destination.put(element);
			}
		} finally {
		    try { source.close(); } catch (Exception e) {}
            try { destination.close(); } catch (Exception e) {}
		}
	}

	public Destination<E> getDestination() {
		return destination;
	}

	public void setDestination(Destination<E> destination) {
		this.destination = destination;
	}

	public Source<E> getSource() {
		return source;
	}

	public void setSource(Source<E> source) {
		this.source = source;
	}

	public Transformer<E> getTransformer() {
		return transformer;
	}

	public void setTransformer(Transformer<E> transformer) {
		this.transformer = transformer;
	}
}
