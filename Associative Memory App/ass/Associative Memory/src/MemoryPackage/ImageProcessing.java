package MemoryPackage;

// this code need to be fixed in readImage...It is just given to make it easy.
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageProcessing {

	File inFile;
	int pathNmber, inputVectorSize, outputVectorSize;
	Vector<Integer> pixels = new Vector<Integer>();

	public ImageProcessing(String path, int pathNmber) {
		inFile = new File(path);
		this.pathNmber = pathNmber;
	}

	public void writeToFile(String type) throws IOException {
		if (type.equals("1") || type.equals("3") || type.equals("8")) {
			BufferedWriter fileW = null;
			fileW = new BufferedWriter(new FileWriter("recall" + type + ".txt",
					false));
			for (int i = 0; i < this.pixels.size(); i++) {
				fileW.write(this.pixels.get(i) + " ");
			}
			fileW.newLine();
			fileW.close();
		} else {
			type = type.substring(1);
			BufferedWriter fileW = null;
			fileW = new BufferedWriter(new FileWriter(
					"vectorX" + type + ".txt", false));
			for (int i = 0; i < this.pixels.size(); i++) {
				fileW.write(this.pixels.get(i) + " ");
			}
			fileW.newLine();
			fileW.close();
		}
	}

	public Vector<Integer> BiPolarFunction() {
		for (int i = 0; i < this.pixels.size(); i++)
			if (this.pixels.get(i).equals(0))
				this.pixels.set(i, -1);

		return this.pixels;
	}

	public Vector<Integer> readImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(inFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int width = image.getWidth();
		int height = image.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = image.getRGB(x, y);
				int r = (int) ((rgb >> 16) & 0xff);
				int g = (rgb >> 8) & 0xff;
				int b = (rgb >> 0) & 0xff;
				if (r == 0 && g == 0 && b == 0)
					pixels.add(0);
				else if (r == 255 && g == 255 && b == 255)
					pixels.add(1);
			}
		}

		if (pathNmber == 1) {
			pixels.add(0);
			pixels.add(0);
			pixels.add(0);
			pixels.add(1);
		} else if (pathNmber == 3) {
			pixels.add(0);
			pixels.add(0);
			pixels.add(1);
			pixels.add(1);
		} else if (pathNmber == 8) {
			pixels.add(1);
			pixels.add(0);
			pixels.add(0);
			pixels.add(0);
		}
		return pixels;
	}

	public void writeImage(int vect[] , String fileName) throws IOException {
		BufferedImage img = new BufferedImage(5, 10,
				BufferedImage.TYPE_INT_RGB);

		int width = img.getWidth();
		int height = img.getHeight();
		
		try {
			File f = new File(fileName + ".bmp");
			int indx = 0;
			Color myWhite = new Color(255, 255, 255); // Color white
			int rgb1 = myWhite.getRGB();

			Color myBlack = new Color(0, 0, 0); // Color white
			int rgb2 = myBlack.getRGB();

			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					if (vect[indx] == 1)
						img.setRGB(y, x, rgb1);
					else
						img.setRGB(y, x, rgb2);
					indx++;
				}
			}
			ImageIO.write(img, "bmp", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
