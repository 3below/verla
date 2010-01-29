package info.threebelow.verla.mvc.view.velocity

import org.apache.velocity.runtime.parser.node.PropertyExecutor
import org.apache.velocity.runtime.log.Log
import org.apache.velocity.util.introspection.Introspector

class ScalaPropertyExecutor(log: Log, introspector: Introspector,
                            clazz: java.lang.Class[_], property: String) 
                            extends PropertyExecutor(log, introspector, clazz, property) {

    
	override def discover(clazz: java.lang.Class[_], property: String) = {
	    val params = Array[java.lang.Object]()
		setMethod(introspector.getMethod(clazz, property, params))
		if(!isAlive()) {
		  super.discover(clazz, property)
		}
	}
 

}
