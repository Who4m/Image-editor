
public class Pixel {
	int r;
	int g;
	int b;

	public Pixel(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public void setR(int r) {
		this.r = r;

	}

	public void setG(int g) {
		this.g = g;
	}

	public void setB(int b) {
		this.b = b;
	}

	public String toString() {
		String odgovor = "";
		odgovor = "R:" + r + " G:" + g + " B:" + b;
		return odgovor;
	}
}
