package managers;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class BorderManager {

    // Common Borders
    public static final Border EMPTY_BORDER = new EmptyBorder(0, 50, 0, 50);
    public static final Border THIN_LINE_BORDER = new LineBorder(Color.BLACK, 1);
    public static final Border THICK_LINE_BORDER = new LineBorder(Color.BLACK, 3);
}