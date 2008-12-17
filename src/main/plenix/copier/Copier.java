package plenix.copier;

import java.util.logging.Logger;

import plenix.copier.destination.Destination;
import plenix.copier.source.Source;
import plenix.copier.transformer.Transformer;

public class Copier<E> {
    private static Logger logger = Logger.getLogger(Copier.class.getName());
    
	private Source<E> source;
	private Destination<E> destination;
	private Transformer<E> transformer;

	public void copy() throws Exception {
	    try {
	        source.open();
	    } catch (Throwable t) {
	        logger.severe("Error opening copier source: " + t);
	        throw new CopierException("Error opening copier source", t);
	    }

		try {
			destination.open();
		} catch (Throwable t) {
		    logger.severe("Error opening copier destination: " + t);
			try {
			    source.close();
			} catch (Throwable et) {
	            logger.warning("Error closing copier source: " + t);
			}
			throw new CopierException("Error opening copier destination", t);
		}

		try {
			E element;
            int count;
			for (count = 1; (element = source.get()) != null; count++) {
				if (transformer != null) {
				    // TODO Handle transformation errors
					element = transformer.transform(element);
				}
				try {
	                destination.put(element);
				} catch (Throwable t) {
				    logger.warning("Error putting record #" + count + ": " + t);
				}
			}
		} finally {
		    try {
		        source.close();
		    } catch (Throwable t) {
		        logger.warning("Error closing copier source: " + t);
		    }
            try {
                destination.close();
            } catch (Throwable t) {
                logger.warning("Error closing copier destination: " + t);
            }
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
