package floribot_acceleo.java;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

public class FloriBotAcceleo {
	
	/**
	 * @param p a Package containing nodes or launches
	 * @return depending packages resulting from message types of ports
	 */
	public Set<Namespace> depends(Package p) {
		HashSet<Namespace> depends = new HashSet<Namespace>();
		EList<NamedElement> classes = p.getOwnedMembers();
		for(NamedElement ne: classes) {
			if(ne.isStereotypeApplied(ne.getAppliedStereotype("ros_profile::node"))) {
				for(Port aPort : ((org.eclipse.uml2.uml.Class) ne).getOwnedPorts()) {
					depends.add(aPort.getType().getNamespace());
				}
			} else if(ne.isStereotypeApplied(ne.getAppliedStereotype("ros_profile::launch"))) {
				for(Property aPart : ((org.eclipse.uml2.uml.Class) ne).getOwnedAttributes()) {
					if (!aPart.getType().isStereotypeApplied(aPart.getType().getAppliedStereotype("ros_profile::primitive_type"))) {
						depends.add(aPart.getType().getNamespace());
					}
				}
			}
		}
		depends.remove(p);
		return depends;
	}
	

	/**
	 * @param p is a Part with Ports
	 * @return a remap String for the use in launch files
	 */
	public Set<String> remaps(Property p) {
		HashSet<String> remaps = new HashSet<String>();
		EList<Connector> allConnectors = ((org.eclipse.uml2.uml.Class) p.getOwner()).getOwnedConnectors();
		for(Connector con: allConnectors) {
			if (!con.getEnds().get(0).getRole().getName().equals(con.getEnds().get(1).getRole().getName())) {
				int myEnd = 1, otherEnd = 0;
				if ((con.getEnds().get(0).getPartWithPort() == p)) {
					myEnd = 0; otherEnd = 1;
				}
				String line = "<remap from=\"" + con.getEnds().get(myEnd).getRole().getName() + "\" to=\"" + con.getEnds().get(otherEnd).getRole().getName() + "\"/>";
				remaps.add(line);
			}
		}
		return remaps;
	}
}
