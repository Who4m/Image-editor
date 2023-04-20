import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTranslate {

	public static char[] importImage(String in, String name, int faultInject) {
		char[] toReturn;
		if (faultInject == 0) {
			toReturn = faultInject0(in, name);
		} else if (faultInject == 1) {
			toReturn = faultInject1(in, name);
		} else if (faultInject == 2) {
			toReturn = faultInject2(in, name);
		} else if (faultInject == 3) {
			toReturn = faultInject3(in, name);
		} else if (faultInject == 4) {
			toReturn = faultInject4(in, name);
		} else if (faultInject == 5) {
			toReturn = faultInject5(in, name);
		} else if (faultInject == 6) {
			toReturn = faultInject6(in, name);
		} else if (faultInject == 7) {
			toReturn = faultInject7(in, name);
		} else if (faultInject == 8) {
			toReturn = faultInject8(in, name);
		} else if (faultInject == 9) {
			toReturn = faultInject9(in, name);
		} else if (faultInject == 10) {
			toReturn = faultInject10(in, name);
		} else if (faultInject == 11) {
			toReturn = faultInject11(in, name);
		} else {
			toReturn = faultInject0(in, name);
		}
		return toReturn;
	}

	private static char[] faultInject0(String in, String name) {
		int getPixel;
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(in));
		} catch (IOException e) {
			e.printStackTrace();
		}

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (img.getHeight() * img.getWidth()) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((img.getWidth() & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((img.getHeight() & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}

		for (int y = img.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < img.getWidth(); x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = img.getRGB(x, y);
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}
		return rpr;
	}

	private static char[] faultInject1(String in, String name) {
		char[] rpr = new char[1];
		rpr[0] = 'B';
		return rpr;
	}

	private static char[] faultInject2(String in, String name) {
		int getPixel;
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (128 * 128) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'x';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}

		for (int y = 128 - 1; y >= 0; y--) {
			for (int x = 0; x < 128; x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = 333333;
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}
		return rpr;
	}

	private static char[] faultInject3(String in, String name) {
		int arrayIterator = 0;
		char[] rpr = new char[5003];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < 5000; i++) {
			rpr[arrayIterator++] = 'a';
		}
		return rpr;
	}

	private static char[] faultInject4(String in, String name) {
		int arrayIterator = 0;
		char[] rpr = new char[5006];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < 5001; i++) {
			rpr[arrayIterator++] = 'a';
		}
		rpr[arrayIterator++] = '=';
		rpr[arrayIterator++] = '0';
		return rpr;
	}

	private static char[] faultInject5(String in, String name) {
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 63; // Size of image
//		arrayLen = arrayLen + (128 * 128) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 31; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}
		return rpr;
	}

	private static char[] faultInject6(String in, String name) {
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
//		arrayLen = arrayLen + (128 * 128) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}
		return rpr;
	}

	private static char[] faultInject7(String in, String name) {
		int getPixel;
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (64 * 64) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}
		for (int y = 64 - 1; y >= 0; y--) {
			for (int x = 0; x < 64; x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = 33112345;
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}

		return rpr;
	}

	private static char[] faultInject8(String in, String name) {
		int getPixel;
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (200 * 200) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}
		for (int y = 128 - 1; y >= 0; y--) {
			for (int x = 0; x < 128; x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = 33112345;
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}

		return rpr;
	}

	private static char[] faultInject9(String in, String name) {
		int getPixel;
		int arrayLen;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (128 * 128) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}
		for (int y = 128 - 1; y >= 0; y--) {
			for (int x = 0; x < 128; x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = 33112345;
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}

		return rpr;
	}

	private static char[] faultInject10(String in, String name) {
		int getPixel;
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (128 * 128) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}

		rpr[arrayIterator - 63] = 'x';
		for (int y = 128 - 1; y >= 0; y--) {
			for (int x = 0; x < 128; x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = 33112345;
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}

		return rpr;
	}

	private static char[] faultInject11(String in, String name) {
		int getPixel;
		int arrayLen;
		int fillerSpaces;
		int arrayIterator = 0;
		char[][] hArray = new char[4][];
		char[][] wArray = new char[4][];
		int[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

		arrayLen = 2 + 1 + name.length() + 1; // "BM=name="
		fillerSpaces = 0;
		if (arrayLen % 4 != 0) {
			fillerSpaces = (4 - arrayLen % 4);
		}
		arrayLen = arrayLen + fillerSpaces; // Filler spaces to get to %4 number of chars
		arrayLen = arrayLen + 64; // Size of image
		arrayLen = arrayLen + (128 * 128) * 24;
		char[] rpr = new char[arrayLen];
		rpr[arrayIterator++] = 'B';
		rpr[arrayIterator++] = 'M';
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < name.length(); i++) {
			rpr[arrayIterator++] = name.charAt(i);
		}
		rpr[arrayIterator++] = '=';
		for (int i = 0; i < fillerSpaces; i++) {
			rpr[arrayIterator++] = ' ';
		}
		for (int i = 0; i < 4; i++) {
			wArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
			hArray[i] = dec2bin((128 & mask[i]) >> 8 * i, 8);
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = wArray[i / 8][i % 8];
		}
		for (int i = 0; i < 32; i++) {
			rpr[arrayIterator++] = hArray[i / 8][i % 8];
		}

		for (int y = 128 - 1; y >= 0; y--) {
			for (int x = 0; x < 128; x++) {
				int B, G, R;
				char[] b, g, r;
				getPixel = 33112345;
				B = getPixel & 0x000000FF;
				G = (getPixel & 0x0000FF00) >> 8;
				R = (getPixel & 0x00FF0000) >> 16;
				b = dec2bin(B, 8);
				g = dec2bin(G, 8);
				r = dec2bin(R, 8);
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = b[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = g[byte_it];
				}
				for (int byte_it = 0; byte_it < 8; byte_it++) {
					rpr[arrayIterator++] = r[byte_it];
				}
			}
		}
		rpr[arrayIterator - 1] = 'x';

		return rpr;
	}

	private static char[] dec2bin(int a, int n) {
		char[] bin = new char[n];
		for (int i = n - 1; i >= 0; i--) {
			bin[i] = (char) (a % 2 + '0');
			a = a / 2;
		}
		return bin;
	}

	public static void exportImg(char[] niz) {
		String name = "";
		int arrayIterator = 3;
		int[] w = { 0, 0, 0, 0 };
		int[] h = { 0, 0, 0, 0 };
		int final_w;
		int final_h;

		while (niz[arrayIterator] != '=') {
			name += niz[arrayIterator++];
		}
		arrayIterator++;

		if (arrayIterator % 4 != 0) {
			arrayIterator = arrayIterator + (4 - arrayIterator % 4);
		}

		for (int i = 0; i < 32; i++) {
			w[i / 8] = w[i / 8] + ((int) Math.pow(2, 7 - i % 8) * (niz[arrayIterator++] - '0'));
		}
		for (int i = 0; i < 32; i++) {
			h[i / 8] = h[i / 8] + ((int) Math.pow(2, 7 - i % 8) * (niz[arrayIterator++] - '0'));
		}

		final_w = w[0] + w[1] * 256 + w[2] * 256 * 256 + w[3] * 256 * 256 * 256;
		final_h = h[0] + h[1] * 256 + h[2] * 256 * 256 + h[3] * 256 * 256 * 256;

		BufferedImage img = new BufferedImage(final_w, final_h, BufferedImage.TYPE_3BYTE_BGR);
		for (int y = img.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < img.getWidth(); x++) {
				int b = 0, g = 0, r = 0;
				for (int pixel_it = 0; pixel_it < 8; pixel_it++) {
					b = b + ((int) Math.pow(2, 7 - pixel_it) * (niz[arrayIterator++] - '0'));
				}
				for (int pixel_it = 0; pixel_it < 8; pixel_it++) {
					g = g + ((int) Math.pow(2, 7 - pixel_it) * (niz[arrayIterator++] - '0'));
				}
				for (int pixel_it = 0; pixel_it < 8; pixel_it++) {
					r = r + ((int) Math.pow(2, 7 - pixel_it) * (niz[arrayIterator++] - '0'));
				}
				img.setRGB(x, y, new Color(r, g, b).getRGB());
			}
		}
		try {
			File outputfile = new File(name + ".bmp");
			ImageIO.write(img, "bmp", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (niz.length != arrayIterator) {
				throw new Exception("Pogresna duzina niza");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
