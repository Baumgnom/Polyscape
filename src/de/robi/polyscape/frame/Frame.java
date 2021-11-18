package de.robi.polyscape.frame;

import de.robi.polyscape.scape.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;

public class Frame extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

	private final Canvas canvas;
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

		canvas = new Canvas();
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


				for(int x = 0; x < 400; x++) {
					double px = (x / 200.0 - 1) * 10.0;
					for(int y = 0; y < 400; y++) {
						double py = (y / 200.0 - 1) * 10.0;

						double value = 0.0;
						if(polynomial != null) {
							value = polynomial.getValue(px, py);
						}

						Color color = null;
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
