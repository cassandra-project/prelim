package eu.cassandra.platform.appliances;

//import eu.cassandra.platform.Timeslot;

public class ContinuousStepFunction extends ConsumptionModel
{

//  private double powerOn;
//  private double powerStandBy;
//  private int periodOn;
//  private int periodStandBy;
//
//  public ContinuousStepFunction ()
//  {
//  }
//
//  public ContinuousStepFunction (double aPowerOn, double aPowerStandBy, int aPeriodOn, int aPeriodStandBy)
//  {
//    powerOn = aPowerOn;
//    periodOn = aPeriodOn;
//    periodStandBy = aPeriodStandBy;
//    powerStandBy = aPowerStandBy;
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
//  public void setPeriodOn (int periodOn)
//  {
//    this.periodOn = periodOn;
//  }
//
//  public void setPeriodStandBy (int periodStandBy)
//  {
//    this.periodStandBy = periodStandBy;
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
//  public int getPeriodOn ()
//  {
//    return periodOn;
//  }
//
//  public int getPeriodStandBy ()
//  {
//    return periodStandBy;
//  }
//
//  @Override
//  public double[] getNextDayConsumption (Timeslot[] timeslots)
//  {
//
//    double[] consumption = new double[AppliancesConstants.MINUTES_OF_DAY];
//    int fullPeriod = periodOn + periodStandBy;
//
//    for (int i = 0; i < timeslots.length; i++) {
//      timeslots[i].Status();
//      int timeCounter = 0;
//      for (int j = 0; j < timeslots[i].getDuration(); j++) {
//        int time = timeCounter % fullPeriod;
//        if (time < periodOn)
//          consumption[timeslots[i].getStartTime() + j] = powerOn;
//        else
//          consumption[timeslots[i].getStartTime() + j] = powerStandBy;
//        timeCounter++;
//      }
//    }
//    return consumption;
//  }
//
//  @Override
//  public void showStatus ()
//  {
//    System.out.println("Power On: " + powerOn);
//    System.out.println("Power StandBy: " + powerStandBy);
//    System.out.println("On Duration: " + periodOn);
//    System.out.println("StandBy Duration: " + periodStandBy);
//  }
}
