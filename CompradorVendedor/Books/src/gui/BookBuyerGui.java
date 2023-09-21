package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agents.BookBuyerAgent;

public class BookBuyerGui extends JFrame {
    private BookBuyerAgent myAgent;
    private JTextField titleField;

    public BookBuyerGui(BookBuyerAgent a) {
        super(a.getLocalName());

        myAgent = a;

        // Crear un panel para la entrada de datos (título del libro a buscar)
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Book title to search: "), BorderLayout.WEST);
        titleField = new JTextField(15);
        p.add(titleField, BorderLayout.CENTER);
        getContentPane().add(p, BorderLayout.CENTER);

        // Configurar la ventana
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Método para mostrar la interfaz gráfica
    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;

        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

    // Método para obtener el título ingresado por el usuario
    public String getBookTitle() {
        return titleField.getText().trim();
    }
}
