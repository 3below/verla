package info.threebelow.verla.mvc.view.velocity

import org.apache.velocity.runtime.log.Log
import org.apache.velocity.runtime.parser.node.{AbstractExecutor, MapGetExecutor}

class ScalaMapGetExecutor(val llog: Log, val clazz: java.lang.Class[_], val property: String) extends MapGetExecutor(llog, clazz, property) {
	override def isAlive = true

	override def execute(o: AnyRef) = o.asInstanceOf[Map[String, AnyRef]]
                                 .getOrElse[AnyRef](property, null).asInstanceOf[java.lang.Object]
      
}
