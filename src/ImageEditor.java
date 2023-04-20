import java.util.ArrayList;

public class ImageEditor {
	int width;
	int height;
	String name;
	int start;
	ArrayList<Layer> layers;
	Layer activeLayer;
	Layer finalLayer;
	Pixel activeColor;

	private void mergeLayers() {
		// Daje mi obradjen lajer koji zelim da ucitam;
		finalLayer = new Layer(new Pixel[height][width], 100);
		double r = 0;
		double g = 0;
		double b = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				r = 0;
				g = 0;
				b = 0;
				for (int m = layers.size() - 1; m >= 0; m--) {
					double coeficient = (double) layers.get(m).getOpacity() / 100.0;
					if (m < layers.size() - 1) {
						for (int p = m + 1; p < layers.size(); p++) {
							if (layers.get(p).getMatrix()[i][j] != null) {
								coeficient = coeficient * (1 - ((double) layers.get(p).getOpacity() / 100.0));
							}
						}
					}
					if (layers.get(m).getMatrix()[i][j] == null) {

					} else {
						r += layers.get(m).getMatrix()[i][j].getR() * coeficient;
						g += layers.get(m).getMatrix()[i][j].getG() * coeficient;
						b += layers.get(m).getMatrix()[i][j].getB() * coeficient;
					}
				}
				finalLayer.getMatrix()[i][j] = new Pixel((int) r, (int) g, (int) b);
			}
		}
	}

	public ImageEditor() {
		width = 0;
		height = 0;
		name = null;
		start = 0;
		layers = new ArrayList<Layer>();
		activeColor = new Pixel(0, 0, 0);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void pictureName(char[] a) {
		int i = 3;
		String ime = "";
		while (a[i] != '=') {
			ime += a[i];
			i++;
		}
		this.name = ime;
	}

	public void start(char[] a) {
		int startFill;
		startFill = name.length() + 4;
		while (a[startFill] == 32) {
			startFill++;
		}
		this.start = startFill;
	}

	public void calculateWidth(char[] a) {
		String sirina = "";
		start(a);
		for (int i = this.start + 24; i <= start + 31; i++) {
			sirina += a[i];
		}
		for (int i = this.start + 16; i <= start + 23; i++) {
			sirina += a[i];
		}
		for (int i = this.start + 8; i <= start + 15; i++) {
			sirina += a[i];
		}
		for (int i = this.start; i <= start + 7; i++) {
			sirina += a[i];
		}
		int j = 0;
		for (int i = sirina.length() - 1; i >= 0; i--) {
			if (sirina.charAt(i) == '1') {
				this.width += Math.pow(2, j);
				j++;
			} else {
				j++;
			}
		}
	}

	public void calculateHeight(char[] a) {
		String duzina = "";
		start(a);
		for (int i = this.start + 56; i <= start + 63; i++) {
			duzina += a[i];
		}
		for (int i = this.start + 48; i <= start + 55; i++) {
			duzina += a[i];
		}
		for (int i = this.start + 40; i <= start + 47; i++) {
			duzina += a[i];
		}
		for (int i = this.start + 32; i <= start + 39; i++) {
			duzina += a[i];
		}
		int j = 0;
		for (int i = duzina.length() - 1; i >= 0; i--) {
			if (duzina.charAt(i) == '1') {
				this.height += Math.pow(2, j);
				j++;
			} else {
				j++;
			}
		}
	}

	public int decimalValue(String color) {
		int value = 0;
		int j = 0;
		for (int i = color.length() - 1; i >= 0; i--) {
			if (color.charAt(i) == '1') {
				value += Math.pow(2, j);
				j++;
			} else {
				j++;
			}
		}
		return value;
	}

	public boolean loadImage(char[] a) {
		if (a.length < 96) {
			return false;
		}
		if (a[0] != 'B') {
			return false;
		}
		if (a[1] != 'M') {
			return false;
		}
		if (a[2] != '=') {
			return false;
		}
		if (a[3] == '=') {
			return false;
		}
		int charFaund = 0;
		for (int i = 3; i < a.length; i++) {
			if (a[i] == '=') {
				charFaund++;
			}
		}
		if (charFaund != 1) {
			return false;
		}

		pictureName(a);
		this.start = name.length() + 4;
		if ((start + 64 + 3) > a.length) {
			return false;
		}
		int fillStart = name.length() + 4;
		for (int i = fillStart; i < fillStart + 4; i++) {
			if (a[i] == ' ') {
				if (i % 4 == 0) {
					return false;
				}
			}
		}
		// chek if fill is missing
		for (int i = fillStart; i < fillStart + 4; i++) {
			if (i % 4 != 0) {
				if (a[i] != ' ') {
					return false;
				}
			}
		}

		start(a);
		for (int i = start; i < a.length; i++) {
			if ((a[i] != '0') && (a[i] != '1')) {
				return false;
			}
		}
		calculateWidth(a);
		calculateHeight(a);
		int size = (width * height) * 24 + (start + 64);
		if (a.length != size) {
			return false;
		}
		Pixel[][] layerPiksel = new Pixel[height][width];
		Layer sloj;
		int x = 0;
		int y = 0;
		for (int i = start + 64; i < a.length; i += 24) {
			Pixel piksel;
			String R = "";
			String G = "";
			String B = "";
			int r = 0;
			int g = 0;
			int b = 0;
			for (int j = i; j < i + 24; j++) {
				if (j < i + 8) {
					B += a[j];
				}
				if ((j >= i + 8) && (j < i + 16)) {
					G += a[j];
				}
				if (j >= i + 16) {
					R += a[j];
				}
			}
			r = decimalValue(R);
			g = decimalValue(G);
			b = decimalValue(B);
			piksel = new Pixel(r, g, b);
			layerPiksel[x][y] = piksel;
			y++;
			if (y % width == 0) {
				x++;
				y = 0;
			}
		}
		sloj = new Layer(layerPiksel, 100);
		layers.add(sloj);
		activeLayer = layers.get(0);
		return true;
	}

	public String padding(String broj, int x) {
		String vracam = "";
		int padding = x - broj.length();
		for (int i = 0; i < padding; i++) {
			vracam += "0";
		}
		vracam += broj;
		return vracam;
	}

	public String convertToBinary(int x) {

		String binaryValue = "";
		if (x == 0) {
			binaryValue = "0";
		}
		if (x < 0) {
			return null;
		}
		double ostatak = x;
		int i = 0;
		double stepen = Math.pow(2, i);
		while (stepen <= x) {
			stepen = Math.pow(2, i);
			i++;
		}
		i -= 2;
		stepen = Math.pow(2, i);
		while (i > -1) {
			if (stepen <= ostatak) {
				ostatak = ostatak - stepen;
				binaryValue += "1";
			} else {
				binaryValue += "0";
			}
			i--;
			stepen = Math.pow(2, i);
		}
		return binaryValue;
	}

	public char[] saveImage() {
		mergeLayers();
		// deklaracija
		int size = (width * height) * 24 + (start + 64);
		int fill = 3;
		char[] niz = new char[size];
		Pixel piksel;
		// inicijalno popunjavanje niza (fiksne vrednosti)
		niz[0] = 'B';
		niz[1] = 'M';
		niz[2] = '=';
		// ovde dodajem ime slike;
		for (int i = 0; i < name.length(); i++) {
			niz[fill] = name.charAt(i);
			fill++;
		}
		niz[fill] = '=';
		fill++;
		// ovde dopunjujem do prvi broj deljiv sa 4
		while (fill % 4 != 0) {
			niz[fill] = ' ';
			fill++;
		}
		// ovde pretrvaram sirinu i visinu u binary i paddujem sa 0;
		String sirina = convertToBinary(width);
		String visina = convertToBinary(height);
		sirina = padding(sirina, 32);
		visina = padding(visina, 32);
		// ovde dodajem sirinu
		for (int i = 24; i < 32; i++) {
			niz[fill] = sirina.charAt(i);
			fill++;
		}
		for (int i = 16; i < 24; i++) {
			niz[fill] = sirina.charAt(i);
			fill++;
		}
		for (int i = 8; i < 16; i++) {
			niz[fill] = sirina.charAt(i);
			fill++;
		}
		for (int i = 0; i < 8; i++) {
			niz[fill] = sirina.charAt(i);
			fill++;
		}
		// ovde dodajem visinu
		for (int i = 24; i < 32; i++) {
			niz[fill] = visina.charAt(i);
			fill++;
		}
		for (int i = 16; i < 24; i++) {
			niz[fill] = visina.charAt(i);
			fill++;
		}
		for (int i = 8; i < 16; i++) {
			niz[fill] = visina.charAt(i);
			fill++;
		}
		for (int i = 0; i < 8; i++) {
			niz[fill] = visina.charAt(i);
			fill++;
		}
		// ovde dodajem piksele u niz
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				String g = "";
				String b = "";
				String r = "";
				piksel = finalLayer.getMatrix()[i][j];

				// ovde dodajem b;
				b = convertToBinary(piksel.getB());
				b = padding(b, 8);
				for (int k = 0; k < 8; k++) {
					niz[fill] = b.charAt(k);
					fill++;
				}
				// ovde dodajem g ;
				g = convertToBinary(piksel.getG());
				g = padding(g, 8);
				for (int l = 0; l < 8; l++) {
					niz[fill] = g.charAt(l);
					fill++;
				}
				// ovde dodajem r;
				r = convertToBinary(piksel.getR());
				r = padding(r, 8);
				for (int m = 0; m < 8; m++) {
					niz[fill] = r.charAt(m);
					fill++;
				}
			}
		}

		return niz;
	}

	public void addLayer() {
		Pixel[][] matrica = new Pixel[height][width];
		Layer sloj = new Layer(matrica, 0);
		layers.add(sloj);
		activeLayer = sloj;
	}

	public void deleteLayer() {
		for (int i = 1; i < layers.size(); i++) {
			if (layers.get(i) == activeLayer) {
				layers.remove(i);
				activeLayer = layers.get(i - 1);
			}
		}
	}

	public void selectLayer(int i) {
		if (i >= layers.size()) {
			return;
		}
		activeLayer = layers.get(i);
	}

	public void setLayerOpacity(int opacity) {
		if (activeLayer == layers.get(0)) {
			return;
		}
		activeLayer.setOpacity(opacity);

	}

	public void invertColors() {
		Pixel piksel;
		int R;
		int G;
		int B;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (activeLayer.getMatrix()[i][j] != null) {
					piksel = activeLayer.getMatrix()[i][j];
					R = piksel.getR();
					G = piksel.getG();
					B = piksel.getB();
					R = 255 - R;
					G = 255 - G;
					B = 255 - B;
					piksel.setB(B);
					piksel.setG(G);
					piksel.setR(R);
				}
			}

		}
	}

	public void toGrayScale() {
		Pixel piksel;
		int R;
		int G;
		int B;
		double r;
		double g;
		double b;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (activeLayer.getMatrix()[i][j] != null) {
					piksel = activeLayer.getMatrix()[i][j];
					R = piksel.getR();
					B = piksel.getB();
					G = piksel.getG();
					r = (0.3 * (double) R) + (0.59 * (double) G) + (0.11 * (double) B);
					g = (0.3 * (double) R) + (0.59 * (double) G) + (0.11 * (double) B);
					b = (0.3 * (double) R) + (0.59 * (double) G) + (0.11 * (double) B);
					piksel.setB((int) b);
					piksel.setG((int) g);
					piksel.setR((int) r);
				}
			}
		}
	}

	public void flipVertical() {
		Pixel piksel;
		Pixel[][] newMatrix = new Pixel[height][width];
		Layer newLayer;
		int opacity = activeLayer.getOpacity();
		int x = height;
		int y = 0;
		for (int i = 0; i < height; i++) {
			x--;
			y = 0;
			for (int j = 0; j < width; j++) {
				piksel = activeLayer.matrix[i][j];
				newMatrix[x][y] = piksel;
				y++;
			}
		}
		newLayer = new Layer(newMatrix, opacity);
		for (int z = 0; z < layers.size(); z++) {
			if (layers.get(z) == activeLayer) {
				layers.set(z, newLayer);
			}
		}
		activeLayer = newLayer;
	}

	public void flipHorizontal() {
		Pixel piksel;
		Pixel[][] newMatrix = new Pixel[height][width];
		Layer newLayer;
		int opacity = activeLayer.getOpacity();
		int x = 0;
		int y = width;
		for (int i = 0; i < height; i++) {
			x = i;
			y = width - 1;
			for (int j = 0; j < width; j++) {
				piksel = activeLayer.matrix[i][j];
				newMatrix[x][y] = piksel;
				y--;
			}
		}
		newLayer = new Layer(newMatrix, opacity);
		for (int z = 0; z < layers.size(); z++) {
			if (layers.get(z) == activeLayer) {
				layers.set(z, newLayer);
			}
		}
		activeLayer = newLayer;
	}

	public void crop(int x, int y, int w, int h) {
		for (int o = 0; o < layers.size(); o++) {
			Pixel piksel;
			Layer newLayer;
			int iterationW = w;
			int iterationH = h;
			int iterationY = y;
			int interationX = x;
			iterationY = (height - y);
			int cropHeight = iterationY - iterationH;
			int cropWidth = interationX + iterationW;
			Pixel[][] newMatrix = new Pixel[h][w];
			int opacity = layers.get(o).getOpacity();
			for (int i = iterationY; i > cropHeight; i--) {
				iterationH--;
				iterationW = 0;
				for (int j = x; j < cropWidth; j++) {
					piksel = layers.get(o).getMatrix()[i][j];
					newMatrix[iterationH][iterationW] = piksel;
					iterationW++;
				}
			}
			newLayer = new Layer(newMatrix, opacity);
			layers.set(o, newLayer);
		}
		this.height = h;
		this.width = w;
	}

	public void blur(int size) {
		Pixel piksel;
		Pixel[][] newMatrix = new Pixel[height][width];
		int opacity = activeLayer.getOpacity();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// inicijalizujem i resetujem sve vrednosti;
				int numOfPixels = 0;
				int R = 0;
				int G = 0;
				int B = 0;
				for (int x = i - size; x < i + size + 1; x++) {
					for (int y = j - size; y < j + size + 1; y++) {
						if ((x < 0) || (x > (height - 1))) {
							// Ako je x outOfBaunds
							R += 0;
							G += 0;
							B += 0;
							// numOfPixels++;
						} else if ((y < 0) || (y > (width - 1))) {
							// Ako je y outOfBaunds
							R += 0;
							G += 0;
							B += 0;
							// numOfPixels++;
						} else {
							// Ako je piksel unutar matrice
							if (activeLayer.getMatrix()[x][y] != null) {
								R += activeLayer.getMatrix()[x][y].getR();
								G += activeLayer.getMatrix()[x][y].getG();
								B += activeLayer.getMatrix()[x][y].getB();
								numOfPixels++;
							}
						}
					}
				}
				// Racunam prosecnu vrednost za RGB;// Ubacujem blurovanu verziju piksel
				if (activeLayer.getMatrix()[i][j] != null) {
					R = R / numOfPixels;
					G = G / numOfPixels;
					B = B / numOfPixels;
					piksel = new Pixel(R, G, B);
					newMatrix[i][j] = piksel;
				}

			}
		}

		Layer replace = new Layer(newMatrix, opacity);
		// Menjam u aktiv layer
		for (int z = 0; z < layers.size(); z++) {
			if (layers.get(z) == activeLayer) {
				layers.set(z, replace);
			}
		}
		// Resetujem activeLayer
		activeLayer = replace;
	}

	// TODO napravi hexa to deca;
	// da radi set activ kolor preko nju;
	public String hexaToBinary(char x) {
		switch (x) {
		case '0':
			return "0000";
		case '1':
			return "0001";
		case '2':
			return "0010";
		case '3':
			return "0011";
		case '4':
			return "0100";
		case '5':
			return "0101";
		case '6':
			return "0110";
		case '7':
			return "0111";
		case '8':
			return "1000";
		case '9':
			return "1001";
		case 'A', 'a':
			return "1010";
		case 'B', 'b':
			return "1011";
		case 'C', 'c':
			return "1100";
		case 'D', 'd':
			return "1101";
		case 'E', 'e':
			return "1110";
		case 'F', 'f':
			return "1111";
		}
		return "";
	}

	public void setActiveColor(String hex) {
		Pixel piksel;
		int R;
		int G;
		int B;
		String r;
		String b;
		String g;
		r = hexaToBinary(hex.charAt(0));
		r += hexaToBinary(hex.charAt(1));
		b = hexaToBinary(hex.charAt(2));
		b += hexaToBinary(hex.charAt(3));
		g = hexaToBinary(hex.charAt(4));
		g += hexaToBinary(hex.charAt(5));
		R = decimalValue(r);
		G = decimalValue(g);
		B = decimalValue(b);
		piksel = new Pixel(R, G, B);
		activeColor = piksel;
	}

	public void fillRect(int x, int y, int w, int h) {
		int rectH = (height - y - 1) - h;
		int rectW = x + w;
		int R = activeColor.getR();
		int G = activeColor.getG();
		int B = activeColor.getB();
		for (int i = height - y - 1; i > rectH; i--) {
			for (int j = x; j < rectW; j++) {
				if (activeLayer.getMatrix()[i][j] == null) {
					activeLayer.getMatrix()[i][j] = new Pixel(R, G, B);
				} else {
					activeLayer.getMatrix()[i][j].setB(B);
					activeLayer.getMatrix()[i][j].setR(R);
					activeLayer.getMatrix()[i][j].setG(G);
				}
			}
		}
	}

	public void eraseRect(int x, int y, int w, int h) {
		int rectH = (height - y - 1) - h;
		int rectW = x + w;
		for (int i = height - y - 1; i > rectH; i--) {
			for (int j = x; j < rectW; j++) {
				if (activeLayer.getMatrix()[i][j] != null) {
					activeLayer.getMatrix()[i][j] = null;
				}
			}
		}
	}
}
