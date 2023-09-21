package agents;

import jade.core.Agent;
import behaviours.RequestPerformer;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {
  private String bookTitle;     // Título del libro que el agente quiere comprar
  private AID[] sellerAgents;   // Arreglo de agentes vendedores
  private int ticker_timer = 10000;  // Intervalo de tiempo para realizar la búsqueda
  private BookBuyerAgent this_agent = this;  // Referencia al propio agente

  protected void setup() {
    System.out.println("Buyer agent " + getAID().getName() + " is ready");

    // Obtener los argumentos pasados al agente (el título del libro a comprar)
    Object[] args = getArguments();
    if(args != null && args.length > 0) {
      bookTitle = (String)args[0];
      System.out.println("Book: " + bookTitle);

      // Comportamiento periódico para buscar vendedores cada 'ticker_timer' milisegundos
      addBehaviour(new TickerBehaviour(this, ticker_timer) {
        protected void onTick() {
          System.out.println("Trying to buy " + bookTitle);

          // Crear una descripción del agente que estamos buscando (vendedor de libros)
          DFAgentDescription template = new DFAgentDescription();
          ServiceDescription sd = new ServiceDescription();
          sd.setType("book-selling");
          template.addServices(sd);

          try {
            // Realizar una búsqueda en el Directorio Facilitador (DF) para encontrar vendedores
            DFAgentDescription[] result = DFService.search(myAgent, template);
            System.out.println("Found the following seller agents:");
            sellerAgents = new AID[result.length];
            for(int i = 0; i < result.length; i++) {
              sellerAgents[i] = result[i].getName();
              System.out.println(sellerAgents[i].getName());
            }

          } catch(FIPAException fe) {
            fe.printStackTrace();
          }

          // Agregar un comportamiento para enviar una solicitud a los vendedores encontrados
          myAgent.addBehaviour(new RequestPerformer(this_agent));
        }
      });
    } else {
      System.out.println("No target book title specified");
      doDelete();  // Si no se especifica un título de libro, se elimina el agente
    }
  }

  protected void takeDown() {
    System.out.println("Buyer agent " + getAID().getName() + " terminating");
  }

  // Métodos para obtener información sobre los agentes vendedores y el título del libro
  public AID[] getSellerAgents() {
    return sellerAgents;
  }

  public String getBookTitle() {
    return bookTitle;
  }
}
