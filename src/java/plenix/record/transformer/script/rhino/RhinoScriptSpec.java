package plenix.record.transformer.script.rhino;

public class RhinoScriptSpec {
	private String text = null;
	private String name = "script";
	private int lineNumber = 1;
	
	public RhinoScriptSpec() {}
	public RhinoScriptSpec(String text) {
		this.text = text;
	}
	public RhinoScriptSpec(String text, String scriptName) {
		this.text = text;
		this.name = scriptName;
	}
	public RhinoScriptSpec(String text, String scriptName, int lineNumber) {
		this.text = text;
		this.name = scriptName;
		this.lineNumber = lineNumber;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
