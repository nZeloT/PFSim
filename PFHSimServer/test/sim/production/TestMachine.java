package sim.production;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.warehouse.Warehouse;

public class TestMachine {

	/*@Test 
	public void testEmptyWarehouse() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Machine m = new Machine(new Warehouse(50, 100),emps);
		assertEquals(m.produceWall(WallType.ECO), false);
	}
	
	@Test
	public void testSuccessfulProduction() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		
		Warehouse wh = new Warehouse(50, 100); 
		Machine m = new Machine(wh, emps);
		Resource[] r = {
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),  
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		assertEquals(m.produceWall(WallType.ECO), true);
	}
	
	

	@Test
	public void testWrongTypesOfResourcesInWarehouse() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = new Warehouse(50, 100); 
		Machine m = new Machine(wh, emps);
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.INSULATION), 
				new Resource(100, ResourceType.INSULATION), 
				new Resource(100, ResourceType.INSULATION)};
		wh.storeResource(r);
		assertEquals(m.produceWall(WallType.ECO), false);
	}

	@Test
	public void testIfIsProducableAndProduceWallSuccessful() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = new Warehouse(50, 100); 
		Machine m = new Machine(wh, emps);
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		assertEquals(m.isProducable(WallType.ECO), true);
		assertEquals(m.produceWall(WallType.ECO), true);
		
	}
*/
	@Test
	public void testWhetherMultipleProductionProcessesWork() {

		ArrayList<Employee> emps = new ArrayList<>();
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		emps.add(new Employee(EmployeeType.PRODUCTION));
		Warehouse wh = new Warehouse(500, 100); 
		Machine m = new Machine(wh, emps);
		Resource[] r = {
				new Resource(100, ResourceType.INSULATION),
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD),
				new Resource(100, ResourceType.WOOD), 
				new Resource(100, ResourceType.WOOD)};
		wh.storeResource(r);
		
		assertEquals(m.isProducable(WallType.ECO), true);
		assertEquals(m.produceWall(WallType.ECO), true);
		assertEquals(m.isProducable(WallType.ECO), false);
		assertEquals(m.produceWall(WallType.ECO), false);
		
		wh.storeResource(new Resource(100, ResourceType.WOOD));
		assertEquals(m.isProducable(WallType.ECO), true);
		assertEquals(m.produceWall(WallType.ECO), true);

		wh.storeResource(new Resource(100, ResourceType.WOOD));
		assertEquals(m.isProducable(WallType.ECO), false);
		assertEquals(m.produceWall(WallType.ECO), false);
		
		
	}

	
}
