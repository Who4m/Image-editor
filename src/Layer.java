
public class Layer {
	Pixel[][] matrix;
	int opacity;

	public Layer(Pixel[][] piksel, int opacity) {
		this.matrix = piksel;
		this.opacity = opacity;
	}

	public Pixel[][] getMatrix() {
		return matrix;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
}
