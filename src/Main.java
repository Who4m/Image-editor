
public class Main {

	public static void main(String[] args) {
		char[] char_array;
		char[] outPut;
		String hexaColor = "45ac37";
		String nextCOlor = "FFFFFF";
		String name = "FinalTest";
		String s1 = "FourSquares.bmp";
		String s2 = "OrangeFlower.bmp";
		String s3 = "tenk.jpg";
		String s4 = "3x3.bmp";
		String s5 = "prirodne-sise.jpg";
		String s6 = "RazjebanaVerzija.bmp";
		String s7 = "BlueSquare.bmp";
		char_array = ImageTranslate.importImage(s3, name, 0);
		ImageEditor editor = new ImageEditor();
		if (editor.loadImage(char_array)) {
			editor.addLayer();
			editor.setActiveColor(hexaColor);
			editor.setLayerOpacity(100);
			editor.fillRect(1000, 1000, 200, 200);
			editor.setActiveColor("F3A9B1");
			editor.fillRect(1200, 1000, 200, 200);
			editor.setActiveColor("19C72E");
			editor.fillRect(1000, 1200, 200, 200);
			editor.setActiveColor("49BFEC");
			editor.fillRect(1200, 1200, 200, 200);
			editor.blur(5);
			outPut = editor.saveImage();
			ImageTranslate.exportImg(outPut);
		} else {
			System.out.println("ERROR");
		}
	}
}
