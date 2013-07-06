package translation;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RobotSelectionListener implements ListSelectionListener {
	private View view;
	
	public RobotSelectionListener(View v) {
		view = v;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		view.showRobotStatistics();
	}

}
