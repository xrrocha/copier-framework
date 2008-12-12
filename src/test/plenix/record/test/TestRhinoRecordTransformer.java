package plenix.record.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import plenix.copier.transformer.InSituTransformer;
import plenix.record.Record;
import plenix.record.transformer.script.rhino.RhinoRecordTransformer;
import plenix.record.transformer.script.rhino.RhinoScriptSpec;

public class TestRhinoRecordTransformer {
	public static void main(String[] args) throws Exception {
		String scriptName = "recordTransformerScript";
		String scriptText = "count++\n" +
		                    "record.gender = 'Male'\n" +
		                    "out.println('Age: ' + record.age)\n" +
		                    "delete record.age\n" + 
		                    "out.println('Now is: ' + now + ' and count is: ' + count)";
		int scriptLineNumber = 1;
		RhinoScriptSpec scriptSpec = new RhinoScriptSpec(scriptText, scriptName, scriptLineNumber);
		
		String prologScriptName = "prologScript";
		String prologScriptText = "var out = java.lang.System.out\n" +
		                          "out.println('Now is: ' + now + ' and count is: ' + count)";
		int prologScriptLineNumber = 1;
		RhinoScriptSpec prologScriptSpec = new RhinoScriptSpec(prologScriptText, prologScriptName, prologScriptLineNumber);
		
		Record record = new Record();
		record.setField("fname", "Ricardo");
		record.setField("lname", "Rocha");
		record.setField("age", 47);
		
		Map<String, Object> objectModel = new HashMap<String, Object>();
		objectModel.put("now", new Date());
		objectModel.put("count", new Integer(0));
		
		String recordName = "record";

		InSituTransformer<Record> transformer = new RhinoRecordTransformer(scriptSpec, prologScriptSpec, objectModel, recordName);
		transformer.transform(record);
		transformer.transform(record);
		System.out.println(record);
	}
}
