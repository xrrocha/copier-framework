package plenix.copier;

import java.util.logging.Level;
import java.util.logging.Logger;

import plenix.copier.destination.Destination;
import plenix.copier.source.Source;
import plenix.copier.transformer.Transformer;

public class Copier<E> {
    private static Logger logger = Logger.getLogger(Copier.class.getName());
    
	private Source<E> source;
	private Destination<E> destination;
	private Transformer<E> transformer;
	private boolean stopOnError = false;

	public void copy() throws Exception {
	    try {
	        if (logger.isLoggable(Level.FINE)) logger.fine("Opening copier source");
	        source.open();
	    } catch (Throwable t) {
	        logger.severe("Error opening copier source: " + t);
	        throw new CopierException("Error opening copier source", t);
	    }

		try {
            if (logger.isLoggable(Level.FINE)) logger.fine("Opening copier destination");
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
			for (count = 0; (element = source.get()) != null; count++) {
				if (transformer != null) {
	                try {
	                    element = transformer.transform(element);
	                } catch (Throwable t) {
	                    if (stopOnError) {
	                        logger.severe("Error transforming record #" + count + ": " + t);
	                        throw new CopierException("Error transforming record #" + count, t);
	                    } else {
	                        logger.warning("Error transforming record #" + count + ": " + t);
	                        continue;
	                    }
	                }
				}
				try {
	                destination.put(element);
				} catch (Throwable t) {
				    if (stopOnError) {
	                    logger.severe("Error putting record #" + count + ": " + t);
	                    throw new CopierException("Error putting record #" + count, t);
				    } else {
	                    logger.warning("Error putting record #" + count + ": " + t);
				    }
				}
			}
            if (logger.isLoggable(Level.FINE)) logger.fine("Processed " + count + " records");
		} finally {
		    try {
	            if (logger.isLoggable(Level.FINE)) logger.fine("Closing copier source");
		        source.close();
		    } catch (Throwable t) {
		        logger.warning("Error closing copier source: " + t);
		    }
            try {
                if (logger.isLoggable(Level.FINE)) logger.fine("Closing copier destination");
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

    public boolean isStopOnError() {
        return stopOnError;
    }

    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }
}
