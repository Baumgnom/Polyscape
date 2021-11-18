package de.robi.polyscape.frame;

import de.robi.polyscape.scape.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;

public class Frame extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

	private final BufferStrategy bufferStrategy;

	private Polynomial polynomial;
	private Gradient gradient;

	public Frame() {
		super();

		Dimension size = new Dimension(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Point position = new Point((screen.width - size.width) / 2, (screen.height - size.height) / 2);

		setMinimumSize(size);
		setLocation(position);
		setSize(size);

		JPanel panel = (JPanel) getContentPane();

		Canvas canvas = new Canvas();
		panel.add(canvas);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);

		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();

		run();
	}

	public void setPolynomial(Polynomial polynomial) {
		this.polynomial = polynomial;
	}

	public void setGradient(Gradient gradient) {
		this.gradient = gradient;
	}

	private void run() {
		new Thread(() -> {
			while(true) {
				Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();

				double fy = 5.0;
				double fx = 5.0 * getWidth() / getHeight();

				for(int x = 0; x < getWidth(); x++) {
					double px = (x / (getWidth() / 2.0) - 1) * fx;
					for(int y = 0; y < getHeight(); y++) {
						double py = (y / (getHeight() / 2.0) - 1) * fy;

						double value = 0.0;
						if(polynomial != null) {
							value = polynomial.getValue(px, py);
						}

						Color color;
						if(gradient != null) {
							color = gradient.getColor(value);
						} else {
							color = Color.RED;
						}

						graphics.setColor(color);
						graphics.fillRect(x, y, 1, 1);
					}
				}

				graphics.dispose();
				bufferStrategy.show();
			}
		}).start();
	}
}
