package eu.cassandra.platform.appliances;

//import eu.cassandra.platform.Installation;
//import eu.cassandra.platform.Timeslot;

public class Appliance
{
//  protected static int idCounter = 0;
//  protected int id;
//  protected String name;
//  protected Installation installation;
//  protected ConsumptionModel model;
//  // private boolean inUse;
//  // private int scale;
//  // private long onTick;
//  protected boolean[] nextDayInUse = new boolean[AppliancesConstants.MINUTES_OF_DAY];
//  protected double[] nextDayConsumption = new double[AppliancesConstants.MINUTES_OF_DAY];
//
//  public Appliance (String aName)
//  {
//    id = idCounter++;
//    name = aName;
//    // scale = 0;
//    // inUse = false;
//    // onTick = -1;
//    // model = aModel;
//    // installation.addAppliance(this);
//    // installation = aInstallation;
//  }
//
//  // -----------------------------------SETTERS--------------------------------- //
//
//  public void setId (int newid)
//  {
//    id = newid;
//  }
//
//  public void setName (String newName)
//  {
//    name = newName;
//  }
//
//  public void setInstallation (Installation aInstallation)
//  {
//    installation = aInstallation;
//  }
//
//  public void setModel (ConsumptionModel aModel)
//  {
//    model = aModel;
//  }
//
//  /*
//    public void setScale (int newScale)
//    {
//      scale = newScale;
//    }
//  */
//  // -----------------------------------GETTERS--------------------------------- //
//
//  public int getId ()
//  {
//    return id;
//  }
//
//  public String getName ()
//  {
//    return name;
//  }
//
//  public Installation getInstallation ()
//  {
//    return installation;
//  }
//
//  public ConsumptionModel getModel ()
//  {
//    return model;
//  }
//
//  /*
//    public boolean isInUse ()
//    {
//      return inUse;
//    }
//
//    public int getScale ()
//    {
//      return scale;
//    }
//
//    public long getOnTick ()
//    {
//      return onTick;
//    }
//  */
//  // -----------------------------------OPERATION FUNCTIONS--------------------------------- //
//
//  public double getPower (long tick)
//  {
//    int minute = (int) tick % AppliancesConstants.MINUTES_OF_DAY;
//    return nextDayConsumption[minute];
//  }
//
//  public void stepNextDay (Timeslot[] timeslots)
//  {
//  }
//
//  public void showStatus ()
//  {
//    System.out.println("ApplianceID:" + id);
//    System.out.println("Name:" + name);
//    System.out.println("Installation:" + installation.getName());
//    System.out.println("Usage and Power Consumption for Next Day");
//
//    for (int i = 0; i < AppliancesConstants.MINUTES_OF_DAY; i++) {
//      System.out.println("Minute :" + i + "  In Use: " + nextDayInUse[i] + " Power Consumption: " + nextDayConsumption[i]);
//    }
//
//    model.showStatus();
//
//  }
//
//  public void setInUse ()
//  {
//    for (int i = 0; i < AppliancesConstants.MINUTES_OF_DAY; i++) {
//      if (nextDayConsumption[i] > 0)
//        nextDayInUse[i] = true;
//      else
//        nextDayInUse[i] = false;
//    }
//  }
//
//  /*
//    public void turnOff ()
//    {
//      inUse = false;
//      onTick = -1;
//    }
//
//    public void turnOn (long tick)
//    {
//      inUse = true;
//      onTick = tick;
//    }
//  */
}
