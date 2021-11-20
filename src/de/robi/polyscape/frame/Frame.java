package de.robi.polyscape.frame;

import de.robi.polyscape.scape.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serial;

public class Frame extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final int SIZE = 600;

	private final BufferStrategy bufferStrategy;

	private Polynomial polynomial;
	private Gradient gradient;

	private BufferedImage image;

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
		renderImage();
	}

	private void renderImage() {
		BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();
		Polynomial polynomial = this.polynomial;

		double fy = 10.0;
		double fx = 10.0;

		for(int x = 0; x < SIZE; x++) {
			double px = (x / (SIZE / 2.0) - 1) * fx;
			for(int y = 0; y < SIZE; y++) {
				double py = (y / (SIZE / 2.0) - 1) * fy;

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

				raster.setPixel(x, y, new int[] {color.getRed(), color.getGreen(), color.getBlue()});
			}
		}

		this.image = image;
	}

	public void setGradient(Gradient gradient) {
		this.gradient = gradient;
	}

	private void run() {
		new Thread(() -> {
			while(true) {
				Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();


				int size = Math.min(getHeight(), getWidth());

				if(image != null) {
					graphics.drawImage(image, 0, 0, size, size, null);
				}

				graphics.dispose();
				bufferStrategy.show();
			}
		}).start();
	}
}
