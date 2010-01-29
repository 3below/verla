package info.threebelow.verla.mvc.view.velocity

import java.util.{Iterator => JavaIterator}

import org.apache.velocity.util.introspection.{Info, UberspectImpl, VelPropertyGet}
import org.apache.velocity.util.introspection.UberspectImpl.VelGetterImpl


class ScalaUberspect extends UberspectImpl {
   
    override def getIterator(obj: java.lang.Object, i: Info): JavaIterator[_] = {
      def makeJavaIterator(iter: Iterator[_]) =  new JavaIterator[AnyRef] {
        	override def hasNext() = iter.hasNext
        	override def next() = iter.next().asInstanceOf[AnyRef]
            override def remove() = throw new java.lang.UnsupportedOperationException("Remove not supported")
        }
      
      obj match {
        case i: Iterable[_] => makeJavaIterator(i.elements)
        case i: Iterator[_] => makeJavaIterator(i)
        case _ => super.getIterator(obj, i)
      }
    }
    
	override def getPropertyGet(obj: java.lang.Object, identifier: String, i: Info): VelPropertyGet = {
	  if (obj != null) {
	    val claz = obj.getClass()

        val executor = obj match {
	      case m: Map[_, _] => new ScalaMapGetExecutor(log, claz, identifier)	       
	      case _ => new ScalaPropertyExecutor(log, introspector, claz, identifier)
         
	    }

        if (executor.isAlive) {
          new VelGetterImpl(executor)
        } else {
          super.getPropertyGet(obj, identifier, i)
        }
	  } else {
	    null
	  }
	}
}
