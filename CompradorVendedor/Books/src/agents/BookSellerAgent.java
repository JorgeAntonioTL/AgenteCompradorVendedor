package agents;

import java.util.Hashtable;
import behaviours.OfferRequestServer;
import behaviours.PurchaseOrderServer;
import gui.BookSellerGui;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookSellerAgent extends Agent {

    private Hashtable catalogue; // Almacena el catálogo de libros y sus precios
    private BookSellerGui gui;   // Interfaz gráfica para el agente vendedor

    protected void setup() {
        catalogue = new Hashtable();

        gui = new BookSellerGui(this);
        gui.showGui();

        // Registrar el agente en el servicio de directorio (Directory Facilitator)
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType("book-selling");
        sd.setName("book-trading");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Agregar comportamientos para manejar solicitudes de oferta y órdenes de compra
        addBehaviour(new OfferRequestServer(this));
        addBehaviour(new PurchaseOrderServer(this));
    }

    protected void takeDown() {
        try {
            // Deregistrar el agente del servicio de directorio antes de terminar
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Cerrar la interfaz gráfica antes de terminar
        gui.dispose();

        System.out.println("Seller agent " + getAID().getName() + " terminating");
    }

    public void updateCatalogue(final String title, final int price) {
        // Agregar un libro al catálogo (usando un comportamiento OneShotBehaviour)
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                catalogue.put(title, price);
                System.out.println(title + " inserted with a price of " + price);
            }
        });
    }

    public Hashtable getCatalogue() {
        // Obtener el catálogo de libros
        return catalogue;
    }
}
