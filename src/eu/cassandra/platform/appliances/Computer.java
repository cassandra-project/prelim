package eu.cassandra.platform.appliances;

//import java.util.Random;

//import eu.cassandra.platform.Installation;
//import eu.cassandra.platform.Timeslot;

public class Computer extends Appliance
{

//  // Constructor
//  public Computer (String name, Installation inst)
//  {
//    super(name);
//
//    Random r = new Random();
//    double temp = r.nextDouble() * 100;
//
//    // Checking if the appliance will be in the installation //
//    if (temp < AppliancesConstants.COMPUTER_SATURATION) {
//
//      double powerOn = Math.abs(r.nextGaussian()) * AppliancesConstants.COMPUTER_POWER_MEAN + AppliancesConstants.COMPUTER_POWER_VARIANCE;
//      double powerOff = 0;
//
//      // Creating the consumption model //
//      ConsumptionModel model = new SingleStepFunction(powerOn, powerOff);
//
//      this.model = model;
//      installation = inst;
//      inst.addAppliance(this);
//    }
//  }
//
//  // Constructor
//  public Computer (String name, Installation inst, double powerOn, int durationOn)
//  {
//    super(name);
//
//    ConsumptionModel model = new SingleStepFunction(powerOn, durationOn);
//
//    this.model = model;
//    installation = inst;
//    inst.addAppliance(this);
//
//  }
//
//  @Override
//  public void stepNextDay (Timeslot[] timeslots)
//  {
//    nextDayConsumption = model.getNextDayConsumption(timeslots);
//    setInUse();
//  }

}
