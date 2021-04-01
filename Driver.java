import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Driver {
	//Session variables and objects
	static JLabel status;
	static Timer timer;
	static boolean enabled;
	static JFrame window;

	//Launch program
	public static void main(String[] args) {
		window = new KeyListenerTester();
		timer = new Timer();
		timer.schedule(new RemindTask(), 0, 30 * 1000);
	}

	//Update the status in the window
	public static void switchGraphics() {
		if (enabled) {
			status.setText("Enabled");
		} else {
			status.setText("Disabled");
		}
		window.repaint();
		window.revalidate();
	}

	//Simulate a key stroke on 'Enter'
	public static void click() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);

		robot.keyRelease(KeyEvent.VK_ENTER);
	}
}

//
class KeyListenerTester extends JFrame implements KeyListener {
	//auto generated ID
	private static final long serialVersionUID = 1L;
	
	JLabel status;
	JFrame window = Driver.window;

	//Create a window to monitor the timer status
	public KeyListenerTester() {
		super();

		setSize(400, 100);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		status = new JLabel("Disabled");
		Driver.status = status;
		JPanel p = new JPanel();

		p.add(status);
		add(p);

		status.setPreferredSize(new Dimension(50, 25));
		add(status);
		repaint();
		revalidate();

		setVisible(true);
		addKeyListener(this);
		setSize(400, 200);
	}

	//Control flow with toggle key
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '`') {
			Driver.enabled = !Driver.enabled;
			Driver.switchGraphics();
		}
	}

	//Ignore these methods
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}

// Timer task to be scheduled
class RemindTask extends TimerTask {
	public void run() {
		System.out.println(Driver.enabled);
		if (Driver.enabled) {
			try {
				Driver.click();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}
}
