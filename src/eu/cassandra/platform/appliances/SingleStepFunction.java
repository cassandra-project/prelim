package eu.cassandra.platform.appliances;

//import eu.cassandra.platform.Timeslot;

public class SingleStepFunction extends ConsumptionModel
{

//  private double powerOn;
//  private double powerStandBy;
//
//  public SingleStepFunction ()
//  {
//  }
//
//  public SingleStepFunction (double aPowerOn, double aPowerStandBy)
//  {
//    powerOn = aPowerOn;
//    powerStandBy = aPowerStandBy;
//
//  }
//
//  public void setPowerOn (double powerOn)
//  {
//    this.powerOn = powerOn;
//  }
//
//  public void setPowerStandBy (double powerStandBy)
//  {
//    this.powerStandBy = powerStandBy;
//  }
//
//  public double getPowerOn ()
//  {
//    return powerOn;
//  }
//
//  public double getPowerStandBy ()
//  {
//    return powerStandBy;
//  }
//
//  @Override
//  public void showStatus ()
//  {
//    System.out.println("Power On: " + powerOn);
//    System.out.println("Power Off: " + powerStandBy);
//  }
//
//  @Override
//  public double[] getNextDayConsumption (Timeslot[] timeslots)
//  {
//
//    double[] consumption = new double[AppliancesConstants.MINUTES_OF_DAY];
//
//    for (int i = 0; i < AppliancesConstants.MINUTES_OF_DAY; i++) {
//      consumption[i] = powerStandBy;
//    }
//
//    for (int i = 0; i < timeslots.length; i++) {
//      timeslots[i].Status();
//      for (int j = 0; j < timeslots[i].getDuration(); j++) {
//        consumption[timeslots[i].getStartTime() + j] = powerOn;
//      }
//    }
//
//    return consumption;
//  }

}
