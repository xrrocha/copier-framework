package plenix.copier.destination;

import java.util.List;

public class BroadcastingDestination<E> implements Destination<E> {
    private List<Destination<E>> targets;

    @Override
    public void open() throws Exception {
        for (int i = 0; i < targets.size(); i++) {
            try {
                targets.get(i).open();
            } catch (Exception e) {
                for (int j = 0; j < i; j++) {
                    try { targets.get(j).close(); } catch (Exception x) {}
                }
                throw e;
            }
        }
    }

    @Override
    public void put(E element) throws Exception {
        for (int i = 0; i < targets.size(); i++) {
            targets.get(i).put(element);
        }
    }

    @Override
    public void close() throws Exception {
        for (int i = 0; i < targets.size(); i++) {
            try { targets.get(i).close(); } catch (Exception e) {}
        }
    }

    public List<Destination<E>> getTargets() {
        return targets;
    }

    public void setTargets(List<Destination<E>> targets) {
        this.targets = targets;
    }
}
