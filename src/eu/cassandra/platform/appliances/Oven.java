package eu.cassandra.platform.appliances;

//import java.util.Random;

//import eu.cassandra.platform.Installation;
//import eu.cassandra.platform.Timeslot;

public class Oven extends Appliance
{

//  // Constructor
//  public Oven (String name, Installation inst)
//  {
//    super(name);
//
//    Random r = new Random();
//    double temp = r.nextDouble() * 100;
//
//    // Checking if the appliance will be in the installation //
//    if (temp < AppliancesConstants.OVEN_SATURATION) {
//
//      double power = Math.abs(r.nextGaussian()) * AppliancesConstants.OVEN_POWER_MEAN + AppliancesConstants.OVEN_POWER_VARIANCE;
//      double powerStandBy = Math.abs(r.nextGaussian()) * AppliancesConstants.OVEN_POWER_STANDBY_MEAN + AppliancesConstants.OVEN_POWER_STANDBY_VARIANCE;
//      int durationOn = (int) (r.nextDouble() * AppliancesConstants.OVEN_ONDURATION_CYCLE_MEAN + AppliancesConstants.OVEN_ONDURATION_CYCLE_VARIANCE);
//      int durationOff = (int) (r.nextDouble() * AppliancesConstants.OVEN_OFFDURATION_CYCLE_MEAN + AppliancesConstants.OVEN_OFFDURATION_CYCLE_VARIANCE);
//
//      // Creating the consumption model //
//      ConsumptionModel model = new ContinuousStepFunction(power, powerStandBy, durationOn, durationOff);
//
//      this.model = model;
//      installation = inst;
//      inst.addAppliance(this);
//    }
//  }
//
//  // Constructor
//  public Oven (String name, Installation inst, double powerOn, double powerStandBy, int durationOn, int durationStandBy)
//  {
//    super(name);
//
//    ConsumptionModel model = new ContinuousStepFunction(powerOn, powerStandBy, durationOn, durationStandBy);
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
