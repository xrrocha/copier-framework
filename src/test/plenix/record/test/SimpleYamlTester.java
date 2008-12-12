package plenix.record.test;

import static plenix.record.util.FieldSpec.Type.STRING;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.yamlbeans.YamlReader;
import net.sourceforge.yamlbeans.YamlWriter;
import plenix.copier.Copier;
import plenix.copier.transformer.InSituTransformerAdapter;
import plenix.record.Record;
import plenix.record.csv.CSVRecordDestination;
import plenix.record.transformer.script.rhino.RhinoRecordTransformer;
import plenix.record.transformer.script.rhino.RhinoScriptSpec;
import plenix.record.util.FieldSpec;
import plenix.record.xbase.XBaseRecordSource;
import plenix.util.io.FileOutputStreamSource;

public class SimpleYamlTester {
    public static void main(String[] args) throws Exception {
		if (false) {
			Copier<Record> copier = new Copier<Record>();
			XBaseRecordSource recordProducer = new XBaseRecordSource();
			RhinoRecordTransformer recordTransformer = new RhinoRecordTransformer();
			CSVRecordDestination recordConsumer = new CSVRecordDestination();
			copier.setSource(recordProducer);
			copier.setTransformer(new InSituTransformerAdapter<Record>(recordTransformer));
			copier.setDestination(recordConsumer);
			
			recordProducer.setFilename("paises.dbf");

			recordTransformer.setScriptSpec(new RhinoScriptSpec(loadFile("dbf2csv.js"), "recordTransformerScript", 1));
			recordTransformer.setPrologScriptSpec(new RhinoScriptSpec(loadFile("dbf2csv-prolog.js"), "recordPrologScript", 1));
			
			recordConsumer.setOutputStreamSource(new FileOutputStreamSource("paises.txt"));
			recordConsumer.setSeparator('\t');
			List<FieldSpec> fieldSpecs = new ArrayList<FieldSpec>();
			Collections.addAll(fieldSpecs, new FieldSpec[] {
                    new FieldSpec("COD_PAIS", STRING, null),
                    new FieldSpec("NMBRE", STRING, null),
                    new FieldSpec("CNTNNTE", STRING, null),
                });
			recordConsumer.setFields(fieldSpecs);

			copier.copy();
			
			YamlWriter writer = new YamlWriter(new FileWriter("out-dbf2csv.yaml"));
			writer.write(copier);
			writer.write(recordProducer);
			writer.write(recordTransformer);
			writer.write(recordConsumer);
			writer.close();
		}
		
		if (true) {
			YamlReader reader = new YamlReader(new FileReader("in-dbf2csv.yaml"));
			@SuppressWarnings("unchecked")
			Copier<Record> copier = (Copier<Record>) reader.read(Copier.class);
			copier.copy();
		}
		
		System.out.println("Done!");
	}
	
	private static final int BUFFER_SIZE = 4096;
	private static String loadFile(String filename) throws IOException {
		int cnt;
		char[] buffer = new char[BUFFER_SIZE];
		StringBuilder builder = new StringBuilder();
		FileReader in = new FileReader(filename);

		while ((cnt = in.read(buffer)) > 0) {
			builder.append(buffer, 0, cnt);
		}
		return builder.toString();
	}
}
