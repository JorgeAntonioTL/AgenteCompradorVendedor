package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agents.BookSellerAgent;

public class BookSellerGui extends JFrame {
    private BookSellerAgent myAgent;
    private JTextField titleField, priceField;

    public BookSellerGui(BookSellerAgent a) {
        super(a.getLocalName());

        myAgent = a;

        // Crear un panel para la entrada de datos (título y precio)
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        p.add(new JLabel("Book title:"));
        titleField = new JTextField(15);
        p.add(titleField);
        p.add(new JLabel("Price:"));
        priceField = new JTextField(15);
        p.add(priceField);
        getContentPane().add(p, BorderLayout.CENTER);

        // Crear un botón para agregar libros al catálogo
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    // Obtener los valores de título y precio ingresados por el usuario
                    String title = titleField.getText().trim();
                    String price = priceField.getText().trim();

                    // Llamar al método del agente para actualizar el catálogo
                    myAgent.updateCatalogue(title, Integer.parseInt(price));

                    // Limpiar los campos de entrada
                    titleField.setText("");
                    priceField.setText("");

                    // Mostrar una ventana emergente de confirmación
                    JOptionPane.showMessageDialog(BookSellerGui.this, "Venta confirmada", "Confirmación",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    // En caso de valores inválidos, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Crear un panel para el botón "Add"
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        // Agregar un manejador de eventos para cerrar la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Llamar al método para eliminar el agente cuando se cierre la ventana
                myAgent.doDelete();
            }
        });

        // Configurar la ventana
        setResizable(false);
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
}
