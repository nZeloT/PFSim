package sim.research.dev;

import java.util.ArrayList;
import java.util.List;

import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.hr.HR;
import sim.production.Machine;
import sim.production.MachineType;

public class MachineUpgrade extends Upgrade<Machine> {

	private Machine machine;
	private HR empMgr;

	MachineUpgrade(Machine m, HR empMgr) {
		super(m.getType().getUpgradeDuration(), m.getType().getUpgradeCosts());

		this.machine = m;
		this.empMgr = empMgr;
	}

	@Override
	protected void setup() {
		//1. set machine to inupgrade
		machine.setInUpgrade(true);

		//2. unassign employees | this is needed because otherwise we only get a reference then remove items from it ...
		List<Employee> emps = new ArrayList<>(machine.getAssignedEmployees());
		for (Employee e : emps) {
			e.unassignWorkplace();
		}
	}

	@Override
	protected void finish() {
		//1. set machine to not in upgrade
		MachineType t = machine.getType();
		machine.deltaCosts(t.getUpgradeCostInc());
		machine.deltaPerformance(t.getUpgradePerfInc());
		machine.deltaQuality(t.getUpgradeQualInc());
		machine.deltaRequiredEmps(t.getUpgradeEmpInc());
		machine.setInUpgrade(false);

		//2. try to reassign employees
		Employee[] emps = empMgr.getUnassignedEmployees(EmployeeType.PRODUCTION, machine.getRequiredEmps());
		if(emps != null){
			for (Employee e : emps) {
				e.assignWorkplace(machine);
			}
		}
	}
	
	@Override Machine getUpgradeObject() {
		return machine;
	}

}
