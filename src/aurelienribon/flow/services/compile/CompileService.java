package aurelienribon.flow.services.compile;

import aurelien.contracts.ContractAttributes;
import aurelien.contracts.ContractContext;
import aurelien.contracts.ContractGraph;
import aurelien.contracts.ContractUtils;
import aurelien.utils.GraphUtils;
import aurelienribon.common.GlabUtils;
import aurelienribon.common.TemplateManager;
import aurelienribon.flow.Service;
import aurelienribon.flow.ServiceContext;
import aurelienribon.flow.ServiceExecutionException;
import aurelienribon.flow.models.Model;
import aurelienribon.flow.models.ModelTuple;
import aurelienribon.flow.models.ModelUtils;
import graph.Graph;
import graph.node.Node;
import java.io.File;
import java.io.IOException;
import java.util.List;
import modules.shared.NodeAttr.AttrFx;
import org.apache.commons.io.FileUtils;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class CompileService extends Service {
	@Override
	public void process(ServiceContext ctx) throws ServiceExecutionException {
		ModelTuple mt = ctx.models.getModel(ctx.input);
		if (mt == null) exit("Model '" + ctx.input + "' not found");

		compile(mt.getModel(), mt.getConstraintName(), ctx.models.getScriptFile());
	}

	private void compile(Model model, String constraintName, File scriptFile) throws ServiceExecutionException {
		ModelUtils.clearResult(model, constraintName);

		log("========================================================\n");
		log("==== Compilation -- " + model.getHumanReadableName(constraintName) + "\n");
		log("========================================================");

		String latency = ModelUtils.getConstraint(model.getConstraintsFile(constraintName), "latency");
		String alloc = ModelUtils.getConstraint(model.getConstraintsFile(constraintName), "alloc");
		String ports = ModelUtils.getConstraint(model.getConstraintsFile(constraintName), "ports");
		String timings = ModelUtils.getConstraint(model.getConstraintsFile(constraintName), "timings");

		if (latency == null) exit("Could not find 'latency' constraint.");
		if (alloc == null) exit("Could not find 'alloc' constraint.");
		if (ports == null) exit("Could not find 'ports' constraint.");
		if (timings == null) exit("Could not find 'timings' constraint.");

		File tmpAllocFile = new File("_tmp", "tmp.alloc");
		File tmpPortsFile = new File("_tmp", "tmp.ports");
		File tmpTimingsFile = new File("_tmp", "tmp.timings");
		File tmpScriptFile = new File("_tmp", "tmp.script");

		TemplateManager template = new TemplateManager();
		template.define("latency", latency);
		template.define("model_filepath", model.getModelFile().getPath());
		template.define("model_vhdl_filepath", model.getVhdlFile(constraintName).getPath());
		template.define("alloc_filepath", tmpAllocFile.getPath());
		template.define("ports_filepath", tmpPortsFile.getPath());
		template.define("timings_filepath", tmpTimingsFile.getPath());

		try {
			FileUtils.writeStringToFile(tmpAllocFile, alloc);
			FileUtils.writeStringToFile(tmpPortsFile, ports);
			FileUtils.writeStringToFile(tmpTimingsFile, timings);

			String script = template.process(scriptFile);
			FileUtils.writeStringToFile(tmpScriptFile, script);

			GlabUtils.execute("script(" + tmpScriptFile.getPath() + ")");
			Graph graph = GlabUtils.getCurrentGraph();

			String graphStr = GraphUtils.serialize(graph);
			FileUtils.writeStringToFile(model.getGraphFile(constraintName), graphStr);

			String metaStr = generateMeta(graph);
			FileUtils.writeStringToFile(model.getMetaFile(constraintName), metaStr);

		} catch (IOException ex) {
			ModelUtils.clearResult(model, constraintName);
			exit("Something went wrong during compilation of " + model.getHumanReadableName(constraintName), ex);
		}
	}

	private String generateMeta(Graph g) {
		List<Node> vars =  GraphUtils.getVariables(g, GraphUtils.EXCLUDE_CONSTANTS | GraphUtils.EXCLUDE_TEMPVARS);
		String result = "";

		result += "[Variables]";
		for (Node n : vars) {
			String[] ress = AttrFx.getHardwareRessource(n).split(",");
			String res = ress[0].equals("") ? ress[1] : ress[0];
			int lifeStart = AttrFx.getLifeStart(n);
			int lifeEnd = AttrFx.getLifeStop(n);
			result += "\n" + n.getNodeName() + ";" + res + ";" + lifeStart + ";" + lifeEnd;
		}

		result += "\n\n[Monitors]";
		List<ContractGraph> ctrGraphs = ContractContext.getPulledGraphs();
		for (ContractGraph ctrGraph : ctrGraphs) {
			int texec = ContractAttributes.getContractExecDate(ContractUtils.getContractGraphSink(ctrGraph));
			result += "\n" + ctrGraph.getId() + ";" + ctrGraph.getEquation() + ";" + texec;
		}

		return result;
	}
}
