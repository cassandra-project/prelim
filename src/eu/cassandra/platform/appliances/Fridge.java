package eu.cassandra.platform.appliances;

//import java.util.Random;

//import eu.cassandra.platform.Installation;
//import eu.cassandra.platform.Timeslot;

public class Fridge extends Appliance
{

//  // Constructor
//  public Fridge (String name, Installation inst)
//  {
//    super(name);
//
//    Random r = new Random();
//    double temp = r.nextDouble() * 100;
//
//    // Checking if the appliance will be in the installation //
//    if (temp < AppliancesConstants.FRIDGE_SATURATION) {
//
//      double power = Math.abs(r.nextGaussian()) * AppliancesConstants.FRIDGE_POWER_MEAN + AppliancesConstants.FRIDGE_POWER_VARIANCE;
//      int durationOn = (int) (r.nextDouble() * AppliancesConstants.FRIDGE_DURATION_CYCLE_MEAN + AppliancesConstants.FRIDGE_DURATION_CYCLE_VARIANCE);
//      int durationStandBy = (int) (r.nextDouble() * AppliancesConstants.FRIDGE_DURATION_CYCLE_MEAN + AppliancesConstants.FRIDGE_DURATION_CYCLE_VARIANCE);
//
//      // Creating the consumption model //
//      ConsumptionModel model = new ContinuousStepFunction(power, 0, durationOn, durationStandBy);
//
//      this.model = model;
//      installation = inst;
//      inst.addAppliance(this);
//    }
//  }
//
//  // Constructor
//  public Fridge (String name, Installation inst, double powerOn, double powerStandBy, int durationOn, int durationStandBy)
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
