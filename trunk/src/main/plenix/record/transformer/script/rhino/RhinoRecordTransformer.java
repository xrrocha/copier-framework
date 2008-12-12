package plenix.record.transformer.script.rhino;

import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

import plenix.copier.transformer.InSituTransformer;
import plenix.record.Record;

public class RhinoRecordTransformer implements InSituTransformer<Record> {
	RhinoScriptSpec scriptSpec;
	private String recordName = "record";

	private RhinoScriptSpec prologScriptSpec;
	private Map<String, Object> objectModel;

	public RhinoRecordTransformer() {}
	public RhinoRecordTransformer(RhinoScriptSpec scriptSpec) {
		this.scriptSpec = scriptSpec;
	}
	public RhinoRecordTransformer(RhinoScriptSpec scriptSpec, String recordName) {
		this.scriptSpec = scriptSpec;
		this.recordName = recordName;
	}
	public RhinoRecordTransformer(RhinoScriptSpec scriptSpec, RhinoScriptSpec prologScriptSpec, Map<String, Object> objectModel, String recordName) {
		this.scriptSpec = scriptSpec;
		this.recordName = recordName;
		this.prologScriptSpec = prologScriptSpec;
		this.objectModel = objectModel;
	}

	private ContextFactory contextFactory = new ContextFactory();
	private Scriptable prologScriptObject;
	private Scriptable getPrologScriptObject() {
		if (prologScriptObject == null) {
			contextFactory.call(new ContextAction() {
				@Override
				public Object run(Context context) {
					prologScriptObject = context.initStandardObjects(null, true);
					prologScriptObject.setParentScope(null);
					if (objectModel != null) {
						for (String name: objectModel.keySet()) {
							prologScriptObject.put(name, prologScriptObject, objectModel.get(name));
						}
					}
					if (prologScriptSpec.getText() != null) {
						context.evaluateString(prologScriptObject,
					                           prologScriptSpec.getText(),
					                           prologScriptSpec.getName(),
					                           prologScriptSpec.getLineNumber(),
					                           null);
					}

					return null;
				}
			});
		}
		return prologScriptObject;
	}
	
	public void transform(final Record record) throws Exception {
		contextFactory.call(new ContextAction() {
			@Override
			public Object run(Context context) {
				Scriptable scriptObject = context.newObject(getPrologScriptObject());
				Scriptable recordObject = context.newObject(scriptObject);
				for (String name: record.fieldNames()) {
					scriptObject.put(name, recordObject, record.getField(name));
				}
				scriptObject.put("record", scriptObject, recordObject);
				context.evaluateString(scriptObject,
						               scriptSpec.getText(),
						               scriptSpec.getName(),
						               scriptSpec.getLineNumber(),
						               null);
				record.clear();
				for (Object id: recordObject.getIds()) {
					String name = id.toString();
					record.setField(name, recordObject.get(name, recordObject));
				}
				return null;
			}
		});
	}
	public RhinoScriptSpec getScriptSpec() {
		return scriptSpec;
	}
	public void setScriptSpec(RhinoScriptSpec scriptSpec) {
		this.scriptSpec = scriptSpec;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public RhinoScriptSpec getPrologScriptSpec() {
		return prologScriptSpec;
	}
	public void setPrologScriptSpec(RhinoScriptSpec prologScriptSpec) {
		this.prologScriptSpec = prologScriptSpec;
	}
	public Map<String, Object> getObjectModel() {
		return objectModel;
	}
	public void setObjectModel(Map<String, Object> objectModel) {
		this.objectModel = objectModel;
	}
}
/*
ScriptEngineManager manager = new ScriptEngineManager();
SimpleNamespace namespace = new SimpleNamespace();
namespace.put("inputRecord", inputRecord);
Record outputRecord = new Record();
namespace.put("outputRecord", outputRecord);
engine.setNamespace(namespace, ScriptContext.ENGINE_SCOPE);
engine.eval(script);
return outputRecord;
*/

