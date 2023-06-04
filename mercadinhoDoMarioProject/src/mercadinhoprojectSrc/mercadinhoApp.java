package mercadinhoprojectSrc;
import forms.*;
import java.io.IOException;

/**
 *
 * @author Mario de Sousa Jr.
 */
public class mercadinhoApp {
    public static void main(String[] args) throws IOException {
        mainFrame mainFrameInstance = new mainFrame();
        mainFrameInstance.setLocationRelativeTo(null);
        mainFrameInstance.setVisible(true);
        
        /*productsManagementFrame productsManagementFrameInstance = new productsManagementFrame();
        productsManagementFrameInstance.setLocationRelativeTo(null);
        productsManagementFrameInstance.setVisible(true);*/
    }
}
