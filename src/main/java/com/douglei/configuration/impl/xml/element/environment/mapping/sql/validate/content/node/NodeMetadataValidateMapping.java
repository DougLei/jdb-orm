package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.database.metadata.sql.content.node.NodeMetadata;
import com.douglei.instances.scanner.ClassScanner;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class NodeMetadataValidateMapping {
	private static Map<Short, NodeMetadataValidate> NODE_METADATA_VALIDATE_MAPPING;
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classPaths = cs.scan(NodeMetadataValidateMapping.class.getPackage().getName() + ".impl");
		NODE_METADATA_VALIDATE_MAPPING = new HashMap<Short, NodeMetadataValidate>(classPaths.size());
		
		Object obj = null;
		NodeMetadataValidate nodeMetadataValidate = null;
		for (String cp : classPaths) {
			obj = ConstructorUtil.newInstance(cp);
			if(obj instanceof NodeMetadataValidate) {
				nodeMetadataValidate= (NodeMetadataValidate) obj;
				NODE_METADATA_VALIDATE_MAPPING.put(nodeMetadataValidate.getNodeType(), nodeMetadataValidate);
			}
		}
		cs.destroy();
	}
	
	public static NodeMetadata doValidate(Node node) {
		NodeMetadataValidate nodeMetadataValidate = NODE_METADATA_VALIDATE_MAPPING.get(node.getNodeType());
		if(nodeMetadataValidate == null) {
			throw new NullPointerException("目前系统不支持nodeType=["+node.getNodeType()+"]的元素");
		}
		return (NodeMetadata) nodeMetadataValidate.doValidate(node);
	}
}
