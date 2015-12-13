package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;
import sim.simulation.sales.Offer;
import sim.simulation.sales.SalesSimulation;

public class ServerSimulation {
	
	private ResourceMarket market;
	private SalesSimulation sales;
	
	public ServerSimulation() {
		market = new ResourceMarket();
		sales = new SalesSimulation();
	}
	
	public ServerMessage[] simulationStep(ClientMessage[] clntMsgs){
		//0. setup the response objects
		ServerMessage[] servMsgs = new ServerMessage[clntMsgs.length];
		
		//1. aggregate all the received data for the market price adjustments
		HashMap<ResourceType, Integer> soldResources = new HashMap<>();
		for (ResourceType t : ResourceType.values()) {
			soldResources.put(t, 0);
		}
		
		for (ClientMessage c : clntMsgs) {
			HashMap<ResourceType, Integer> clnt = c.getBoughtResourceAmounts();
			for (ResourceType t : ResourceType.values()) {
				int current = soldResources.get(t);
				current += clnt.get(t);
				soldResources.put(t, current);
			}
		}
		
		//2. pass this values to the market
		market.adjustPrices(soldResources);
		
		//3. aggregate all the received data for the sales simulation
		HashMap<Integer, List<Offer>> offers = new HashMap<>();
		List<Offer> marketResearch = new ArrayList<>();
		for (int i = 0; i < clntMsgs.length; i++) {
			offers.put(i, clntMsgs[i].getNewCatalog());
			
			//TODO: is this correct? collect the data of ALL enterprises? or just the ones who are not the player?
			marketResearch.addAll(clntMsgs[i].getNewCatalog());
		}
		
		//4. pass those values to the sales simulation
		sales.simulateSalesMarket(offers);
		
		//5. is the game finished?
		boolean isFinished = isFinished();
		
		//6. some output :D
		System.out.println();
		System.out.println("<-------------------------------->");
		System.out.println("New Prices:");
		for (Entry<ResourceType, Integer> e : market.getCosts().entrySet()) {
			System.out.println("\t" + e.getKey() + "\t\t" + e.getValue());
		}
		System.out.println("<-------------------------------->");
		System.out.println();
		
		//6. build the response objects
		HashMap<Integer, List<Offer>> salesData = sales.getSalesData();
		for (int i = 0; i < servMsgs.length; i++) {
			servMsgs[i] = new ServerMessage(market.getCosts(), salesData.get(i), isFinished, marketResearch);
		}
		
		return servMsgs;
	}
	
	public boolean isFinished(){
		//TODO: when to end the game? additional data required?
		return false;
	}
	
	public ServerMessage getSetupMessage(){
		return new ServerMessage(market.getCosts(), new ArrayList<Offer>(), false, new ArrayList<Offer>());
	}
}
