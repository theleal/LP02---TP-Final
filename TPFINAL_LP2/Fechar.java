/**
 * @author Rodrigo Rebelo e Luiz Gustavo
 */

import java.awt.event.*;

public class Fechar extends WindowAdapter
{
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }
}