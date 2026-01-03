import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		image = flippedVertically(tinypic);
		System.out.println();
		print(image);

		image = grayScaled(tinypic);
		System.out.println();
		print(image);

		image = scaled(tinypic, 3, 5);
		System.out.println();
		print(image);
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		if (in.isEmpty()) {
			return null;
		}
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i ++)
		{
			for (int j = 0; j < numCols; j ++)
			{
				Color c = new Color(in.readInt(), in.readInt(), in.readInt());
				image[i][j] = c;
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i ++)
		{
			for (int j = 0; j < image[0].length; j ++)
			{
				print(image[i][j]);
			}
		}
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] fHImage = new Color[image.length][image[0].length];
		int imageCul = image[0].length - 1;
		for (int i = 0; i < fHImage.length; i ++)
		{
			for (int j = 0; j < fHImage[0].length; j ++)
			{
				fHImage[i][j] = image[i][imageCul - j];
			}
		}
		return fHImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] fVImage = new Color[image.length][image[0].length];
		int imageRow = image.length - 1;
		for (int i = 0; i < fVImage.length; i ++)
		{
			for (int j = 0; j < fVImage[0].length; j ++)
			{
				fVImage[i][j] = image[imageRow - i][j];
			}
		}
		return fVImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int lum = (int)(0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color grayS = new Color(lum, lum, lum);
		return grayS;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grayImage = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i ++)
		{
			for (int j = 0; j < image[0].length; j ++)
			{
				grayImage[i][j] = luminance(image[i][j]);
			}
		}
		return grayImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int h0 = image.length;
		int w0 = image[0].length;
		double ratioH = (double) h0 / height;
		double ratioW = (double) w0 / width;
		Color[][] sImage = new Color[height][width];
		for (int i = 0; i < sImage.length; i ++)
		{
			for (int j = 0; j < sImage[0].length; j ++)
			{
				sImage[i][j] = image[(int)(i * ratioH)][(int)(j * ratioW)];
			}
		}
		return sImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		double r = (c1.getRed() * alpha) + (1 - alpha) * c2.getRed();
		double g = (c1.getGreen() * alpha) + (1 - alpha) * c2.getGreen();
		double b = (c1.getBlue() * alpha) + (1 - alpha) * c2.getBlue();
		int newR = (int)r;
		int newG = (int)g;
		int newB = (int)b;
		Color blendC = new Color(newR, newG, newB);
		return blendC;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		if ((image1.length != image2.length) || (image1[0].length != image2[0].length)) {
			return null;
		}
		Color[][] blendCImages = new Color[image1.length][image1[0].length];
		for (int i = 0; i < blendCImages.length; i ++)
		{
			for (int j = 0; j < blendCImages[0].length; j ++)
			{
				blendCImages[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blendCImages;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		if ((source.length != target.length) || (source[0].length != target[0].length)) {
			target = scaled(target, source[0].length, source.length);
		}
		Color[][] blendCImages = source;
		for (int i = 0; i < n; i ++)
		{
			blendCImages = blend(blendCImages, target, (n-i)/n);
			Runigram.display(blendCImages);
			StdDraw.pause(500);
		}
		Runigram.display(target);
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

