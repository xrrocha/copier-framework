package plenix.copier.transformer;

public class InSituTransformerAdapter<E> implements Transformer<E> {
    private InSituTransformer<E> inSituTransformer;
    
    public InSituTransformerAdapter() {}
    
    public InSituTransformerAdapter(InSituTransformer<E> inSituTransformer) {
        this.inSituTransformer = inSituTransformer;
    }
    
    public E transform(E element) throws Exception {
        inSituTransformer.transform(element);
        return element;
    }

    public InSituTransformer<E> getInSituTransformer() {
        return inSituTransformer;
    }

    public void setInSituTransformer(InSituTransformer<E> inSituTransformer) {
        this.inSituTransformer = inSituTransformer;
    }
}
