package problem3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class Scheduler {

	//Add the tasks to this scheduler to perform them periodically
	private final ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(1);
	
	public Scheduler()
	{
		//Add tasks to scheduledExecutorService
	}
}
