package bcornu.nullmode;

import java.io.PrintStream;
import java.io.PrintWriter;

import initialization.on.Null;


@SuppressWarnings("serial")
public class DeluxeNPE extends NullPointerException {

	private DebugInfo data;

	public DeluxeNPE(DebugInfo data) {
		this.data = data;

		StackTraceElement[] old = super.getStackTrace();
		StackTraceElement[] res = new StackTraceElement[old.length-1];
		for (int i =1;i<old.length;i++) {
			res[i-1]=old[i];
		}
		setStackTrace(res);
	}
	
	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
	}

	@Override
	public String toString() {
//		try{
		return super.toString()+" from "+ data.getFirst()+"\n"+NullInstanceManager.printNb();
//		}catch(Throwable t){
//			t.printStackTrace(System.out);
//			return "cannot provide data";
//		}
	}
	
	@Override
	public synchronized Throwable getCause() {
		Null e = null;
		for (int i = 0;i<data.nbSteps();i++) {
			e =new Null("from "+data.get(i),e);
			try{
				e.setStackTrace(data.getStack(i));
			}catch(NullPointerException npe){
				System.err.println("cannot set the creation trace");
			}
		}
		return e;
	}
}