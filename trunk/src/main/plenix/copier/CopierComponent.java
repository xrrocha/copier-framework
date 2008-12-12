package plenix.copier;

public interface CopierComponent {
	public void open() throws Exception;
	public void close() throws Exception; // Idempotent
}
